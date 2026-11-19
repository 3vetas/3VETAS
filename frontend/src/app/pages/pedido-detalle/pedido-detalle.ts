import { Component, computed, inject, input, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

import { PlanoCorte } from '../../components/plano-corte/plano-corte';
import { NOMBRES_ESTADO, numeroPedido, PedidoCompleto } from '../../models/pedido';
import { PedidosService } from '../../services/pedidos';

@Component({
  imports: [PlanoCorte, RouterLink],
  selector: 'app-pedido-detalle',
  styleUrl: './pedido-detalle.css',
  templateUrl: './pedido-detalle.html',
})
export class PedidoDetalle implements OnInit {
  private readonly pedidosService = inject(PedidosService);

  // El :id de la ruta.
  readonly id = input.required<string>();

  // Se queda vacío si no existe ningún pedido con ese id.
  protected readonly pedido = signal<PedidoCompleto | undefined>(undefined);

  ngOnInit(): void {
    this.pedidosService.obtenerPedido(Number(this.id())).subscribe((pedido) => {
      this.pedido.set(pedido);
    });
  }

  // Cada material del proyecto con los tableros de la optimización que compró el cliente.
  protected readonly materiales = computed(() => this.pedido()?.materiales ?? []);

  protected readonly totalPiezas = computed(() =>
    this.materiales()
      .flatMap((m) => m.piezas)
      .reduce((total, p) => total + p.cantidad, 0),
  );
  protected readonly totalTableros = computed(() =>
    this.materiales().reduce((total, m) => total + m.planos.length, 0),
  );

  // Comentario del último cambio de estado, por ejemplo el motivo de un rechazo.
  protected readonly motivo = computed(
    () => this.pedido()?.historialEstados.at(-1)?.comentario ?? '',
  );

  protected readonly nombresEstado = NOMBRES_ESTADO;
  protected readonly numeroPedido = numeroPedido;
  protected readonly euros = new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' });
  private readonly formatoFecha = new Intl.DateTimeFormat('es-ES', {
    dateStyle: 'short',
    timeStyle: 'short',
  });

  protected fecha(iso: string): string {
    return this.formatoFecha.format(new Date(iso));
  }

  protected imprimir(): void {
    window.print();
  }
}
