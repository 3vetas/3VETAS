import { TABLEROS } from '../../mocks/catalogo';
import { Tablero } from '../../models/catalogo';
import { Pieza } from '../../models/planificador';
import { calcularOpciones } from './opciones';

function tablero(
  id: number,
  anchoMm: number,
  altoMm: number,
  precioTablero: number,
  stockActual = 10,
): Tablero {
  return { ...TABLEROS[0], id, formato: { id, anchoMm, altoMm }, precioTablero, stockActual };
}

function pieza(descripcion: string, anchoMm: number, altoMm: number, cantidad = 1): Pieza {
  return { descripcion, anchoMm, altoMm, cantidad, rotacionPermitida: true };
}

const pequeno = tablero(1, 2440, 1220, 35);
const grande = tablero(2, 2800, 2070, 62);
const laterales = [pieza('Lateral', 800, 500, 6)];

describe('calcularOpciones', () => {
  it('calcula la cantidad, las superficies y el coste de cada formato', () => {
    const [opcion] = calcularOpciones(laterales, [pequeno], null).optimizaciones;

    expect(opcion).toMatchObject({
      nombreOpcion: '2440 × 1220',
      superficieUtilizada: 2_400_000,
      superficieDesperdiciada: 576_800,
      costeEstimado: 35,
      seleccionada: true,
    });
    expect(opcion.porcentajeAprovechamiento).toBeCloseTo(80.62);
    expect(opcion.detalles).toHaveLength(1);
    expect(opcion.detalles[0]).toMatchObject({ tablero: pequeno, cantidad: 1, precio: 35 });
    expect(opcion.detalles[0].unidades[0].distribucion).toHaveLength(6);
  });

  it('recomienda la opción más barata', () => {
    const { optimizaciones, elegida } = calcularOpciones(laterales, [grande, pequeno], null);

    expect(optimizaciones.map((o) => o.nombreOpcion)).toEqual(['2440 × 1220', '2800 × 2070']);
    expect(optimizaciones.map((o) => o.seleccionada)).toEqual([true, false]);
    expect(elegida).toBe(optimizaciones[0]);
  });

  it('si cuestan lo mismo, recomienda la que más aprovecha el tablero', () => {
    const grandeAlMismoPrecio = tablero(2, 2800, 2070, 35);
    const { elegida } = calcularOpciones(laterales, [grandeAlMismoPrecio, pequeno], null);

    expect(elegida?.nombreOpcion).toBe('2440 × 1220');
  });

  it('respeta la opción del cliente mientras sea posible', () => {
    const tableros = [pequeno, grande];

    expect(calcularOpciones(laterales, tableros, '2800 × 2070').elegida?.nombreOpcion).toBe(
      '2800 × 2070',
    );
    expect(calcularOpciones(laterales, tableros, '9999 × 9999').elegida?.nombreOpcion).toBe(
      '2440 × 1220',
    );
  });

  it('descarta los formatos en los que no cabe alguna pieza o no hay stock suficiente', () => {
    const sinStock = tablero(3, 2750, 1830, 55, 0);
    const piezas = [...laterales, pieza('Encimera', 2600, 600)];
    const { optimizaciones, descartados } = calcularOpciones(
      piezas,
      [pequeno, grande, sinStock],
      null,
    );

    expect(optimizaciones.map((o) => o.nombreOpcion)).toEqual(['2800 × 2070']);
    expect(descartados).toEqual([
      { tablero: pequeno, motivo: 'No cabe «Encimera»' },
      { tablero: sinStock, motivo: 'Sin stock' },
    ]);

    const pocoStock = tablero(4, 2440, 1220, 35, 2);
    expect(calcularOpciones([pieza('Entero', 2440, 1220, 3)], [pocoStock], null)).toEqual({
      optimizaciones: [],
      elegida: undefined,
      descartados: [{ tablero: pocoStock, motivo: 'Solo quedan 2' }],
    });
  });
});
