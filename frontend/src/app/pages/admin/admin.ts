import { Component, computed, inject, linkedSignal, OnInit, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { RouterLink } from '@angular/router';

import { PlanoCorte } from '../../components/plano-corte/plano-corte';
import { ID_USUARIO_TALLER } from '../../mocks/pedidos';
import { EstadoPedido, NOMBRES_ESTADO, numeroPedido, PedidoCompleto } from '../../models/pedido';
import { Catalogo } from '../../services/catalogo';
import { PedidosService } from '../../services/pedidos';
import { UsuariosService } from '../../services/usuarios';

@Component({
  imports: [PlanoCorte, RouterLink],
  selector: 'app-admin',
  styleUrl: './admin.css',
  templateUrl: './admin.html',
})
export class Admin implements OnInit {
  private readonly pedidosService = inject(PedidosService);

  // Los cambios de estado solo se guardan en la página hasta que exista
  // PATCH /api/pedidos/{id}/estado.
  protected readonly pedidos = signal<PedidoCompleto[]>([]);
  protected readonly usuarios = toSignal(inject(UsuariosService).obtenerUsuarios(), {
    initialValue: [],
  });
  protected readonly tableros = toSignal(inject(Catalogo).obtenerTableros(), { initialValue: [] });

  protected readonly estados = Object.keys(NOMBRES_ESTADO) as EstadoPedido[];
  protected readonly nombresEstado = NOMBRES_ESTADO;
  protected readonly numeroPedido = numeroPedido;
  protected readonly euros = new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' });

  // Filtros de la lista de pedidos. Un estado vacío significa «todos».
  protected readonly filtroEstado = signal<EstadoPedido | ''>('');
  protected readonly busqueda = signal('');

  protected readonly filtrados = computed(() => {
    const texto = this.busqueda().trim().toLowerCase();
    return this.pedidos().filter(
      (p) =>
        (this.filtroEstado() === '' || p.estado === this.filtroEstado()) &&
        (texto === '' || textoBuscable(p).includes(texto)),
    );
  });

  // Pedido en revisión: al cargar la página, el primero pendiente.
  protected readonly seleccionadoId = signal<number | undefined>(undefined);
  protected readonly seleccionado = computed(() =>
    this.pedidos().find((p) => p.id === this.seleccionadoId()),
  );

  // Tableros físicos de la opción elegida en cada material del pedido en revisión.
  protected readonly planos = computed(() =>
    (this.seleccionado()?.materiales ?? []).flatMap((material) =>
      material.planos.map((plano) => ({ material, ...plano })),
    ),
  );

  // Al cambiar de pedido, el desplegable vuelve a su estado y el motivo se vacía.
  protected readonly estadoNuevo = linkedSignal<EstadoPedido>(
    () => this.seleccionado()?.estado ?? 'pendiente',
  );
  protected readonly motivo = linkedSignal({ source: this.seleccionadoId, computation: () => '' });

  ngOnInit(): void {
    this.pedidosService.obtenerPedidos().subscribe((pedidos) => {
      this.pedidos.set(pedidos);

      // Selecciona el primer pedido pendiente.
      for (const pedido of pedidos) {
        if (pedido.estado === 'pendiente') {
          this.seleccionadoId.set(pedido.id);
          break;
        }
      }
    });
  }

  protected materiales(pedido: PedidoCompleto): string {
    return pedido.materiales
      .map(
        (t) => `${t.tipoTablero.nombre} ${t.acabado.nombre.toLowerCase()} ${t.grosor.espesorMm} mm`,
      )
      .join(', ');
  }

  protected piezas(pedido: PedidoCompleto): number {
    return pedido.proyecto.tableros
      .flatMap((t) => t.piezas)
      .reduce((total, p) => total + p.cantidad, 0);
  }

  protected cambiarFiltroEstado(valor: string): void {
    this.filtroEstado.set(valor as EstadoPedido | '');
  }

  protected cambiarEstado(estado: EstadoPedido): void {
    const id = this.seleccionadoId();
    const comentario = estado === 'rechazado' ? this.motivo().trim() : '';
    this.pedidos.update((pedidos) =>
      pedidos.map((p) =>
        p.id === id
          ? {
              ...p,
              estado,
              historialEstados: [
                ...p.historialEstados,
                {
                  // Id provisional: el definitivo lo pondrá el backend.
                  id: -(p.historialEstados.length + 1),
                  estado,
                  comentario,
                  usuarioId: ID_USUARIO_TALLER,
                  fechaEstado: new Date().toISOString(),
                },
              ],
            }
          : p,
      ),
    );
  }

  protected imprimir(): void {
    window.print();
  }
}

function textoBuscable(pedido: PedidoCompleto): string {
  const { persona, proyecto } = pedido;
  return [numeroPedido(pedido), persona.nombre, persona.apellidos, proyecto.nombre]
    .join(' ')
    .toLowerCase();
}
