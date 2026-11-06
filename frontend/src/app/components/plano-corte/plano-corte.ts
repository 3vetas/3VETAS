import { Component, input } from '@angular/core';

import { Formato } from '../../models/catalogo';
import { DistribucionPieza, Pieza, UnidadTableroOptimizacion } from '../../models/planificador';

// Dibuja un tablero físico con sus piezas. El SVG trabaja en milímetros, sin convertir escalas.
@Component({
  selector: 'app-plano-corte',
  styleUrl: './plano-corte.css',
  templateUrl: './plano-corte.html',
})
export class PlanoCorte {
  readonly formato = input.required<Formato>();
  readonly unidad = input.required<UnidadTableroOptimizacion>();
  // Piezas del material: la distribución las identifica por su posición en esta lista.
  readonly piezas = input.required<Pieza[]>();
  readonly color = input.required<string>();

  // Letra en mm para que la descripción y las medidas quepan dentro de la pieza dibujada.
  protected tamanoLetra(colocada: DistribucionPieza, pieza: Pieza): number {
    const caracteres = Math.max(
      pieza.descripcion.length,
      `${pieza.anchoMm} × ${pieza.altoMm} ↻`.length,
    );
    return Math.min(70, colocada.altoFinalMm / 3, colocada.anchoFinalMm / (0.6 * caracteres));
  }
}
