import { ACABADOS, FORMATOS, GROSORES, TABLEROS, TIPOS_TABLERO } from '../mocks/catalogo';
import { completarTableros } from '../services/catalogo';
import { TableroCompleto } from './catalogo';
import { completarPedido, Pedido, Proyecto } from './pedido';
import { Optimizacion, TableroProyecto, UnidadTableroOptimizacion } from './planificador';
import { Persona } from './usuario';

const tableros = completarTableros({
  tableros: TABLEROS,
  tipos: TIPOS_TABLERO,
  acabados: ACABADOS,
  formatos: FORMATOS,
  grosores: GROSORES,
});
// Melamina blanca de 16 mm en dos formatos y melamina de roble de 16 mm.
const blancoPequeno = tableros[0];
const blancoGrande = tableros[1];
const roble = tableros[5];

function material(id: number, tablero: TableroCompleto): TableroProyecto {
  return {
    id,
    tipoTableroId: tablero.tipoTableroId,
    acabadoId: tablero.acabadoId,
    grosorId: tablero.grosorId,
    piezas: [],
  };
}

function unidad(numeroTablero: number): UnidadTableroOptimizacion {
  return { numeroTablero, distribuciones: [] };
}

function optimizacion(
  id: number,
  tableroProyectoId: number,
  tablero: TableroCompleto,
  unidades: UnidadTableroOptimizacion[],
): Optimizacion {
  const precio = unidades.length * tablero.precioTablero;
  return {
    id,
    tableroProyectoId,
    nombreOpcion: `${tablero.formato.anchoMm} × ${tablero.formato.altoMm}`,
    superficieUtilizada: 0,
    superficieDesperdiciada: 0,
    porcentajeAprovechamiento: 0,
    costeEstimado: precio,
    seleccionada: true,
    detalles: [{ tableroId: tablero.id, cantidad: unidades.length, precio, unidades }],
  };
}

describe('completarPedido', () => {
  const ana: Persona = { id: 1, nombre: 'Ana', apellidos: 'Martínez', telefono: '', dni: '' };
  const proyecto: Proyecto = {
    id: 5,
    usuarioId: 1,
    nombre: 'Estantería',
    descripcion: '',
    estado: 'pedido',
    fechaCreacion: '2026-10-20T10:15:00',
    fechaModificacion: '2026-10-20T10:15:00',
    tableros: [material(11, blancoPequeno), material(12, roble)],
  };
  const pedido: Pedido = {
    id: 7,
    personaId: 1,
    proyectoId: 5,
    direccionEnvioId: 1,
    direccionFacturacionId: 2,
    estado: 'pendiente',
    baseImponible: 139,
    descuento: 0,
    importeIva: 29.19,
    importeFinal: 168.19,
    fechaPedido: '2026-10-20T10:15:00',
    fechaActualizacion: '2026-10-20T10:15:00',
    lineas: [
      { id: 1, optimizacionId: 21, importe: 55 },
      { id: 2, optimizacionId: 23, importe: 84 },
    ],
    historialEstados: [],
  };
  const datos = {
    personas: [ana],
    proyectos: [proyecto],
    tableros,
    optimizaciones: [
      optimizacion(21, 11, blancoGrande, [unidad(1)]),
      // Otra opción del primer material que el cliente no compró.
      optimizacion(22, 11, blancoPequeno, [unidad(1), unidad(2), unidad(3)]),
      optimizacion(23, 12, roble, [unidad(1), unidad(2)]),
    ],
  };

  it('añade al pedido su cliente y su proyecto', () => {
    const completo = completarPedido(pedido, datos);

    expect(completo.persona).toBe(ana);
    expect(completo.proyecto).toBe(proyecto);
  });

  it('añade a cada material su tipo, acabado y grosor del catálogo', () => {
    const [primero, segundo] = completarPedido(pedido, datos).materiales;

    expect(primero).toMatchObject({
      tipoTablero: blancoPequeno.tipoTablero,
      acabado: blancoPequeno.acabado,
      grosor: blancoPequeno.grosor,
    });
    expect(segundo.acabado).toBe(roble.acabado);
  });

  it('da a cada material los tableros físicos de la optimización que se compró', () => {
    const [primero, segundo] = completarPedido(pedido, datos).materiales;

    expect(primero.planos).toEqual([
      { formato: blancoGrande.formato, unidad: unidad(1), cantidad: 1 },
    ]);
    expect(segundo.planos).toEqual([
      { formato: roble.formato, unidad: unidad(1), cantidad: 2 },
      { formato: roble.formato, unidad: unidad(2), cantidad: 2 },
    ]);
  });
});
