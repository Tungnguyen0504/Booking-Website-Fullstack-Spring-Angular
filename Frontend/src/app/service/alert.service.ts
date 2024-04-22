import { Injectable } from '@angular/core';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { AlertComponent } from '../shared/generic/alert/alert.component';

declare var $: any;

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  horizontalPosition: MatSnackBarHorizontalPosition = 'end';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(private $snackBar: MatSnackBar) {}

  success(message: string) {
    this.createAlert('SUCCESS', message, 'alert-success', 'SUCCESS');
  }

  error(message: string) {
    console.log(message);
    this.createAlert('ERROR', message, 'alert-danger', 'WARNING');
  }

  warning(message: string) {
    console.log(message);
    this.createAlert('WARNING', message, 'alert-warning', 'WARNING');
  }

  private createAlert(title: string, message: string, bgColor: string, icon: string) {
    this.$snackBar.openFromComponent(AlertComponent, {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
      duration: 5000,
      data: {
        title: title,
        message: message,
        bgColor: bgColor,
        icon: icon,
      },
    });
  }
}
