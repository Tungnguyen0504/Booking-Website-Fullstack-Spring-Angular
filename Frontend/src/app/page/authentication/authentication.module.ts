import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthenticationRoutingModule } from './authentication-routing.module';
import { AuthenticationComponent } from './authentication.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { AlertComponent } from 'src/app/shared/generic/alert/alert.component';
import { MatIconModule } from '@angular/material/icon';
import { GenericComponentModule } from 'src/app/shared/generic-component.module';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { MatInputModule } from '@angular/material/input';

@NgModule({
  declarations: [
    AuthenticationComponent,
    RegisterComponent,
    LoginComponent,
    AlertComponent,
    ResetPasswordComponent,
  ],
  imports: [
    CommonModule,
    AuthenticationRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    GenericComponentModule,
  ],
})
export class AuthenticationModule {}
