import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppAdminRoutingModule } from './app-admin-routing.module';
import { AppComponent } from './app.component';
import { TestComponent } from './test/test.component';

@NgModule({
  declarations: [
    AppComponent,
    TestComponent,
  ],
  imports: [
    BrowserModule,
    AppAdminRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppAdminModule { }
