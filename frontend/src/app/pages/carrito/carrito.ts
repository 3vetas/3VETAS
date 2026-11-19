import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

import { LineaCarrito } from '../../models/carrito';
import { CarritoService } from '../../services/carrito';

@Component({
  imports: [RouterLink],
  selector: 'app-carrito',
  styleUrl: './carrito.css',
  templateUrl: './carrito.html',
})
export class Carrito implements OnInit {
  private readonly carritoService = inject(CarritoService);

  protected readonly lineas = signal<LineaCarrito[]>([]);
  protected readonly comentario = signal('');

  protected readonly totalMaterial = computed(() =>
    this.lineas().reduce((total, linea) => total + linea.precioEstimado, 0),
  );

  protected readonly euros = new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' });

  ngOnInit(): void {
    this.carritoService.obtenerLineas().subscribe((lineas) => {
      this.lineas.set(lineas);
    });
  }

  protected quitar(indice: number): void {
    this.lineas.update((lineas) => lineas.filter((_, i) => i !== indice));
  }
}
