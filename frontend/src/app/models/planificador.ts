import { Tablero } from './catalogo';

// Los id y las fechas de estas tablas los asigna el backend al guardar el proyecto.

export interface Pieza {
  descripcion: string;
  anchoMm: number;
  altoMm: number;
  cantidad: number;
  rotacionPermitida: boolean;
}

export interface DistribucionPieza {
  // Posición de la pieza en la lista de piezas del proyecto.
  indicePieza: number;
  numTablero: number;
  numeroUnidad: number;
  ordenDibujo: number;
  posicionXMm: number;
  posicionYMm: number;
  anchoFinalMm: number;
  altoFinalMm: number;
  rotada: boolean;
}

export interface Optimizacion {
  nombreOpcion: string;
  tablero: Tablero;
  // Número de tableros necesarios.
  cantidad: number;
  // Superficies en mm².
  superficieUtilizada: number;
  superficieDesperdiciada: number;
  porcentajeAprovechamiento: number;
  costeEstimado: number;
  seleccionada: boolean;
  distribucion: DistribucionPieza[];
}
