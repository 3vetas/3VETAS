import { Service } from '@angular/core';
import { Observable, of } from 'rxjs';

import { CLIENTE, PEDIDOS } from '../mocks/pedidos';
import { PedidoCompleto } from '../models/pedido';

// De momento devuelve los datos de prueba. Cuando el backend tenga los endpoints,
// solo habrá que cambiar estos métodos por llamadas con HttpClient.
@Service()
export class PedidosService {
  // GET /api/pedidos (panel de administración)
  obtenerPedidos(): Observable<PedidoCompleto[]> {
    return of(PEDIDOS);
  }

  // GET /api/pedidos/mis-pedidos
  // Mientras no haya login, el cliente con la sesión iniciada es siempre CLIENTE.
  obtenerMisPedidos(): Observable<PedidoCompleto[]> {
    const misPedidos: PedidoCompleto[] = [];
    for (const pedido of PEDIDOS) {
      if (pedido.personaId === CLIENTE.id) {
        misPedidos.push(pedido);
      }
    }
    return of(misPedidos);
  }

  // GET /api/pedidos/{id}
  obtenerPedido(id: number): Observable<PedidoCompleto | undefined> {
    for (const pedido of PEDIDOS) {
      if (pedido.id === id) {
        return of(pedido);
      }
    }
    return of(undefined);
  }
}
