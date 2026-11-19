import { Service } from '@angular/core';
import { Observable, of } from 'rxjs';

import { USUARIOS } from '../mocks/usuarios';
import { Usuario } from '../models/usuario';

// De momento devuelve los datos de prueba. Cuando el backend tenga el endpoint,
// solo habrá que cambiar este método por una llamada con HttpClient.
@Service()
export class UsuariosService {
  // GET /api/usuarios (panel de administración)
  obtenerUsuarios(): Observable<Usuario[]> {
    return of(USUARIOS);
  }
}
