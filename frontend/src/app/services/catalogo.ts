import { Service } from '@angular/core';
import { forkJoin, map, Observable, of } from 'rxjs';

import { ACABADOS, FORMATOS, GROSORES, TABLEROS, TIPOS_TABLERO } from '../mocks/catalogo';
import {
  Acabado,
  Formato,
  Grosor,
  Tablero,
  TableroCompleto,
  TipoTablero,
} from '../models/catalogo';

interface ListasCatalogo {
  tableros: Tablero[];
  tipos: TipoTablero[];
  acabados: Acabado[];
  formatos: Formato[];
  grosores: Grosor[];
}

@Service()
export class Catalogo {
  // Datos de prueba hasta que existan los GET del catálogo. El backend devuelve cada tablero solo
  // con los id de sus características, así que hay que pedir también esas cuatro listas.
  obtenerTableros(): Observable<TableroCompleto[]> {
    return forkJoin({
      tableros: of(TABLEROS),
      tipos: of(TIPOS_TABLERO),
      acabados: of(ACABADOS),
      formatos: of(FORMATOS),
      grosores: of(GROSORES),
    }).pipe(map(completarTableros));
  }
}

export function completarTableros(listas: ListasCatalogo): TableroCompleto[] {
  return listas.tableros.map((tablero) => ({
    ...tablero,
    tipoTablero: buscar(listas.tipos, tablero.tipoTableroId),
    acabado: buscar(listas.acabados, tablero.acabadoId),
    formato: buscar(listas.formatos, tablero.formatoId),
    grosor: buscar(listas.grosores, tablero.grosorId),
  }));
}

// Las claves ajenas de la base de datos garantizan que el id existe.
function buscar<T extends { id: number }>(lista: T[], id: number): T {
  return lista.find((item) => item.id === id)!;
}
