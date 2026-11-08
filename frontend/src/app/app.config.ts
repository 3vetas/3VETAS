import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    // Pasa los parámetros de la ruta, como el :id de /pedido/:id, a los input() del componente.
    provideRouter(routes, withComponentInputBinding()),
  ],
};
