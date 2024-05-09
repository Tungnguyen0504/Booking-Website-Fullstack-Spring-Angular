import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormAddressComponent } from './generic/form-address/form-address.component';
import { FormAddressDialogComponent } from './generic/form-address/form-address-dialog/form-address-dialog.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';

@NgModule({
  declarations: [FormAddressComponent, FormAddressDialogComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatSelectModule,
    MatStepperModule,
    MatButtonModule,
    MatInputModule
  ],
  exports: [FormAddressComponent, FormAddressDialogComponent],
})
export class GenericComponentModule {}
