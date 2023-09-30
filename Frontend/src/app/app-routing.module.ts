import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateAccommodationComponent } from 'projects/admin-app/src/app/accommodation-management/create-accommodation/create-accommodation.component';
import { AppAuthenticationRoutingModule } from 'projects/authentication/src/app/app-authentication-routing.module';
import { LoginComponent } from 'projects/authentication/src/app/login/login.component';
import { LogoutComponent } from 'projects/authentication/src/app/logout/logout.component';
import { RegisterComponent } from 'projects/authentication/src/app/register/register.component';
import { UserHomeComponent } from 'projects/user-app/src/app/user-home/user-home.component';

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'user', component: UserHomeComponent },
  { path: 'user/home', component: UserHomeComponent },
  { path: 'admin/create-accommodation', component: CreateAccommodationComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes), AppAuthenticationRoutingModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
