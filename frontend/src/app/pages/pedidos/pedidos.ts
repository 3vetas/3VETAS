import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

import { CLIENTE, PEDIDOS } from '../../mocks/pedidos';
import { NOMBRES_ESTADO, numeroPedido } from '../../models/pedido';

@Component({
  imports: [RouterLink],
  selector: 'app-pedidos',
  styleUrl: './pedidos.css',
  templateUrl: './pedidos.html',
})
export class Pedidos {
  // Datos de prueba hasta que exista GET /api/pedidos/mis-pedidos. Del más reciente al más antiguo.
  protected readonly pedidos = PEDIDOS.filter((p) => p.persona.dni === CLIENTE.dni).sort((a, b) =>
    b.fechaPedido.localeCompare(a.fechaPedido),
  );

  protected readonly nombresEstado = NOMBRES_ESTADO;
  protected readonly numeroPedido = numeroPedido;
  protected readonly euros = new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' });
  private readonly formatoFecha = new Intl.DateTimeFormat('es-ES', { dateStyle: 'short' });

  protected fecha(iso: string): string {
    return this.formatoFecha.format(new Date(iso));
  }
}
