import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { CreateAccommodationComponent } from './accommodation-management/create-accommodation/create-accommodation.component';
import { AdminComponent } from './admin.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatListModule } from '@angular/material/list';
import { MatDividerModule } from '@angular/material/divider';
import { FormAddressComponent } from 'src/app/shared/admin/form-address/form-address.component';
import { FormAddressDialogComponent } from 'src/app/shared/admin/form-address/form-address-dialog/form-address-dialog.component';
import { AdminSidebarComponent } from 'src/app/shared/admin/admin-sidebar/admin-sidebar.component';
import { CreateRoomComponent } from './room-management/create-room/create-room.component';
import { NgxMatTimepickerModule } from 'ngx-mat-timepicker';
import { RatingModule } from 'ngx-bootstrap/rating';
import { CustomFieldTagInputComponent } from 'src/app/shared/admin/custom-field-tag-input/custom-field-tag-input.component';
import { FileInputComponent } from 'src/app/shared/generic/file-input/file-input.component';
import { AccommmodationListComponent } from './accommodation-management/accommmodation-list/accommmodation-list.component';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';

@NgModule({
  declarations: [
    AdminComponent,
    CreateAccommodationComponent,
    FormAddressComponent,
    CustomFieldTagInputComponent,
    FormAddressDialogComponent,
    AdminSidebarComponent,
    CreateRoomComponent,
    FileInputComponent,
    AccommmodationListComponent,
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserModule,
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
    MatListModule,
    MatDividerModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    NgxMatTimepickerModule,
    RatingModule,
  ],
})
export class AdminModule {}
