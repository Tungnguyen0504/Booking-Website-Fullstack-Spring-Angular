import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppAdminRoutingModule } from './app-admin-routing.module';
import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppAdminRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppAdminModule { }
