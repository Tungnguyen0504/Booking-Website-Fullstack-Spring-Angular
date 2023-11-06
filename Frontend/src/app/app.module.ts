import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from 'projects/authentication/src/app/register/register.component';
import { AppAuthenticationModule } from 'projects/authentication/src/app/app-authentication.module';
import { LoginComponent } from 'projects/authentication/src/app/login/login.component';
import { VerificationCodeComponent } from './shared/authentication/verification-code/verification-code.component';
import { AlertComponent } from './shared/generic/alert/alert.component';
import { AppUserModule } from 'projects/user-app/src/app/app-user.module';
import { AppUserRoutingModule } from 'projects/user-app/src/app/app-user-routing.module';
import { UserHomeComponent } from 'projects/user-app/src/app/user-home/user-home.component';
import { AccommodationManagementComponent } from 'projects/admin-app/src/app/accommodation-management/accommodation-management.component';
import { CreateAccommodationComponent } from 'projects/admin-app/src/app/accommodation-management/create-accommodation/create-accommodation.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { TokenInterceptor } from './service/token.interceptor';
import { UserAssetsComponent } from './common/user-assets/user-assets.component';
import { AdminAssetsComponent } from './common/admin-assets/admin-assets.component';
import { CssLoadingComponent } from './shared/user/css-loading/css-loading.component';
import { UserHeaderComponent } from './shared/user/user-header/user-header.component';
import { BookNowComponent } from './shared/user/book-now/book-now.component';
import { AdminHeaderSidebarComponent } from './shared/admin/admin-header-sidebar/admin-header-sidebar.component';
import { AdminWidgetComponent } from './shared/admin/admin-widget/admin-widget.component';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TestComponent } from 'projects/admin-app/src/app/test/test.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormAddressComponent } from './shared/admin/form-address/form-address.component';
import { MatDialogModule } from '@angular/material/dialog';
import { FormAddressDialogComponent } from './shared/admin/form-address/form-address-dialog/form-address-dialog.component';
import { GenericAssetsComponent } from './common/generic-assets/generic-assets.component';
import { MatExpansionModule } from '@angular/material/expansion';

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
    AccommodationManagementComponent,
    CreateAccommodationComponent,
    AdminHeaderSidebarComponent,
    UserAssetsComponent,
    AdminAssetsComponent,
    AdminWidgetComponent,
    TestComponent,
    FormAddressComponent,
    FormAddressDialogComponent,
    GenericAssetsComponent,
  ],
  imports: [
    HttpClientModule,
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    AppAuthenticationModule,
    AppUserRoutingModule,
    AppUserModule,
    NgMultiSelectDropDownModule.forRoot(),
    NgbModule,
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
