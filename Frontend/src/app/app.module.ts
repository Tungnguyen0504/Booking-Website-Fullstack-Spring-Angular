import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from 'projects/authentication/src/app/register/register.component';
import { AppAuthenticationModule } from 'projects/authentication/src/app/app-authentication.module';
import { LoginComponent } from 'projects/authentication/src/app/login/login.component';
import { VerificationCodeComponent } from './shared/verification-code/verification-code.component';
import { AlertComponent } from './shared/alert/alert.component';
import { CssLoadingComponent } from './shared/css-loading/css-loading.component';
import { UserHeaderComponent } from './shared/user-header/user-header.component';
import { AppUserModule } from 'projects/user-app/src/app/app-user.module';
import { AppUserRoutingModule } from 'projects/user-app/src/app/app-user-routing.module';
import { UserHomeComponent } from 'projects/user-app/src/app/user-home/user-home.component';
import { BookNowComponent } from './shared/book-now/book-now.component';
import { HotelManagementComponent } from 'projects/admin-app/src/app/hotel-management/hotel-management.component';
import { CreateHotelComponent } from 'projects/admin-app/src/app/hotel-management/create-hotel/create-hotel.component';
import { AdminHeaderSidebarComponent } from './shared/admin-header-sidebar/admin-header-sidebar.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    CssLoadingComponent,
    AlertComponent,
    VerificationCodeComponent,
    UserHomeComponent,
    UserHeaderComponent,
    BookNowComponent,
    HotelManagementComponent,
    CreateHotelComponent,
    AdminHeaderSidebarComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    AppAuthenticationModule,
    AppUserRoutingModule,
    AppUserModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
