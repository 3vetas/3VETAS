import { Component, signal } from '@angular/core';
import { email, form, FormField, FormRoot, required } from '@angular/forms/signals';
import { RouterLink } from '@angular/router';

@Component({
  imports: [FormField, FormRoot, RouterLink],
  selector: 'app-login',
  styleUrl: './login.css',
  templateUrl: './login.html',
})
export class Login {
  private readonly credenciales = signal({ correo: '', contrasena: '' });
  protected readonly loginForm = form(
    this.credenciales,
    (c) => {
      required(c.correo, { message: 'Escribe tu correo electrónico.' });
      email(c.correo, { message: 'El correo electrónico no es válido.' });
      required(c.contrasena, { message: 'Escribe tu contraseña.' });
    },
    {
      submission: {
        // Pendiente: POST /api/auth/login.
        action: async () => undefined,
      },
    },
  );
}
