import { Tablero } from '../../models/catalogo';
import { Optimizacion, Pieza } from '../../models/planificador';
import { colocarPorFilas } from './colocacion';

export interface FormatoDescartado {
  tablero: Tablero;
  motivo: string;
}

export interface Opciones {
  // De la más barata a la más cara: la primera es la recomendada.
  optimizaciones: Optimizacion[];
  // La que eligió el cliente si sigue siendo posible; si no, la recomendada.
  elegida: Optimizacion | undefined;
  descartados: FormatoDescartado[];
}

// Coloca las piezas en cada tablero del material, es decir, en cada formato, y compara el resultado.
export function calcularOpciones(
  piezas: Pieza[],
  tableros: Tablero[],
  opcionElegida: string | null,
): Opciones {
  const superficiePiezas = piezas.reduce(
    (total, p) => total + p.anchoMm * p.altoMm * p.cantidad,
    0,
  );
  const optimizaciones: Optimizacion[] = [];
  const descartados: FormatoDescartado[] = [];

  for (const tablero of tableros) {
    const colocacion = colocarPorFilas(piezas, tablero.formato);
    if (!colocacion.ok) {
      descartados.push({ tablero, motivo: colocacion.error });
      continue;
    }
    const cantidad = colocacion.unidades.length;
    if (tablero.stockActual < cantidad) {
      const motivo = tablero.stockActual === 0 ? 'Sin stock' : `Solo quedan ${tablero.stockActual}`;
      descartados.push({ tablero, motivo });
      continue;
    }
    const { anchoMm, altoMm } = tablero.formato;
    const superficieTableros = cantidad * anchoMm * altoMm;
    optimizaciones.push({
      nombreOpcion: `${anchoMm} × ${altoMm}`,
      superficieUtilizada: superficiePiezas,
      superficieDesperdiciada: superficieTableros - superficiePiezas,
      porcentajeAprovechamiento: (superficiePiezas / superficieTableros) * 100,
      costeEstimado: cantidad * tablero.precioTablero,
      seleccionada: false,
      detalles: [
        { tablero, cantidad, precio: tablero.precioTablero, unidades: colocacion.unidades },
      ],
    });
  }

  // La más barata y, si empatan, la que más aprovecha el tablero.
  optimizaciones.sort(
    (a, b) =>
      a.costeEstimado - b.costeEstimado ||
      b.porcentajeAprovechamiento - a.porcentajeAprovechamiento,
  );
  const elegida =
    optimizaciones.find((o) => o.nombreOpcion === opcionElegida) ?? optimizaciones.at(0);
  if (elegida) {
    elegida.seleccionada = true;
  }
  return { optimizaciones, elegida, descartados };
}
