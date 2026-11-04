import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  imports: [RouterLink, RouterLinkActive],
  selector: 'app-header',
  styleUrl: './header.css',
  templateUrl: './header.html',
})
export class Header {
  protected readonly enlaces = [
    { ruta: '/', texto: 'Inicio' },
    { ruta: '/productos', texto: 'Catálogo' },
    { ruta: '/planificador', texto: 'Planificador' },
    { ruta: '/carrito', texto: 'Carrito' },
    { ruta: '/pedidos', texto: 'Mis pedidos' },
    { ruta: '/login', texto: 'Acceso' },
  ];
}
