import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SearchAccommodationComponent } from './search-accommodation/search-accommodation.component';
import { UserHeaderComponent } from 'src/app/shared/user/user-header/user-header.component';
import { UserHomeComponent } from './user-home/user-home.component';
import { CityBlockComponent } from 'src/app/shared/user/city-block/city-block.component';
import { BookNowComponent } from 'src/app/shared/user/book-now/book-now.component';
import { AccommodationDetailComponent } from './accommodation-detail/accommodation-detail.component';
import { GalleryModule } from 'ng-gallery';
import { LightgalleryModule } from 'lightgallery/angular';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { RoomDetailDialogComponent } from './accommodation-detail/room-detail-dialog/room-detail-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { CheckoutComponent } from './checkout/checkout.component';
import { MatStepperModule } from '@angular/material/stepper';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BookingDetailLeftComponent } from 'src/app/shared/admin/booking-detail-left/booking-detail-left.component';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';

@NgModule({
  declarations: [
    UserComponent,
    UserHeaderComponent,
    CityBlockComponent,
    BookNowComponent,
    SearchAccommodationComponent,
    UserHomeComponent,
    AccommodationDetailComponent,
    RoomDetailDialogComponent,
    CheckoutComponent,
    BookingDetailLeftComponent,
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    GalleryModule,
    MatMenuModule,
    MatButtonModule,
    MatListModule,
    MatDialogModule,
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule,
    MatSidenavModule,
    MatSelectModule,

    LightgalleryModule,
  ],
})
export class UserModule { }
