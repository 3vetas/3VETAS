import { TableroProyecto } from './planificador';
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

// El backend devuelve el pedido solo con los id de su cliente, su proyecto y sus direcciones.
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
  historialEstados: PedidoEstado[];
}

// El pedido con su cliente y su proyecto ya reunidos para mostrarlo.
export interface PedidoCompleto extends Pedido {
  persona: Persona;
  proyecto: Proyecto;
}

// Número que ve el cliente, por ejemplo #0007.
export function numeroPedido(pedido: Pedido): string {
  return `#${String(pedido.id).padStart(4, '0')}`;
}
