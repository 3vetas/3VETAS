import { Acabado, Grosor, Tablero, TipoTablero } from './catalogo';

// Los id y las fechas de estas tablas los asigna el backend al guardar el proyecto.

// Un material del proyecto (tipo, acabado y grosor) con sus piezas y sus optimizaciones.
export interface TableroProyecto {
  tipoTablero: TipoTablero;
  acabado: Acabado;
  grosor: Grosor;
  piezas: Pieza[];
  optimizaciones: Optimizacion[];
}

export interface Pieza {
  descripcion: string;
  anchoMm: number;
  altoMm: number;
  cantidad: number;
  rotacionPermitida: boolean;
}

// Una opción para cortar las piezas de un material. Solo una por material está seleccionada.
export interface Optimizacion {
  nombreOpcion: string;
  // Superficies en mm².
  superficieUtilizada: number;
  superficieDesperdiciada: number;
  porcentajeAprovechamiento: number;
  costeEstimado: number;
  seleccionada: boolean;
  detalles: DetalleOptimizacion[];
}

// Los tableros de un mismo formato que usa una optimización.
export interface DetalleOptimizacion {
  tablero: Tablero;
  cantidad: number;
  // Precio de cada tablero en el momento del cálculo.
  precio: number;
  unidades: UnidadTableroOptimizacion[];
}

// Un tablero físico.
export interface UnidadTableroOptimizacion {
  numeroTablero: number;
  distribucion: DistribucionPieza[];
}

export interface DistribucionPieza {
  // Posición de la pieza en la lista de piezas de su material.
  indicePieza: number;
  numeroUnidad: number;
  ordenDibujo: number;
  posicionXMm: number;
  posicionYMm: number;
  anchoFinalMm: number;
  altoFinalMm: number;
  rotada: boolean;
}
