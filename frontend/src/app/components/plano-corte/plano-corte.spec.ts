import { TestBed } from '@angular/core/testing';

import { PlanoCorte } from './plano-corte';

describe('PlanoCorte', () => {
  it('busca cada pieza colocada por su id y no por su posición en la lista', () => {
    const fixture = TestBed.createComponent(PlanoCorte);
    fixture.componentRef.setInput('formato', { id: 1, anchoMm: 2440, altoMm: 1220 });
    fixture.componentRef.setInput('color', '#f4f4f2');
    fixture.componentRef.setInput('piezas', [
      {
        id: 20,
        descripcion: 'Costado',
        anchoMm: 800,
        altoMm: 500,
        cantidad: 1,
        rotacionPermitida: true,
      },
      {
        id: 10,
        descripcion: 'Balda',
        anchoMm: 600,
        altoMm: 300,
        cantidad: 1,
        rotacionPermitida: true,
      },
    ]);
    fixture.componentRef.setInput('unidad', {
      numeroTablero: 1,
      distribuciones: [
        {
          piezaId: 10,
          numeroUnidad: 1,
          ordenDibujo: 1,
          posicionXmm: 0,
          posicionYmm: 0,
          anchoFinalMm: 600,
          altoFinalMm: 300,
          rotada: false,
        },
      ],
    });
    fixture.detectChanges();

    const titulo: HTMLElement = fixture.nativeElement.querySelector('title');
    expect(titulo.textContent).toContain('Balda');
  });
});
