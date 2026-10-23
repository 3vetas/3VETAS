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
  valorMm: number;
}

export interface Tablero {
  id: number;
  tipo: TipoTablero;
  acabado: Acabado;
  formato: Formato;
  grosor: Grosor;
  referencia: string;
  descripcion: string;
  precioTablero: number;
  stockActual: number;
  activo: boolean;
}
