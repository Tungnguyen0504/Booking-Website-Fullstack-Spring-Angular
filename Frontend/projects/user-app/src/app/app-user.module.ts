import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppUserRoutingModule } from './app-user-routing.module';
import { AppComponent } from './app.component';

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
