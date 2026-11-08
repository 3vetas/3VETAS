// Los id y las fechas de estas tablas los asigna el backend.

export interface Rol {
  id: number;
  nombre: string;
}

export interface Persona {
  nombre: string;
  apellidos: string;
  telefono: string;
  dni: string;
}

export interface Usuario {
  email: string;
  rol: Rol;
  activo: boolean;
  persona: Persona;
}
