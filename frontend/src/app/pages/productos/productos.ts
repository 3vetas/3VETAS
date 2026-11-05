import { Component, computed, inject, linkedSignal, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { RouterLink } from '@angular/router';

import { Tablero } from '../../models/catalogo';
import { Catalogo } from '../../services/catalogo';

type Disponibilidad = 'todos' | 'en-stock' | 'sin-stock';

const TABLEROS_POR_PAGINA = 8;

@Component({
  imports: [RouterLink],
  selector: 'app-productos',
  styleUrl: './productos.css',
  templateUrl: './productos.html',
})
export class Productos {
  private readonly tableros = toSignal(inject(Catalogo).obtenerTableros(), { initialValue: [] });
  private readonly activos = computed(() => this.tableros().filter((t) => t.activo));

  // Opciones de los desplegables, sacadas del propio catálogo. 0 significa «todos».
  protected readonly tipos = computed(() => sinRepetir(this.activos().map((t) => t.tipo)));
  protected readonly grosores = computed(() =>
    sinRepetir(this.activos().map((t) => t.grosor)).sort((a, b) => a.valorMm - b.valorMm),
  );

  protected readonly tipoId = signal(0);
  protected readonly grosorId = signal(0);
  protected readonly disponibilidad = signal<Disponibilidad>('todos');

  protected readonly filtrados = computed(() =>
    this.activos().filter(
      (t) =>
        (this.tipoId() === 0 || t.tipo.id === this.tipoId()) &&
        (this.grosorId() === 0 || t.grosor.id === this.grosorId()) &&
        cumpleDisponibilidad(t, this.disponibilidad()),
    ),
  );

  // Vuelve a la primera página cada vez que cambian los filtros.
  protected readonly pagina = linkedSignal({ source: this.filtrados, computation: () => 1 });
  protected readonly paginas = computed(() =>
    Array.from(
      { length: Math.ceil(this.filtrados().length / TABLEROS_POR_PAGINA) },
      (_, i) => i + 1,
    ),
  );
  protected readonly visibles = computed(() => {
    const inicio = (this.pagina() - 1) * TABLEROS_POR_PAGINA;
    return this.filtrados().slice(inicio, inicio + TABLEROS_POR_PAGINA);
  });

  protected readonly euros = new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' });

  protected cambiarDisponibilidad(valor: string): void {
    this.disponibilidad.set(valor as Disponibilidad);
  }

  protected limpiar(): void {
    this.tipoId.set(0);
    this.grosorId.set(0);
    this.disponibilidad.set('todos');
  }
}

function cumpleDisponibilidad(tablero: Tablero, disponibilidad: Disponibilidad): boolean {
  switch (disponibilidad) {
    case 'en-stock':
      return tablero.stockActual > 0;
    case 'sin-stock':
      return tablero.stockActual === 0;
    default:
      return true;
  }
}

// Quita los repetidos por id y conserva el orden del catálogo.
function sinRepetir<T extends { id: number }>(lista: T[]): T[] {
  return lista.filter((item, i) => lista.findIndex((otro) => otro.id === item.id) === i);
}
