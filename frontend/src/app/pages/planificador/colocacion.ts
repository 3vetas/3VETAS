import { Formato } from '../../models/catalogo';
import { DistribucionPieza, Pieza, UnidadTableroOptimizacion } from '../../models/planificador';

// Provisional: falta decidir si se guarda en TABLEROS o en una tabla de configuración.
export const GROSOR_SIERRA_MM = 4;

export type Colocacion =
  { ok: true; unidades: UnidadTableroOptimizacion[] } | { ok: false; error: string };

interface Medidas {
  ancho: number;
  alto: number;
  rotada: boolean;
}

interface Fila {
  y: number;
  // Donde empieza el hueco libre de la fila.
  x: number;
}

interface TableroAbierto {
  filas: Fila[];
  // Donde empieza el alto libre del tablero.
  y: number;
  distribucion: DistribucionPieza[];
}

// Coloca las piezas por filas, de izquierda a derecha y de arriba abajo:
// - las piezas van de la más alta a la más baja;
// - cada pieza entra en la primera fila de cualquier tablero donde le quede ancho;
// - si no cabe en ninguna, abre una fila en el primer tablero con alto libre o un tablero nuevo.
// Entre piezas y entre filas se pierde el grosor de sierra; en los bordes del tablero, no.
// Así cada corte atraviesa el tablero o la fila de lado a lado, como en una seccionadora.
export function colocarPorFilas(
  piezas: Pieza[],
  formato: Formato,
  grosorSierraMm = GROSOR_SIERRA_MM,
): Colocacion {
  const unidades = [];
  for (const [indicePieza, pieza] of piezas.entries()) {
    const medidas = orientar(pieza, formato);
    if (!medidas) {
      return { ok: false, error: `No cabe «${pieza.descripcion}»` };
    }
    for (let numeroUnidad = 1; numeroUnidad <= pieza.cantidad; numeroUnidad++) {
      unidades.push({ indicePieza, numeroUnidad, ...medidas });
    }
  }
  unidades.sort((a, b) => b.alto - a.alto || b.ancho - a.ancho);

  const tableros: TableroAbierto[] = [];
  for (const unidad of unidades) {
    const { tablero, fila } = buscarHueco(tableros, unidad, formato, grosorSierraMm);
    tablero.distribucion.push({
      indicePieza: unidad.indicePieza,
      numeroUnidad: unidad.numeroUnidad,
      ordenDibujo: tablero.distribucion.length + 1,
      posicionXMm: fila.x,
      posicionYMm: fila.y,
      anchoFinalMm: unidad.ancho,
      altoFinalMm: unidad.alto,
      rotada: unidad.rotada,
    });
    fila.x += unidad.ancho + grosorSierraMm;
  }

  return {
    ok: true,
    unidades: tableros.map((t, i) => ({ numeroTablero: i + 1, distribucion: t.distribucion })),
  };
}

export function cabe(pieza: Pieza, formato: Formato): boolean {
  return orientar(pieza, formato) !== undefined;
}

// Si la pieza se puede girar, se tumba (lado largo a lo ancho) para que las filas sean más bajas,
// y si así no cabe, se prueba girada. Si no se puede girar, su ancho va en el sentido del ancho
// del tablero por la veta.
function orientar(pieza: Pieza, formato: Formato): Medidas | undefined {
  const recta = { ancho: pieza.anchoMm, alto: pieza.altoMm, rotada: false };
  const girada = { ancho: pieza.altoMm, alto: pieza.anchoMm, rotada: true };
  let posibles = [recta];
  if (pieza.rotacionPermitida) {
    posibles = recta.ancho >= recta.alto ? [recta, girada] : [girada, recta];
  }
  return posibles.find((m) => m.ancho <= formato.anchoMm && m.alto <= formato.altoMm);
}

// La fila donde va la pieza. Si hace falta, abre una fila o un tablero nuevos.
function buscarHueco(
  tableros: TableroAbierto[],
  medidas: Medidas,
  formato: Formato,
  grosorSierraMm: number,
): { tablero: TableroAbierto; fila: Fila } {
  // Por el orden, la pieza nunca es más alta que las filas ya abiertas.
  for (const tablero of tableros) {
    const fila = tablero.filas.find((f) => f.x + medidas.ancho <= formato.anchoMm);
    if (fila) {
      return { tablero, fila };
    }
  }

  let tablero = tableros.find((t) => t.y + medidas.alto <= formato.altoMm);
  if (!tablero) {
    tablero = { filas: [], y: 0, distribucion: [] };
    tableros.push(tablero);
  }
  const fila = { y: tablero.y, x: 0 };
  tablero.filas.push(fila);
  tablero.y += medidas.alto + grosorSierraMm;
  return { tablero, fila };
}
