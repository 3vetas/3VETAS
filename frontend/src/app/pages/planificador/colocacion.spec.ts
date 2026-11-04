import { Formato } from '../../models/catalogo';
import { DistribucionPieza, Pieza, UnidadTableroOptimizacion } from '../../models/planificador';
import { colocarPorFilas } from './colocacion';

const formato: Formato = { id: 1, anchoMm: 2440, altoMm: 1220 };
const sierra = 4;

function pieza(
  descripcion: string,
  anchoMm: number,
  altoMm: number,
  cantidad = 1,
  rotacionPermitida = true,
): Pieza {
  return { descripcion, anchoMm, altoMm, cantidad, rotacionPermitida };
}

function colocar(piezas: Pieza[], en = formato): UnidadTableroOptimizacion[] {
  const colocacion = colocarPorFilas(piezas, en, sierra);
  if (!colocacion.ok) {
    throw new Error(colocacion.error);
  }
  return colocacion.unidades;
}

// Dos piezas del mismo tablero quedan separadas al menos por el grosor de sierra.
function separadas(a: DistribucionPieza, b: DistribucionPieza): boolean {
  return (
    a.posicionXMm + a.anchoFinalMm + sierra <= b.posicionXMm ||
    b.posicionXMm + b.anchoFinalMm + sierra <= a.posicionXMm ||
    a.posicionYMm + a.altoFinalMm + sierra <= b.posicionYMm ||
    b.posicionYMm + b.altoFinalMm + sierra <= a.posicionYMm
  );
}

describe('colocarPorFilas', () => {
  it('coloca en un tablero las 6 piezas de 800 × 500 del ejemplo del documento', () => {
    const unidades = colocar([pieza('Lateral', 800, 500, 6)]);

    expect(unidades).toHaveLength(1);
    expect(unidades[0].distribucion.map((d) => [d.posicionXMm, d.posicionYMm])).toEqual([
      [0, 0],
      [804, 0],
      [1608, 0],
      [0, 504],
      [804, 504],
      [1608, 504],
    ]);
  });

  it('no solapa las piezas, deja la sierra entre ellas y no se sale del tablero', () => {
    const piezas = [
      pieza('A', 1200, 700, 3),
      pieza('B', 600, 400, 5),
      pieza('C', 300, 900, 4),
      pieza('D', 2000, 300, 2, false),
    ];
    const unidades = colocar(piezas);

    for (const { distribucion } of unidades) {
      for (const [i, a] of distribucion.entries()) {
        expect(a.posicionXMm).toBeGreaterThanOrEqual(0);
        expect(a.posicionYMm).toBeGreaterThanOrEqual(0);
        expect(a.posicionXMm + a.anchoFinalMm).toBeLessThanOrEqual(formato.anchoMm);
        expect(a.posicionYMm + a.altoFinalMm).toBeLessThanOrEqual(formato.altoMm);
        for (const b of distribucion.slice(i + 1)) {
          expect(separadas(a, b)).toBe(true);
        }
      }
    }
    expect(unidades.flatMap((u) => u.distribucion)).toHaveLength(3 + 5 + 4 + 2);
  });

  it('tumba las piezas que se pueden girar y respeta las que no', () => {
    const [giratoria] = colocar([pieza('Puerta', 300, 900)])[0].distribucion;
    const [fija] = colocar([pieza('Puerta', 300, 900, 1, false)])[0].distribucion;

    expect(giratoria).toMatchObject({ anchoFinalMm: 900, altoFinalMm: 300, rotada: true });
    expect(fija).toMatchObject({ anchoFinalMm: 300, altoFinalMm: 900, rotada: false });
  });

  it('gira la pieza si tumbada no cabe', () => {
    const vertical: Formato = { id: 2, anchoMm: 1220, altoMm: 2440 };
    const [colocada] = colocar([pieza('Costado', 2000, 500)], vertical)[0].distribucion;

    expect(colocada).toMatchObject({ anchoFinalMm: 500, altoFinalMm: 2000, rotada: true });
  });

  it('devuelve un error si una pieza no cabe ni girada', () => {
    expect(colocarPorFilas([pieza('Enorme', 3000, 500)], formato, sierra)).toEqual({
      ok: false,
      error: 'No cabe «Enorme»',
    });
    expect(colocarPorFilas([pieza('Alta', 500, 2000, 1, false)], formato, sierra)).toEqual({
      ok: false,
      error: 'No cabe «Alta»',
    });
  });

  it('cuenta bien los tableros', () => {
    expect(colocar([pieza('Entero', 2440, 1220, 3)])).toHaveLength(3);
    // Caben dos por tablero: 1200 + 4 + 1200 = 2404 a lo ancho, pero a lo alto solo una fila.
    expect(colocar([pieza('Cuadrado', 1200, 1200, 5)])).toHaveLength(3);
  });

  it('aprovecha el hueco que queda en una fila ya abierta', () => {
    const [tablero] = colocar([pieza('Larga', 2000, 600, 2), pieza('Taco', 400, 300)]);

    expect(tablero.distribucion.at(-1)).toMatchObject({ posicionXMm: 2004, posicionYMm: 0 });
  });

  it('numera las unidades de cada pieza, los tableros y el orden de dibujo', () => {
    const unidades = colocar([pieza('A', 1200, 1200, 3), pieza('B', 500, 500, 2)]);
    const numerosDe = (indicePieza: number) =>
      unidades
        .flatMap((u) => u.distribucion)
        .filter((d) => d.indicePieza === indicePieza)
        .map((d) => d.numeroUnidad)
        .sort();

    expect(unidades.map((u) => u.numeroTablero)).toEqual([1, 2]);
    expect(numerosDe(0)).toEqual([1, 2, 3]);
    expect(numerosDe(1)).toEqual([1, 2]);
    for (const { distribucion } of unidades) {
      expect(distribucion.map((d) => d.ordenDibujo)).toEqual(distribucion.map((_, i) => i + 1));
    }
  });
});
