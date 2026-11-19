import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

import { NOMBRES_ESTADO, numeroPedido, PedidoCompleto } from '../../models/pedido';
import { PedidosService } from '../../services/pedidos';

@Component({
  imports: [RouterLink],
  selector: 'app-pedidos',
  styleUrl: './pedidos.css',
  templateUrl: './pedidos.html',
})
export class Pedidos implements OnInit {
  private readonly pedidosService = inject(PedidosService);

  protected readonly pedidos = signal<PedidoCompleto[]>([]);

  protected readonly nombresEstado = NOMBRES_ESTADO;
  protected readonly numeroPedido = numeroPedido;
  protected readonly euros = new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' });
  private readonly formatoFecha = new Intl.DateTimeFormat('es-ES', { dateStyle: 'short' });

  ngOnInit(): void {
    this.pedidosService.obtenerMisPedidos().subscribe((pedidos) => {
      // Del más reciente al más antiguo.
      pedidos.sort((a, b) => b.fechaPedido.localeCompare(a.fechaPedido));
      this.pedidos.set(pedidos);
    });
  }

  protected fecha(iso: string): string {
    return this.formatoFecha.format(new Date(iso));
  }
}
