import { Formato, TableroCompleto } from './catalogo';
import {
  MaterialCompleto,
  Optimizacion,
  TableroProyecto,
  UnidadTableroOptimizacion,
} from './planificador';
import { Persona } from './usuario';

// Estados de PEDIDOS.estado en la BBDD revisada.
export type EstadoPedido =
  | 'pendiente'
  | 'aceptado'
  | 'en_produccion'
  | 'finalizado'
  | 'entregado'
  | 'cancelado'
  | 'rechazado';

export const NOMBRES_ESTADO: Record<EstadoPedido, string> = {
  pendiente: 'Pendiente',
  aceptado: 'Aceptado',
  en_produccion: 'En producción',
  finalizado: 'Finalizado',
  entregado: 'Entregado',
  cancelado: 'Cancelado',
  rechazado: 'Rechazado',
};

export interface Proyecto {
  id: number;
  usuarioId: number;
  nombre: string;
  descripcion: string;
  // Sus valores todavía no están definidos en el backend.
  estado: string;
  fechaCreacion: string;
  fechaModificacion: string;
  tableros: TableroProyecto[];
}

// Una fila de PEDIDO_ESTADOS: cada cambio de estado queda en el historial.
export interface PedidoEstado {
  id: number;
  estado: EstadoPedido;
  comentario: string;
  // Usuario que hizo el cambio.
  usuarioId: number;
  fechaEstado: string;
}

// Una optimización comprada en el pedido.
export interface LineaPedido {
  id: number;
  optimizacionId: number;
  importe: number;
}

// El backend devuelve el pedido solo con los id de su cliente, su proyecto, sus direcciones
// y sus optimizaciones.
export interface Pedido {
  id: number;
  personaId: number;
  proyectoId: number;
  direccionEnvioId: number;
  direccionFacturacionId: number;
  estado: EstadoPedido;
  baseImponible: number;
  descuento: number;
  importeIva: number;
  importeFinal: number;
  fechaPedido: string;
  fechaActualizacion: string;
  lineas: LineaPedido[];
  historialEstados: PedidoEstado[];
}

// El pedido con su cliente, su proyecto y sus planos de corte ya reunidos para mostrarlo.
export interface PedidoCompleto extends Pedido {
  persona: Persona;
  proyecto: Proyecto;
  materiales: MaterialPedido[];
}

// Un material del proyecto con los tableros físicos de la optimización que se compró.
export interface MaterialPedido extends MaterialCompleto {
  planos: PlanoTablero[];
}

export interface PlanoTablero {
  formato: Formato;
  unidad: UnidadTableroOptimizacion;
  // Tableros de ese formato que lleva el material.
  cantidad: number;
}

// Lo que el backend devuelve en peticiones aparte.
export interface DatosPedido {
  personas: Persona[];
  proyectos: Proyecto[];
  optimizaciones: Optimizacion[];
  tableros: TableroCompleto[];
}

export function completarPedido(pedido: Pedido, datos: DatosPedido): PedidoCompleto {
  const proyecto = buscar(datos.proyectos, pedido.proyectoId);
  const compradas = pedido.lineas.map((linea) =>
    datos.optimizaciones.find((o) => o.id === linea.optimizacionId),
  );
  return {
    ...pedido,
    persona: buscar(datos.personas, pedido.personaId),
    proyecto,
    materiales: proyecto.tableros.map((material) => ({
      ...completarMaterial(material, datos.tableros),
      planos: compradas
        .filter((o) => o?.tableroProyectoId === material.id)
        .flatMap((o) => o?.detalles ?? [])
        .flatMap((detalle) =>
          detalle.unidades.map((unidad) => ({
            formato: buscar(datos.tableros, detalle.tableroId).formato,
            unidad,
            cantidad: detalle.cantidad,
          })),
        ),
    })),
  };
}

// Cualquier tablero del catálogo que sea de ese material sirve para conocer sus características.
function completarMaterial(
  material: TableroProyecto,
  tableros: TableroCompleto[],
): MaterialCompleto {
  const { tipoTablero, acabado, grosor } = tableros.find(
    (t) =>
      t.tipoTableroId === material.tipoTableroId &&
      t.acabadoId === material.acabadoId &&
      t.grosorId === material.grosorId,
  )!;
  return { ...material, tipoTablero, acabado, grosor };
}

// Las claves ajenas de la base de datos garantizan que el id existe.
function buscar<T extends { id: number }>(lista: T[], id: number): T {
  return lista.find((item) => item.id === id)!;
}

// Número que ve el cliente, por ejemplo #0007.
export function numeroPedido(pedido: Pedido): string {
  return `#${String(pedido.id).padStart(4, '0')}`;
}
