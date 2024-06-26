import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import { CreateAccommodationComponent } from './accommodation-management/create-accommodation/create-accommodation.component';
import { CreateRoomComponent } from './room-management/create-room/create-room.component';
import { AccommmodationListComponent } from './accommodation-management/accommmodation-list/accommmodation-list.component';
import { AdminGuard } from 'src/app/config/access-guard';
import { BookingListComponent } from './booking-management/booking-list/booking-list.component';

const routes: Routes = [
  {
    path: 'dashboard',
    component: AdminComponent,
    children: [
      {
        path: 'accommodation-management',
        component: AccommmodationListComponent,
      },
      {
        path: 'create-accommodation',
        component: CreateAccommodationComponent,
      },
      {
        path: 'create-room',
        component: CreateRoomComponent,
      },
      {
        path: 'booking-management',
        component: BookingListComponent,
      },
    ],
    canActivate: [AdminGuard],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
