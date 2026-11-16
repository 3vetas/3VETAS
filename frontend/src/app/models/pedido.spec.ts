import { ACABADOS, FORMATOS, GROSORES, TABLEROS, TIPOS_TABLERO } from '../mocks/catalogo';
import { completarTableros } from '../services/catalogo';
import { completarPedido, Pedido, Proyecto } from './pedido';
import { Optimizacion, UnidadTableroOptimizacion } from './planificador';
import { Persona } from './usuario';

const [tablero] = completarTableros({
  tableros: TABLEROS,
  tipos: TIPOS_TABLERO,
  acabados: ACABADOS,
  formatos: FORMATOS,
  grosores: GROSORES,
});

function unidad(numeroTablero: number): UnidadTableroOptimizacion {
  return { numeroTablero, distribuciones: [] };
}

function optimizacion(
  id: number,
  tableroProyectoId: number,
  unidades: UnidadTableroOptimizacion[],
): Optimizacion {
  const precio = unidades.length * tablero.precioTablero;
  return {
    id,
    tableroProyectoId,
    nombreOpcion: '2440 × 1220',
    superficieUtilizada: 0,
    superficieDesperdiciada: 0,
    porcentajeAprovechamiento: 0,
    costeEstimado: precio,
    seleccionada: true,
    detalles: [{ tablero, cantidad: unidades.length, precio, unidades }],
  };
}

describe('completarPedido', () => {
  const ana: Persona = { id: 1, nombre: 'Ana', apellidos: 'Martínez', telefono: '', dni: '' };
  const material = {
    tipoTablero: TIPOS_TABLERO[0],
    acabado: ACABADOS[0],
    grosor: GROSORES[0],
    piezas: [],
  };
  const proyecto: Proyecto = {
    id: 5,
    usuarioId: 1,
    nombre: 'Estantería',
    descripcion: '',
    estado: 'pedido',
    fechaCreacion: '2026-10-20T10:15:00',
    fechaModificacion: '2026-10-20T10:15:00',
    tableros: [
      { ...material, id: 11 },
      { ...material, id: 12 },
    ],
  };
  const pedido: Pedido = {
    id: 7,
    personaId: 1,
    proyectoId: 5,
    direccionEnvioId: 1,
    direccionFacturacionId: 2,
    estado: 'pendiente',
    baseImponible: 105,
    descuento: 0,
    importeIva: 22.05,
    importeFinal: 127.05,
    fechaPedido: '2026-10-20T10:15:00',
    fechaActualizacion: '2026-10-20T10:15:00',
    lineas: [
      { id: 1, optimizacionId: 21, importe: 35 },
      { id: 2, optimizacionId: 23, importe: 70 },
    ],
    historialEstados: [],
  };
  const datos = {
    personas: [ana],
    proyectos: [proyecto],
    optimizaciones: [
      optimizacion(21, 11, [unidad(1)]),
      // Otra opción del primer material que el cliente no compró.
      optimizacion(22, 11, [unidad(1), unidad(2), unidad(3)]),
      optimizacion(23, 12, [unidad(1), unidad(2)]),
    ],
  };

  it('añade al pedido su cliente y su proyecto', () => {
    const completo = completarPedido(pedido, datos);

    expect(completo.persona).toBe(ana);
    expect(completo.proyecto).toBe(proyecto);
  });

  it('da a cada material los tableros físicos de la optimización que se compró', () => {
    const [primero, segundo] = completarPedido(pedido, datos).materiales;

    expect(primero.planos).toEqual([{ formato: tablero.formato, unidad: unidad(1), cantidad: 1 }]);
    expect(segundo.planos).toEqual([
      { formato: tablero.formato, unidad: unidad(1), cantidad: 2 },
      { formato: tablero.formato, unidad: unidad(2), cantidad: 2 },
    ]);
  });
});
