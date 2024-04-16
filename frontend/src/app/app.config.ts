import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ToastrModule } from 'ngx-toastr';
import { provideAnimations } from '@angular/platform-browser/animations';
// http client module
import { HttpClientModule } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideClientHydration(),
    provideAnimations(),
    importProvidersFrom(HttpClientModule),
    importProvidersFrom(NgxSpinnerModule.forRoot({ type: 'ball-scale-multiple' })),
    importProvidersFrom(ToastrModule.forRoot(/*config*/)),
  ]
};
