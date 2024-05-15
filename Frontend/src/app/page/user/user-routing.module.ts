import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './user.component';
import { UserHomeComponent } from './user-home/user-home.component';
import { SearchAccommodationComponent } from './search-accommodation/search-accommodation.component';
import { AccommodationDetailComponent } from './accommodation-detail/accommodation-detail.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { BookingSuccessComponent } from './booking-success/booking-success.component';
import { UserInformationComponent } from './user-information/user-information.component';
import { ChangePasswordComponent } from './change-password/change-password.component';

const routes: Routes = [
  {
    path: '',
    component: UserComponent,
    children: [
      { path: '', component: UserHomeComponent },
      { path: 'home', component: UserHomeComponent },
      { path: 'search-accommodation', component: SearchAccommodationComponent },
      {
        path: 'accommodation-detail/:accommodationId',
        component: AccommodationDetailComponent,
      },
      { path: 'booking/checkout', component: CheckoutComponent },
      { path: 'booking/success', component: BookingSuccessComponent },
      { path: 'user-information', component: UserInformationComponent },
      { path: 'change-password', component: ChangePasswordComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
