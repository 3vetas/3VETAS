import { Acabado, Formato, Grosor, Tablero, TipoTablero } from '../models/catalogo';
import { completarTableros } from './catalogo';

describe('completarTableros', () => {
  it('añade a cada tablero su tipo, acabado, formato y grosor a partir de sus id', () => {
    const melamina: TipoTablero = { id: 1, nombre: 'Melamina', descripcion: '', imagen: '' };
    const mdf: TipoTablero = { id: 2, nombre: 'MDF', descripcion: '', imagen: '' };
    const blanco: Acabado = {
      id: 5,
      familia: 'Unicolor',
      nombre: 'Blanco',
      colorBase: '#f4f4f2',
      textura: 'Lisa',
      fabricante: 'Genérico',
      codFabricante: 'U-100',
    };
    const pequeno: Formato = { id: 3, anchoMm: 2440, altoMm: 1220 };
    const grande: Formato = { id: 4, anchoMm: 2800, altoMm: 2070 };
    const g16: Grosor = { id: 7, espesorMm: 16 };
    const tablero: Tablero = {
      id: 9,
      tipoTableroId: 2,
      acabadoId: 5,
      formatoId: 4,
      grosorId: 7,
      referencia: 'MDF-U-100-16-2800',
      descripcion: 'MDF blanco 16 mm',
      precioTablero: 62,
      stockActual: 10,
      activo: true,
    };

    const completos = completarTableros({
      tableros: [tablero],
      tipos: [melamina, mdf],
      acabados: [blanco],
      formatos: [pequeno, grande],
      grosores: [g16],
    });

    expect(completos).toEqual([
      { ...tablero, tipoTablero: mdf, acabado: blanco, formato: grande, grosor: g16 },
    ]);
  });
});
