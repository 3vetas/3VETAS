import { Rol, Usuario } from '../models/usuario';
import { CLIENTE } from './pedidos';

const cliente: Rol = { id: 1, nombre: 'Cliente' };
const produccion: Rol = { id: 2, nombre: 'Producción' };
const administracion: Rol = { id: 3, nombre: 'Administración' };

export const USUARIOS: Usuario[] = [
  { email: 'ana.martinez@example.com', rol: cliente, activo: true, persona: CLIENTE },
  {
    email: 'mikel.etxeberria@example.com',
    rol: cliente,
    activo: true,
    persona: { nombre: 'Mikel', apellidos: 'Etxeberria Ruiz', telefono: '611 987 654', dni: '' },
  },
  {
    email: 'iratxe.taller@example.com',
    rol: produccion,
    activo: true,
    persona: { nombre: 'Iratxe', apellidos: 'Gómez Sanz', telefono: '', dni: '' },
  },
  {
    email: 'jon.admin@example.com',
    rol: administracion,
    activo: true,
    persona: { nombre: 'Jon', apellidos: 'Arrieta Olano', telefono: '', dni: '' },
  },
  {
    email: 'pedro.antiguo@example.com',
    rol: cliente,
    activo: false,
    persona: { nombre: 'Pedro', apellidos: 'Sáez Vidal', telefono: '', dni: '' },
  },
];
