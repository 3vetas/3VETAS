import { Pedido, PedidoCompleto, PedidoEstado, Proyecto } from '../models/pedido';
import { Pieza, TableroProyecto } from '../models/planificador';
import { Persona } from '../models/usuario';
import { calcularOpciones } from '../pages/planificador/opciones';
import { completarTableros } from '../services/catalogo';
import { ACABADOS, FORMATOS, GROSORES, TABLEROS, TIPOS_TABLERO } from './catalogo';

const IVA = 0.21;

// Usuario del taller con la sesión iniciada mientras no haya login.
export const ID_USUARIO_TALLER = 3;

// Un cambio de estado sin los datos que pone el backend.
type Cambio = Omit<PedidoEstado, 'id' | 'usuarioId'>;

const tableros = completarTableros({
  tableros: TABLEROS,
  tipos: TIPOS_TABLERO,
  acabados: ACABADOS,
  formatos: FORMATOS,
  grosores: GROSORES,
});

export const PERSONAS: Persona[] = [
  { id: 1, nombre: 'Ana', apellidos: 'Martínez López', telefono: '600 123 456', dni: '12345678Z' },
  {
    id: 2,
    nombre: 'Mikel',
    apellidos: 'Etxeberria Ruiz',
    telefono: '611 987 654',
    dni: '87654321X',
  },
];

// Cliente con la sesión iniciada mientras no haya login.
export const CLIENTE = PERSONAS[0];
const otroCliente = PERSONAS[1];

// Las optimizaciones se calculan con el mismo algoritmo que el planificador, como si el
// cliente hubiera dejado la opción recomendada.
function material(idTablero: number, piezas: Pieza[]): TableroProyecto {
  const base = tableros.find((t) => t.id === idTablero)!;
  const candidatos = tableros.filter(
    (t) =>
      t.tipoTableroId === base.tipoTableroId &&
      t.acabadoId === base.acabadoId &&
      t.grosorId === base.grosorId,
  );
  return {
    tipoTablero: base.tipoTablero,
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
  historial: Cambio[],
): { pedido: Pedido; proyecto: Proyecto } {
  const baseImponible = tableros
    .flatMap((t) => t.optimizaciones.filter((o) => o.seleccionada))
    .reduce((total, o) => total + o.costeEstimado, 0);
  const importeIva = Math.round(baseImponible * IVA * 100) / 100;
  const fechaPedido = historial[0].fechaEstado;
  const ultimoCambio = historial[historial.length - 1];
  const proyecto: Proyecto = {
    id: 100 + id,
    // En los datos de prueba el usuario tiene el mismo id que su persona.
    usuarioId: persona.id,
    nombre,
    descripcion,
    estado: 'pedido',
    fechaCreacion: fechaPedido,
    fechaModificacion: fechaPedido,
    tableros,
  };
  const pedido: Pedido = {
    id,
    personaId: persona.id,
    proyectoId: proyecto.id,
    direccionEnvioId: persona.id * 10 + 1,
    direccionFacturacionId: persona.id * 10 + 2,
    estado: ultimoCambio.estado,
    baseImponible,
    descuento: 0,
    importeIva,
    importeFinal: baseImponible + importeIva,
    fechaPedido,
    fechaActualizacion: ultimoCambio.fechaEstado,
    historialEstados: historial.map((cambio, i) => ({
      id: id * 10 + i + 1,
      usuarioId: ID_USUARIO_TALLER,
      ...cambio,
    })),
  };
  return { pedido, proyecto };
}

const datos = [
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

export const PROYECTOS: Proyecto[] = datos.map((d) => d.proyecto);

// Reúne lo que el backend devuelve por separado. Cuando existan los endpoints lo hará un servicio.
export const PEDIDOS: PedidoCompleto[] = datos.map(({ pedido }) => ({
  ...pedido,
  persona: PERSONAS.find((p) => p.id === pedido.personaId)!,
  proyecto: PROYECTOS.find((p) => p.id === pedido.proyectoId)!,
}));
