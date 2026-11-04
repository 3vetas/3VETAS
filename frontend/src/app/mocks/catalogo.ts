import { Acabado, Formato, Grosor, Tablero, TipoTablero } from '../models/catalogo';

const melamina: TipoTablero = {
  id: 1,
  nombre: 'Melamina',
  descripcion: 'Aglomerado recubierto de melamina por las dos caras.',
  imagen: '',
};
const mdf: TipoTablero = {
  id: 2,
  nombre: 'MDF',
  descripcion: 'Tablero de fibras de densidad media, liso y fácil de pintar.',
  imagen: '',
};
const contrachapado: TipoTablero = {
  id: 3,
  nombre: 'Contrachapado',
  descripcion: 'Chapas de madera encoladas en capas cruzadas.',
  imagen: '',
};

const blanco: Acabado = {
  id: 1,
  familia: 'Unicolor',
  nombre: 'Blanco',
  colorBase: '#f4f4f2',
  textura: 'Lisa',
  fabricante: 'Genérico',
  codFabricante: 'U-100',
};
const roble: Acabado = {
  id: 2,
  familia: 'Madera',
  nombre: 'Roble natural',
  colorBase: '#c8a27a',
  textura: 'Poro',
  fabricante: 'Genérico',
  codFabricante: 'M-210',
};
const crudo: Acabado = {
  id: 3,
  familia: 'Sin acabado',
  nombre: 'Crudo',
  colorBase: '#b89b74',
  textura: 'Lisa',
  fabricante: 'Genérico',
  codFabricante: 'S-000',
};
const abedul: Acabado = {
  id: 4,
  familia: 'Madera',
  nombre: 'Abedul',
  colorBase: '#e3cfa8',
  textura: 'Natural',
  fabricante: 'Genérico',
  codFabricante: 'M-305',
};

const f2440x1220: Formato = { id: 1, anchoMm: 2440, altoMm: 1220 };
const f2750x1830: Formato = { id: 2, anchoMm: 2750, altoMm: 1830 };
const f2800x2070: Formato = { id: 3, anchoMm: 2800, altoMm: 2070 };
const f2500x1250: Formato = { id: 4, anchoMm: 2500, altoMm: 1250 };

const g10: Grosor = { id: 1, valorMm: 10 };
const g16: Grosor = { id: 2, valorMm: 16 };
const g18: Grosor = { id: 3, valorMm: 18 };
const g19: Grosor = { id: 4, valorMm: 19 };

function tablero(
  id: number,
  tipo: TipoTablero,
  acabado: Acabado,
  grosor: Grosor,
  formato: Formato,
  precioTablero: number,
  stockActual: number,
): Tablero {
  const prefijo = tipo.nombre.slice(0, 3).toUpperCase();
  return {
    id,
    tipo,
    acabado,
    formato,
    grosor,
    referencia: `${prefijo}-${acabado.codFabricante}-${grosor.valorMm}-${formato.anchoMm}`,
    descripcion: `${tipo.nombre} ${acabado.nombre.toLowerCase()} ${grosor.valorMm} mm`,
    precioTablero,
    stockActual,
    activo: true,
  };
}

// El mismo material (tipo, acabado y grosor) aparece en varios formatos para que el planificador
// pueda compararlos. Los formatos grandes salen más baratos por m².
export const TABLEROS: Tablero[] = [
  tablero(1, melamina, blanco, g16, f2440x1220, 35, 40),
  tablero(2, melamina, blanco, g16, f2750x1830, 55, 15),
  tablero(3, melamina, blanco, g16, f2800x2070, 62, 10),
  tablero(4, melamina, blanco, g19, f2440x1220, 39, 25),
  tablero(5, melamina, blanco, g19, f2800x2070, 70, 6),
  tablero(6, melamina, roble, g16, f2440x1220, 42, 20),
  tablero(7, melamina, roble, g16, f2800x2070, 76, 0),
  tablero(8, mdf, crudo, g10, f2440x1220, 22, 30),
  tablero(9, mdf, crudo, g19, f2440x1220, 34, 18),
  tablero(10, mdf, crudo, g19, f2750x1830, 58, 8),
  tablero(11, contrachapado, abedul, g18, f2500x1250, 89, 12),
];
