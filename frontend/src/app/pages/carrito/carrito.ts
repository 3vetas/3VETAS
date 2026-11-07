import { Component, computed, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

import { CARRITO } from '../../mocks/carrito';

@Component({
  imports: [RouterLink],
  selector: 'app-carrito',
  styleUrl: './carrito.css',
  templateUrl: './carrito.html',
})
export class Carrito {
  // Datos de prueba hasta que se defina el carrito.
  protected readonly lineas = signal(CARRITO);
  protected readonly comentario = signal('');

  protected readonly totalMaterial = computed(() =>
    this.lineas().reduce((total, linea) => total + linea.precioEstimado, 0),
  );

  protected readonly euros = new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' });

  protected quitar(indice: number): void {
    this.lineas.update((lineas) => lineas.filter((_, i) => i !== indice));
  }
}
