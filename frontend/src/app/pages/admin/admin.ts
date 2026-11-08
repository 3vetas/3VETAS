import { Component, computed, inject, linkedSignal, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { RouterLink } from '@angular/router';

import { PlanoCorte } from '../../components/plano-corte/plano-corte';
import { PEDIDOS } from '../../mocks/pedidos';
import { USUARIOS } from '../../mocks/usuarios';
import { EstadoPedido, NOMBRES_ESTADO, numeroPedido, Pedido } from '../../models/pedido';
import { Catalogo } from '../../services/catalogo';

@Component({
  imports: [PlanoCorte, RouterLink],
  selector: 'app-admin',
  styleUrl: './admin.css',
  templateUrl: './admin.html',
})
export class Admin {
  // Datos de prueba hasta que existan GET /api/pedidos y PATCH /api/pedidos/{id}/estado.
  // Los cambios de estado solo se guardan en la página.
  protected readonly pedidos = signal(PEDIDOS);
  protected readonly usuarios = USUARIOS;
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

  // Pedido en revisión: al principio, el primero pendiente.
  protected readonly seleccionadoId = signal(PEDIDOS.find((p) => p.estado === 'pendiente')?.id);
  protected readonly seleccionado = computed(() =>
    this.pedidos().find((p) => p.id === this.seleccionadoId()),
  );

  // Tableros físicos de la opción elegida en cada material del pedido en revisión.
  protected readonly planos = computed(() =>
    (this.seleccionado()?.proyecto.tableros ?? []).flatMap((material) =>
      (material.optimizaciones.find((o) => o.seleccionada)?.detalles ?? []).flatMap((detalle) =>
        detalle.unidades.map((unidad) => ({ material, formato: detalle.tablero.formato, unidad })),
      ),
    ),
  );

  // Al cambiar de pedido, el desplegable vuelve a su estado y el motivo se vacía.
  protected readonly estadoNuevo = linkedSignal<EstadoPedido>(
    () => this.seleccionado()?.estado ?? 'pendiente',
  );
  protected readonly motivo = linkedSignal({ source: this.seleccionadoId, computation: () => '' });

  protected materiales(pedido: Pedido): string {
    return pedido.proyecto.tableros
      .map(
        (t) => `${t.tipoTablero.nombre} ${t.acabado.nombre.toLowerCase()} ${t.grosor.valorMm} mm`,
      )
      .join(', ');
  }

  protected piezas(pedido: Pedido): number {
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
              historial: [
                ...p.historial,
                { estado, comentario, fechaEstado: new Date().toISOString() },
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

function textoBuscable(pedido: Pedido): string {
  const { persona, proyecto } = pedido;
  return [numeroPedido(pedido), persona.nombre, persona.apellidos, proyecto.nombre]
    .join(' ')
    .toLowerCase();
}
