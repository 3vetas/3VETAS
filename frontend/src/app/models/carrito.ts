// Provisional: el carrito todavía no está definido en la base de datos ni en la API.
// Cada línea resume un material de un proyecto configurado en el planificador.
export interface LineaCarrito {
  proyecto: string;
  material: string;
  formato: string;
  piezas: number;
  tableros: number;
  precioEstimado: number;
}
