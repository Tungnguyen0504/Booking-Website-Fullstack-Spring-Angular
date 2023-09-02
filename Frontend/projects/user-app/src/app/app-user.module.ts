import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppUserRoutingModule } from './app-user-routing.module';
import { AppComponent } from './app.component';
import { UserHomeComponent } from './user-home/user-home.component';
import { UserHeaderComponent } from 'src/app/shared/user-header/user-header.component';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppUserRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppUserModule { }
