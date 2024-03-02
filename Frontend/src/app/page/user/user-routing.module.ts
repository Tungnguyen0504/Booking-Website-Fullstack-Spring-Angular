import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './user.component';
import { UserHomeComponent } from './user-home/user-home.component';
import { SearchAccommodationComponent } from './search-accommodation/search-accommodation.component';
import { AccommodationDetailComponent } from './accommodation-detail/accommodation-detail.component';
import { CheckoutComponent } from './checkout/checkout.component';

const routes: Routes = [
  {
    path: 'user',
    component: UserComponent,
    children: [
      { path: '', component: UserHomeComponent },
      { path: 'home', component: UserHomeComponent },
      { path: 'search-accommodation', component: SearchAccommodationComponent },
      { path: 'accommodation-detail/:accommodationId', component: AccommodationDetailComponent },
      { path: 'checkout', component: CheckoutComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
