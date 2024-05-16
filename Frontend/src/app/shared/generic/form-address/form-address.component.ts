import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {
  FormAddressDialogData,
  FormAddressDialogComponent,
} from './form-address-dialog/form-address-dialog.component';

@Component({
  selector: 'app-form-address',
  templateUrl: './form-address.component.html',
  styleUrls: ['./form-address.component.css'],
})
export class FormAddressComponent {
  @Input() action: string = '';
  @Output() eventEmitter: EventEmitter<any> = new EventEmitter<any>();

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
      disableClose: true,
    });

    dialogRef.afterClosed().subscribe((result: FormAddressDialogData) => {
      if (result && result.isComplete) {
        this.eventEmitter.emit({
          wardId: result.wardId,
          specificAddress: result.specificAddress,
          fullAddress: result.fullAddress,
        });
      }
    });
  }
}
