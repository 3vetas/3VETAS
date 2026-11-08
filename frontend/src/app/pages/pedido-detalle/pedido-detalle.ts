import { Component, computed, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { PlanoCorte } from '../../components/plano-corte/plano-corte';
import { PEDIDOS } from '../../mocks/pedidos';
import { NOMBRES_ESTADO, numeroPedido } from '../../models/pedido';

@Component({
  imports: [PlanoCorte, RouterLink],
  selector: 'app-pedido-detalle',
  styleUrl: './pedido-detalle.css',
  templateUrl: './pedido-detalle.html',
})
export class PedidoDetalle {
  // El :id de la ruta.
  readonly id = input.required<string>();

  // Datos de prueba hasta que exista GET /api/pedidos/{id}.
  protected readonly pedido = computed(() => PEDIDOS.find((p) => p.id === Number(this.id())));

  // Cada material del proyecto con la optimización que eligió el cliente.
  protected readonly materiales = computed(() =>
    (this.pedido()?.proyecto.tableros ?? []).map((material) => ({
      material,
      elegida: material.optimizaciones.find((o) => o.seleccionada),
    })),
  );

  protected readonly totalPiezas = computed(() =>
    this.materiales()
      .flatMap((m) => m.material.piezas)
      .reduce((total, p) => total + p.cantidad, 0),
  );
  protected readonly totalTableros = computed(() =>
    this.materiales()
      .flatMap((m) => m.elegida?.detalles ?? [])
      .reduce((total, d) => total + d.cantidad, 0),
  );

  // Comentario del último cambio de estado, por ejemplo el motivo de un rechazo.
  protected readonly motivo = computed(() => this.pedido()?.historial.at(-1)?.comentario ?? '');

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
