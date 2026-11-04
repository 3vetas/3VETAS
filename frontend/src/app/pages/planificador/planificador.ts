import {
  Component,
  computed,
  ElementRef,
  inject,
  linkedSignal,
  signal,
  viewChild,
} from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { form, FormField, FormRoot, required, SchemaPath, validate } from '@angular/forms/signals';

import { Tablero } from '../../models/catalogo';
import { DistribucionPieza, Optimizacion, Pieza, TableroProyecto } from '../../models/planificador';
import { Catalogo } from '../../services/catalogo';
import { cabe, GROSOR_SIERRA_MM } from './colocacion';
import { calcularOpciones } from './opciones';

// Un material del proyecto con sus piezas. Sus optimizaciones se calculan a partir de ellas.
interface Material extends Omit<TableroProyecto, 'optimizaciones'> {
  // Opción que eligió el cliente, o null para usar la recomendada.
  opcionElegida: string | null;
}

@Component({
  imports: [FormField, FormRoot],
  selector: 'app-planificador',
  styleUrl: './planificador.css',
  templateUrl: './planificador.html',
})
export class Planificador {
  private readonly catalogo = toSignal(inject(Catalogo).obtenerTableros(), { initialValue: [] });
  private readonly tableros = computed(() => this.catalogo().filter((t) => t.activo));

  protected readonly materiales = signal<Material[]>([]);

  // Cada material con las opciones de tablero calculadas para sus piezas.
  protected readonly bloques = computed(() =>
    this.materiales().map((material) => ({
      material,
      ...calcularOpciones(
        material.piezas,
        this.tableros().filter((t) => esDelMaterial(t, material)),
        material.opcionElegida,
      ),
    })),
  );

  protected readonly totalPiezas = computed(() =>
    sumar(
      this.materiales().flatMap((m) => m.piezas),
      (p) => p.cantidad,
    ),
  );

  // Totales del proyecto, o null si algún material no tiene ninguna opción posible.
  protected readonly totales = computed(() => {
    const bloques = this.bloques();
    const elegidas = bloques.map((b) => b.elegida).filter((o) => o !== undefined);
    if (bloques.length === 0 || elegidas.length < bloques.length) {
      return null;
    }
    const utilizada = sumar(elegidas, (o) => o.superficieUtilizada);
    const desperdiciada = sumar(elegidas, (o) => o.superficieDesperdiciada);
    return {
      tableros: sumar(elegidas, (o) => this.contarTableros(o)),
      aprovechamiento: utilizada / (utilizada + desperdiciada),
      desperdicioM2: desperdiciada / 1_000_000,
      precio: sumar(elegidas, (o) => o.costeEstimado),
    };
  });

