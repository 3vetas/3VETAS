import { Routes } from '@angular/router';

import { Admin } from './pages/admin/admin';
import { Carrito } from './pages/carrito/carrito';
import { Inicio } from './pages/inicio/inicio';
import { Login } from './pages/login/login';
import { PedidoDetalle } from './pages/pedido-detalle/pedido-detalle';
import { Pedidos } from './pages/pedidos/pedidos';
import { Planificador } from './pages/planificador/planificador';
import { Productos } from './pages/productos/productos';
import { Registro } from './pages/registro/registro';

export const routes: Routes = [
  { path: '', component: Inicio, title: '3Vetas' },
  { path: 'index', redirectTo: '', pathMatch: 'full' },
  { path: 'productos', component: Productos, title: 'Catálogo · 3Vetas' },
  { path: 'login', component: Login, title: 'Acceso · 3Vetas' },
  { path: 'registro', component: Registro, title: 'Registro · 3Vetas' },
  { path: 'planificador', component: Planificador, title: 'Planificador · 3Vetas' },
  { path: 'carrito', component: Carrito, title: 'Carrito · 3Vetas' },
  { path: 'pedidos', component: Pedidos, title: 'Mis pedidos · 3Vetas' },
  { path: 'pedido/:id', component: PedidoDetalle, title: 'Pedido · 3Vetas' },
  { path: 'admin', component: Admin, title: 'Administración · 3Vetas' },
  { path: '**', redirectTo: '' },
];
