import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppAuthenticationRoutingModule } from './app-authentication-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { LogoutComponent } from './logout/logout.component';

@NgModule({
  declarations: [AppComponent, LogoutComponent],
  imports: [BrowserModule, AppAuthenticationRoutingModule, HttpClientModule],
  exports: [],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppAuthenticationModule {}
