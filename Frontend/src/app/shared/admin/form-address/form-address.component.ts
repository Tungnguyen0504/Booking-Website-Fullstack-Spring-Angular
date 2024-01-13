import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import {
  DialogData,
  FormAddressDialogComponent,
} from './form-address-dialog/form-address-dialog.component';

@Component({
  selector: 'app-form-address',
  templateUrl: './form-address.component.html',
  styleUrls: ['./form-address.component.css'],
})
export class FormAddressComponent {
  @Input() form: FormGroup = {} as FormGroup;
  @Input() action: string = '';

  constructor(private dialog: MatDialog) {}

  openDialog() {
    const dialogRef = this.dialog.open(FormAddressDialogComponent, {
      data: {
        action: this.action,
        specificAddress: '',
        wardId: 0,
      },
      position: {
        top: '8%',
      },
      width: '50%',
    });

    dialogRef.afterClosed().subscribe((result: DialogData) => {
      if (result && result.isCompleted) {
        this.form.patchValue({
          wardId: result.wardId,
          address: result.address,
          specificAddress: result.specificAddress,
        });
      }
    });
  }
}
