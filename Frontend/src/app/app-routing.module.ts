import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateHotelComponent } from 'projects/admin-app/src/app/hotel-management/create-hotel/create-hotel.component';
import { AppAuthenticationRoutingModule } from 'projects/authentication/src/app/app-authentication-routing.module';
import { LoginComponent } from 'projects/authentication/src/app/login/login.component';
import { RegisterComponent } from 'projects/authentication/src/app/register/register.component';
import { UserHomeComponent } from 'projects/user-app/src/app/user-home/user-home.component';

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'user', component: UserHomeComponent },
  { path: 'user/home', component: UserHomeComponent },
  { path: 'admin/create-hotel', component: CreateHotelComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes), AppAuthenticationRoutingModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
