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

export const CARRITO: LineaCarrito[] = [
  {
    proyecto: 'Estantería del salón',
    material: 'Melamina · Blanco · 16 mm',
    formato: '2440 × 1220 mm',
    piezas: 9,
    tableros: 2,
    precioEstimado: 70,
  },
  {
    proyecto: 'Estantería del salón',
    material: 'Melamina · Roble natural · 16 mm',
    formato: '2440 × 1220 mm',
    piezas: 4,
    tableros: 1,
    precioEstimado: 42,
  },
  {
    proyecto: 'Cajonera del taller',
    material: 'MDF · Crudo · 19 mm',
    formato: '2750 × 1830 mm',
    piezas: 14,
    tableros: 1,
    precioEstimado: 58,
  },
];
