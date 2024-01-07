import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserHomeComponent } from './page/user/user-home/user-home.component';
import { LogoutComponent } from './page/authentication/logout/logout.component';
import { LoginComponent } from './page/authentication/login/login.component';
import { RegisterComponent } from './page/authentication/register/register.component';
import { AdminComponent } from './page/admin/admin.component';

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'user', component: UserHomeComponent },
  { path: 'user/home', component: UserHomeComponent },
  {
    path: 'admin',
    component: AdminComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
