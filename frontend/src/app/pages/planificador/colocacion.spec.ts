import { Formato } from '../../models/catalogo';
import { DistribucionPieza, Pieza, UnidadTableroOptimizacion } from '../../models/planificador';
import { colocarPorFilas } from './colocacion';

const formato: Formato = { id: 1, anchoMm: 2440, altoMm: 1220 };
const sierra = 4;

let ultimoId = 0;

function pieza(
  descripcion: string,
  anchoMm: number,
  altoMm: number,
  cantidad = 1,
  rotacionPermitida = true,
): Pieza {
  return { id: ++ultimoId, descripcion, anchoMm, altoMm, cantidad, rotacionPermitida };
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
    a.posicionXmm + a.anchoFinalMm + sierra <= b.posicionXmm ||
    b.posicionXmm + b.anchoFinalMm + sierra <= a.posicionXmm ||
    a.posicionYmm + a.altoFinalMm + sierra <= b.posicionYmm ||
    b.posicionYmm + b.altoFinalMm + sierra <= a.posicionYmm
  );
}

describe('colocarPorFilas', () => {
  it('coloca en un tablero las 6 piezas de 800 × 500 del ejemplo del documento', () => {
    const unidades = colocar([pieza('Lateral', 800, 500, 6)]);

    expect(unidades).toHaveLength(1);
    expect(unidades[0].distribuciones.map((d) => [d.posicionXmm, d.posicionYmm])).toEqual([
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

    for (const { distribuciones } of unidades) {
      for (const [i, a] of distribuciones.entries()) {
        expect(a.posicionXmm).toBeGreaterThanOrEqual(0);
        expect(a.posicionYmm).toBeGreaterThanOrEqual(0);
        expect(a.posicionXmm + a.anchoFinalMm).toBeLessThanOrEqual(formato.anchoMm);
        expect(a.posicionYmm + a.altoFinalMm).toBeLessThanOrEqual(formato.altoMm);
        for (const b of distribuciones.slice(i + 1)) {
          expect(separadas(a, b)).toBe(true);
        }
      }
    }
    expect(unidades.flatMap((u) => u.distribuciones)).toHaveLength(3 + 5 + 4 + 2);
  });

  it('tumba las piezas que se pueden girar y respeta las que no', () => {
    const [giratoria] = colocar([pieza('Puerta', 300, 900)])[0].distribuciones;
    const [fija] = colocar([pieza('Puerta', 300, 900, 1, false)])[0].distribuciones;

    expect(giratoria).toMatchObject({ anchoFinalMm: 900, altoFinalMm: 300, rotada: true });
    expect(fija).toMatchObject({ anchoFinalMm: 300, altoFinalMm: 900, rotada: false });
  });

  it('gira la pieza si tumbada no cabe', () => {
    const vertical: Formato = { id: 2, anchoMm: 1220, altoMm: 2440 };
    const [colocada] = colocar([pieza('Costado', 2000, 500)], vertical)[0].distribuciones;

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

    expect(tablero.distribuciones.at(-1)).toMatchObject({ posicionXmm: 2004, posicionYmm: 0 });
  });

  it('numera las unidades de cada pieza, los tableros y el orden de dibujo', () => {
    const a = pieza('A', 1200, 1200, 3);
    const b = pieza('B', 500, 500, 2);
    const unidades = colocar([a, b]);
    const numerosDe = (p: Pieza) =>
      unidades
        .flatMap((u) => u.distribuciones)
        .filter((d) => d.piezaId === p.id)
        .map((d) => d.numeroUnidad)
        .sort();

    expect(unidades.map((u) => u.numeroTablero)).toEqual([1, 2]);
    expect(numerosDe(a)).toEqual([1, 2, 3]);
    expect(numerosDe(b)).toEqual([1, 2]);
    for (const { distribuciones } of unidades) {
      expect(distribuciones.map((d) => d.ordenDibujo)).toEqual(distribuciones.map((_, i) => i + 1));
    }
  });
});
