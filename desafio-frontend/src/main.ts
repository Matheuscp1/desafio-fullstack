import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
bootstrapApplication(AppComponent,  {
  providers: [
    // Importa o módulo HTTP para o cliente HTTP
    ...appConfig.providers  // Aplica a configuração da aplicação, incluindo os interceptores
  ]
})
  .catch((err) => console.error(err));
