import { CambioEstado, Pedido } from '../models/pedido';
import { Pieza, TableroProyecto } from '../models/planificador';
import { Persona } from '../models/usuario';
import { calcularOpciones } from '../pages/planificador/opciones';
import { TABLEROS } from './catalogo';

const IVA = 0.21;

// Cliente con la sesión iniciada mientras no haya login.
export const CLIENTE: Persona = {
  nombre: 'Ana',
  apellidos: 'Martínez López',
  telefono: '600 123 456',
  dni: '12345678Z',
};

const otroCliente: Persona = {
  nombre: 'Mikel',
  apellidos: 'Etxeberria Ruiz',
  telefono: '611 987 654',
  dni: '87654321X',
};

// Las optimizaciones se calculan con el mismo algoritmo que el planificador, como si el
// cliente hubiera dejado la opción recomendada.
function material(idTablero: number, piezas: Pieza[]): TableroProyecto {
  const base = TABLEROS.find((t) => t.id === idTablero)!;
  const candidatos = TABLEROS.filter(
    (t) =>
      t.tipo.id === base.tipo.id &&
      t.acabado.id === base.acabado.id &&
      t.grosor.id === base.grosor.id,
  );
  return {
    tipoTablero: base.tipo,
    acabado: base.acabado,
    grosor: base.grosor,
    piezas,
    optimizaciones: calcularOpciones(piezas, candidatos, null).optimizaciones,
  };
}

function pieza(
  descripcion: string,
  anchoMm: number,
  altoMm: number,
  cantidad: number,
  rotacionPermitida = true,
): Pieza {
  return { descripcion, anchoMm, altoMm, cantidad, rotacionPermitida };
}

function pedido(
  id: number,
  persona: Persona,
  nombre: string,
  descripcion: string,
  tableros: TableroProyecto[],
  historial: CambioEstado[],
): Pedido {
  const baseImponible = tableros
    .flatMap((t) => t.optimizaciones.filter((o) => o.seleccionada))
    .reduce((total, o) => total + o.costeEstimado, 0);
  const importeIva = Math.round(baseImponible * IVA * 100) / 100;
  return {
    id,
    persona,
    proyecto: { nombre, descripcion, tableros },
    estado: historial[historial.length - 1].estado,
    baseImponible,
    descuento: 0,
    importeIva,
    importeFinal: baseImponible + importeIva,
    fechaPedido: historial[0].fechaEstado,
    historial,
  };
}

export const PEDIDOS: Pedido[] = [
  pedido(
    4,
    CLIENTE,
    'Estantería del salón',
    'Estantería blanca con tapa y zócalo de roble.',
    [
      material(1, [pieza('Costado', 1800, 300, 2), pieza('Balda', 768, 300, 5)]),
      material(6, [pieza('Tapa', 800, 320, 1, false), pieza('Zócalo', 800, 100, 1, false)]),
    ],
    [
      { estado: 'aceptado', comentario: '', fechaEstado: '2026-10-20T10:15:00' },
      { estado: 'en_produccion', comentario: '', fechaEstado: '2026-10-27T08:30:00' },
    ],
  ),
  pedido(
    7,
    CLIENTE,
    'Cajonera del taller',
    'Cuatro cajones de MDF para pintar.',
    [
      material(9, [
        pieza('Frente', 500, 200, 4),
        pieza('Lateral', 450, 200, 8),
        pieza('Fondo', 460, 450, 4),
      ]),
    ],
    [
      { estado: 'pendiente', comentario: '', fechaEstado: '2026-10-29T18:40:00' },
      {
        estado: 'rechazado',
        comentario: 'No tendremos MDF de 19 mm hasta dentro de dos semanas.',
        fechaEstado: '2026-10-30T09:05:00',
      },
    ],
  ),
  pedido(
    9,
    CLIENTE,
    'Mesa de estudio',
    'Tablero de contrachapado con patas.',
    [material(11, [pieza('Tablero', 1200, 600, 1, false), pieza('Pata', 700, 80, 4, false)])],
    [{ estado: 'pendiente', comentario: '', fechaEstado: '2026-11-02T20:10:00' }],
  ),
  pedido(
    2,
    CLIENTE,
    'Armario de la entrada',
    'Módulo de armario con dos puertas.',
    [
      material(4, [
        pieza('Costado', 2000, 580, 2),
        pieza('Puerta', 1990, 445, 2),
        pieza('Balda', 862, 560, 3),
      ]),
    ],
    [
      { estado: 'aceptado', comentario: '', fechaEstado: '2026-09-15T12:00:00' },
      { estado: 'en_produccion', comentario: '', fechaEstado: '2026-09-17T08:00:00' },
      { estado: 'finalizado', comentario: '', fechaEstado: '2026-09-18T13:30:00' },
      { estado: 'entregado', comentario: '', fechaEstado: '2026-09-20T11:00:00' },
    ],
  ),
  pedido(
    8,
    otroCliente,
    'Banco de jardín',
    'Asiento y respaldo de contrachapado.',
    [material(11, [pieza('Asiento', 1500, 400, 1, false), pieza('Respaldo', 1500, 300, 1, false)])],
    [{ estado: 'pendiente', comentario: '', fechaEstado: '2026-11-01T17:25:00' }],
  ),
];