  // Formatean en español sin cambiar el locale de toda la aplicación.
  protected readonly euros = new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' });
  protected readonly porcentaje = new Intl.NumberFormat('es-ES', { style: 'percent' });
  protected readonly decimales = new Intl.NumberFormat('es-ES', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  });

  protected readonly grosorSierraMm = GROSOR_SIERRA_MM;

  // Desplegables encadenados del modal: al cambiar uno, los siguientes vuelven a su primera opción.
  protected readonly tipos = computed(() => sinRepetir(this.tableros().map((t) => t.tipo)));
  protected readonly tipoId = linkedSignal(() => this.tipos()[0]?.id);

  protected readonly acabados = computed(() =>
    sinRepetir(
      this.tableros()
        .filter((t) => t.tipo.id === this.tipoId())
        .map((t) => t.acabado),
    ),
  );
  protected readonly acabadoId = linkedSignal(() => this.acabados()[0]?.id);

  protected readonly grosores = computed(() =>
    sinRepetir(
      this.tableros()
        .filter((t) => t.tipo.id === this.tipoId() && t.acabado.id === this.acabadoId())
        .map((t) => t.grosor),
    ),
  );
  protected readonly grosorId = linkedSignal(() => this.grosores()[0]?.id);

  // Tableros del material elegido en el modal, uno por formato.
  private readonly candidatos = computed(() =>
    this.tableros().filter(
      (t) =>
        t.tipo.id === this.tipoId() &&
        t.acabado.id === this.acabadoId() &&
        t.grosor.id === this.grosorId(),
    ),
  );

  private readonly modal = viewChild.required<ElementRef<HTMLDialogElement>>('modal');
  private readonly piezaNueva = signal(piezaVacia());
  protected readonly piezaForm = form(
    this.piezaNueva,
    (p) => {
      required(p.descripcion, { message: 'Escribe una descripción.' });
      enteroPositivo(p.anchoMm, 'El ancho');
      enteroPositivo(p.altoMm, 'El alto');
      enteroPositivo(p.cantidad, 'La cantidad');
      validate(p, ({ value }) => {
        const pieza = value();
        const conMedidas = pieza.anchoMm > 0 && pieza.altoMm > 0;
        return !conMedidas || this.candidatos().some((t) => cabe(pieza, t.formato))
          ? undefined
          : {
              kind: 'noCabe',
              message: 'La pieza es más grande que todos los formatos del material.',
            };
      });
    },
    {
      submission: {
        action: async () => {
          this.anadirPieza(this.piezaNueva());
          this.modal().nativeElement.close();
        },
      },
    },
  );

  // El modal conserva el último material elegido.
  protected abrirModal(): void {
    this.piezaForm().reset(piezaVacia());
    this.modal().nativeElement.showModal();
  }

  protected quitarPieza(indiceMaterial: number, indicePieza: number): void {
    this.cambiarMaterial(indiceMaterial, (m) => ({
      ...m,
      piezas: m.piezas.filter((_, i) => i !== indicePieza),
    }));
    // Un material sin piezas desaparece del proyecto.
    this.materiales.update((materiales) => materiales.filter((m) => m.piezas.length > 0));
  }

  protected elegirOpcion(indiceMaterial: number, opcion: Optimizacion): void {
    this.cambiarMaterial(indiceMaterial, (m) => ({ ...m, opcionElegida: opcion.nombreOpcion }));
  }

  protected contarTableros(opcion: Optimizacion): number {
    return sumar(opcion.detalles, (d) => d.cantidad);
  }

  // Letra en mm para que la descripción y las medidas quepan dentro de la pieza dibujada.
  protected tamanoLetra(colocada: DistribucionPieza, pieza: Pieza): number {
    const caracteres = Math.max(
      pieza.descripcion.length,
      `${pieza.anchoMm} × ${pieza.altoMm} ↻`.length,
    );
    return Math.min(70, colocada.altoFinalMm / 3, colocada.anchoFinalMm / (0.6 * caracteres));
  }

  // La pieza va a la tabla de su material. Si el proyecto aún no tiene ese material, se crea su tabla.
  private anadirPieza(pieza: Pieza): void {
    const [tablero] = this.candidatos();
    const indice = this.materiales().findIndex((m) => esDelMaterial(tablero, m));
    if (indice === -1) {
      this.materiales.update((materiales) => [
        ...materiales,
        {
          tipoTablero: tablero.tipo,
          acabado: tablero.acabado,
          grosor: tablero.grosor,
          piezas: [pieza],
          opcionElegida: null,
        },
      ]);
    } else {
      this.cambiarMaterial(indice, (m) => ({ ...m, piezas: [...m.piezas, pieza] }));
    }
  }

  private cambiarMaterial(indice: number, cambio: (material: Material) => Material): void {
    this.materiales.update((materiales) =>
      materiales.map((m, i) => (i === indice ? cambio(m) : m)),
    );
  }
}

function esDelMaterial(tablero: Tablero, material: Material): boolean {
  return (
    tablero.tipo.id === material.tipoTablero.id &&
    tablero.acabado.id === material.acabado.id &&
    tablero.grosor.id === material.grosor.id
  );
}

// Quita los repetidos por id y conserva el orden del catálogo.
function sinRepetir<T extends { id: number }>(lista: T[]): T[] {
  return lista.filter((item, i) => lista.findIndex((otro) => otro.id === item.id) === i);
}

function sumar<T>(lista: T[], valor: (item: T) => number): number {
  return lista.reduce((total, item) => total + valor(item), 0);
}

function enteroPositivo(campo: SchemaPath<number>, nombre: string): void {
  validate(campo, ({ value }) =>
    Number.isInteger(value()) && value() > 0
      ? undefined
      : {
          kind: 'enteroPositivo',
          message: `${nombre} tiene que ser un número entero mayor que 0.`,
        },
  );
}

// NaN deja vacíos los campos numéricos del formulario.
function piezaVacia(): Pieza {
  return { descripcion: '', anchoMm: NaN, altoMm: NaN, cantidad: 1, rotacionPermitida: true };
}
