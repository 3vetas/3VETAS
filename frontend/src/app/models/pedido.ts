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
  nombre: string;
  descripcion: string;
  tableros: TableroProyecto[];
}

// Una fila de PEDIDO_ESTADOS: cada cambio de estado queda en el historial.
export interface CambioEstado {
  estado: EstadoPedido;
  comentario: string;
  fechaEstado: string;
}

export interface Pedido {
  id: number;
  persona: Persona;
  proyecto: Proyecto;
  estado: EstadoPedido;
  baseImponible: number;
  descuento: number;
  importeIva: number;
  importeFinal: number;
  fechaPedido: string;
  historial: CambioEstado[];
}

// Número que ve el cliente, por ejemplo #0007.
export function numeroPedido(pedido: Pedido): string {
  return `#${String(pedido.id).padStart(4, '0')}`;
}
