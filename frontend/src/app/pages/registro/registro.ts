import { Component, signal } from '@angular/core';
import {
  email,
  form,
  FormField,
  FormRoot,
  minLength,
  required,
  validate,
} from '@angular/forms/signals';
import { RouterLink } from '@angular/router';

const LONGITUD_MINIMA_CONTRASENA = 8;

@Component({
  imports: [FormField, FormRoot, RouterLink],
  selector: 'app-registro',
  styleUrl: './registro.css',
  templateUrl: './registro.html',
})
export class Registro {
  // Los campos definitivos del registro se fijarán durante el análisis.
  private readonly cuenta = signal({ nombre: '', correo: '', contrasena: '', repetida: '' });
  protected readonly registroForm = form(
    this.cuenta,
    (c) => {
      required(c.nombre, { message: 'Escribe tu nombre.' });
      required(c.correo, { message: 'Escribe tu correo electrónico.' });
      email(c.correo, { message: 'El correo electrónico no es válido.' });
      required(c.contrasena, { message: 'Escribe una contraseña.' });
      minLength(c.contrasena, LONGITUD_MINIMA_CONTRASENA, {
        message: `La contraseña tiene que tener al menos ${LONGITUD_MINIMA_CONTRASENA} caracteres.`,
      });
      validate(c.repetida, ({ value, valueOf }) =>
        value() === valueOf(c.contrasena)
          ? undefined
          : { kind: 'distinta', message: 'Las contraseñas no coinciden.' },
      );
    },
    {
      submission: {
        // Pendiente: POST /api/auth/register.
        action: async () => undefined,
      },
    },
  );
}
