import { Service } from '@angular/core';
import { Observable, of } from 'rxjs';

import { TABLEROS } from '../mocks/catalogo';
import { Tablero } from '../models/catalogo';

@Service()
export class Catalogo {
  // Datos de prueba hasta que exista GET /api/productos
  //  despues:
  // this.http.get<Tablero[]>('/api/productos').
  obtenerTableros(): Observable<Tablero[]> {
    return of(TABLEROS);
  }
}
