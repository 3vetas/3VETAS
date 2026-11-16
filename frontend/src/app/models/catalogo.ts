export interface TipoTablero {
  id: number;
  nombre: string;
  descripcion: string;
  imagen: string;
}

export interface Acabado {
  id: number;
  familia: string;
  nombre: string;
  colorBase: string;
  textura: string;
  fabricante: string;
  codFabricante: string;
}

export interface Formato {
  id: number;
  anchoMm: number;
  altoMm: number;
}

export interface Grosor {
  id: number;
  espesorMm: number;
}

// El backend devuelve el tablero solo con los id de sus características.
export interface Tablero {
  id: number;
  tipoTableroId: number;
  acabadoId: number;
  formatoId: number;
  grosorId: number;
  referencia: string;
  descripcion: string;
  precioTablero: number;
  stockActual: number;
  activo: boolean;
}

// El servicio del catálogo añade las características para que las páginas no tengan que buscarlas.
export interface TableroCompleto extends Tablero {
  tipoTablero: TipoTablero;
  acabado: Acabado;
  formato: Formato;
  grosor: Grosor;
}
