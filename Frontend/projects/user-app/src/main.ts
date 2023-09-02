import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppUserModule } from './app/app-user.module';


platformBrowserDynamic().bootstrapModule(AppUserModule)
  .catch(err => console.error(err));
