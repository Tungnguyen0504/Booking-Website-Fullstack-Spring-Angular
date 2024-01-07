import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { TokenInterceptor } from './service/token.interceptor';
import { MatDialogModule } from '@angular/material/dialog';
import { MatStepperModule } from '@angular/material/stepper';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RegisterComponent } from './page/authentication/register/register.component';
import { LoginComponent } from './page/authentication/login/login.component';
import { CssLoadingComponent } from './shared/user/css-loading/css-loading.component';
import { AlertComponent } from './shared/generic/alert/alert.component';
import { VerificationCodeComponent } from './shared/authentication/verification-code/verification-code.component';
import { UserHomeComponent } from './page/user/user-home/user-home.component';
import { UserHeaderComponent } from './shared/user/user-header/user-header.component';
import { BookNowComponent } from './shared/user/book-now/book-now.component';
import { AdminHeaderSidebarComponent } from './shared/admin/admin-header-sidebar/admin-header-sidebar.component';
import { AdminWidgetComponent } from './shared/admin/admin-widget/admin-widget.component';
import { GenericAssetsComponent } from './common/generic-assets/generic-assets.component';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatExpansionModule } from '@angular/material/expansion';
import { AdminModule } from './page/admin/admin.module';

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
    AdminHeaderSidebarComponent,
    AdminWidgetComponent,
    GenericAssetsComponent,
  ],
  imports: [
    HttpClientModule,
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatChipsModule,
    MatIconModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatSelectModule,
    MatStepperModule,
    MatInputModule,
    MatButtonModule,
    MatDialogModule,
    MatExpansionModule,
    AdminModule,
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
