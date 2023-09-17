import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from 'projects/authentication/src/app/register/register.component';
import { AppAuthenticationModule } from 'projects/authentication/src/app/app-authentication.module';
import { LoginComponent } from 'projects/authentication/src/app/login/login.component';
import { VerificationCodeComponent } from './shared/authentication/verification-code/verification-code.component';
import { AlertComponent } from './shared/user/alert/alert.component';
import { AppUserModule } from 'projects/user-app/src/app/app-user.module';
import { AppUserRoutingModule } from 'projects/user-app/src/app/app-user-routing.module';
import { UserHomeComponent } from 'projects/user-app/src/app/user-home/user-home.component';
import { HotelManagementComponent } from 'projects/admin-app/src/app/hotel-management/hotel-management.component';
import { CreateHotelComponent } from 'projects/admin-app/src/app/hotel-management/create-hotel/create-hotel.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './service/token.interceptor';
import { UserAssetsComponent } from './common/user-assets/user-assets.component';
import { AdminAssetsComponent } from './common/admin-assets/admin-assets.component';
import { CssLoadingComponent } from './shared/user/css-loading/css-loading.component';
import { UserHeaderComponent } from './shared/user/user-header/user-header.component';
import { BookNowComponent } from './shared/user/book-now/book-now.component';
import { AdminHeaderSidebarComponent } from './shared/admin/admin-header-sidebar/admin-header-sidebar.component';
import { AdminWidgetComponent } from './shared/admin/admin-widget/admin-widget.component';

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
    AdminHeaderSidebarComponent,
    UserAssetsComponent,
    AdminAssetsComponent,
    AdminWidgetComponent,
  ],
  imports: [
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    AppAuthenticationModule,
    AppUserRoutingModule,
    AppUserModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
