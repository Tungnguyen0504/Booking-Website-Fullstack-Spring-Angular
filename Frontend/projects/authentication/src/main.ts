import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppAuthenticationModule } from './app/app-authentication.module';


platformBrowserDynamic().bootstrapModule(AppAuthenticationModule)
  .catch(err => console.error(err));
