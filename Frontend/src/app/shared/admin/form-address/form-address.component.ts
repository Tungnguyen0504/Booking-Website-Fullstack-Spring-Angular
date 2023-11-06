import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { DialogData, FormAddressDialogComponent } from './form-address-dialog/form-address-dialog.component';

@Component({
  selector: 'app-form-address',
  templateUrl: './form-address.component.html',
  styleUrls: ['./form-address.component.css']
})
export class FormAddressComponent {
  @Input() form: FormGroup = {} as FormGroup;

  isEditable = false;

  constructor(
    private $formBuilder: FormBuilder,
    private dialog: MatDialog
  ) { }

  openDialog() {
    const dialogRef = this.dialog.open(FormAddressDialogComponent, {
      data: {
        action: "create",
        specificAddress: "",
        wardId: 0
      },
      position: {
        top: '8%'
      },
      width: '50%'
    });

    dialogRef.afterClosed().subscribe((result: DialogData) => {
      console.log(`Dialog result: ${result.wardId}`);
      console.log(`Dialog result: ${result.address}`);
      console.log(`Dialog result: ${result.specificAddress}`);
    });
  }
}
