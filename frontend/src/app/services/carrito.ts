import { Service } from '@angular/core';
import { Observable, of } from 'rxjs';

import { CARRITO } from '../mocks/carrito';
import { LineaCarrito } from '../models/carrito';

// El carrito todavía no está definido en la base de datos ni en la API,
// así que de momento devuelve los datos de prueba.
@Service()
export class CarritoService {
  obtenerLineas(): Observable<LineaCarrito[]> {
    return of(CARRITO);
  }
}
