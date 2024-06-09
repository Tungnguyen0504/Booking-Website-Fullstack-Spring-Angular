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
import { MatExpansionModule } from '@angular/material/expansion';
import { FileInputComponent } from './generic/file-input/file-input.component';
import { UserTabListComponent } from './generic/user-tab/user-tab.component';
import { FormVerificationDialogComponent } from './generic/form-verification-dialog/form-verification-dialog.component';
import { ErrorComponent } from './generic/error/error.component';
import { UserHeaderComponent } from './user/user-header/user-header.component';
import { UserFooterComponent } from './generic/user-footer/user-footer.component';

@NgModule({
  declarations: [
    FormAddressComponent,
    FormAddressDialogComponent,
    FileInputComponent,
    UserTabListComponent,
    FormVerificationDialogComponent,
    ErrorComponent,
    UserHeaderComponent,
    UserFooterComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatSelectModule,
    MatStepperModule,
    MatButtonModule,
    MatInputModule,
    MatExpansionModule,
  ],
  exports: [
    FormAddressComponent,
    FormAddressDialogComponent,
    FileInputComponent,
    UserTabListComponent,
    FormVerificationDialogComponent,
    UserHeaderComponent,
    UserFooterComponent,
  ],
})
export class GenericComponentModule {}
