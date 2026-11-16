import { Acabado, Grosor, TipoTablero } from './catalogo';

// Los id y las fechas los asigna el backend al guardar: faltan mientras el proyecto solo está
// en el planificador.

// Un material del proyecto (tipo, acabado y grosor) con sus piezas. El backend lo devuelve solo
// con los id de sus características.
export interface TableroProyecto {
  id?: number;
  tipoTableroId: number;
  acabadoId: number;
  grosorId: number;
  piezas: Pieza[];
}

// El material con sus características, sacadas del catálogo para mostrarlo.
export interface MaterialCompleto extends TableroProyecto {
  tipoTablero: TipoTablero;
  acabado: Acabado;
  grosor: Grosor;
}

export interface Pieza {
  // Las distribuciones se refieren a la pieza por su id. Hasta que se guarda, el planificador
  // le pone uno provisional negativo.
  id: number;
  descripcion: string;
  anchoMm: number;
  altoMm: number;
  cantidad: number;
  rotacionPermitida: boolean;
}

// Una opción para cortar las piezas de un material. Solo una por material está seleccionada.
export interface Optimizacion {
  id?: number;
  // Material al que pertenece.
  tableroProyectoId?: number;
  nombreOpcion: string;
  // Superficies en mm².
  superficieUtilizada: number;
  superficieDesperdiciada: number;
  porcentajeAprovechamiento: number;
  costeEstimado: number;
  seleccionada: boolean;
  fechaCalculo?: string;
  detalles: DetalleOptimizacion[];
}

// Los tableros de un mismo formato que usa una optimización.
export interface DetalleOptimizacion {
  tableroId: number;
  cantidad: number;
  // Importe de todos los tableros del detalle, con el precio del momento del cálculo.
  precio: number;
  unidades: UnidadTableroOptimizacion[];
}

// Un tablero físico.
export interface UnidadTableroOptimizacion {
  numeroTablero: number;
  distribuciones: DistribucionPieza[];
}

export interface DistribucionPieza {
  piezaId: number;
  numeroUnidad: number;
  ordenDibujo: number;
  posicionXmm: number;
  posicionYmm: number;
  anchoFinalMm: number;
  altoFinalMm: number;
  rotada: boolean;
}
