import { Injectable, ViewContainerRef } from '@angular/core';

declare var $: any;

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  success(message: string) {
    this.showAlert('SUCCESS', message, 'alert-success', 'fa-check');
  }

  error(message: string) {
    this.showAlert('ERROR', message, 'alert-danger', 'fa-circle-exclamation');
  }

  warning(message: string) {
    this.showAlert(
      'WARNING',
      message,
      'alert-warning',
      'fa-circle-exclamation'
    );
  }

  showAlert(title: string, message: string, bgColor: string, icon: string) {
    $('#alert-toast .alert').addClass(bgColor);
    $('#alert-toast .alert .alert-icon').addClass(icon);
    $('#alert-toast .toast-title').html(title);
    $('#alert-toast .toast-message').html(message);
    $('#alert-toast').toast('show');
    setTimeout(function () {
      $('#alert-toast').toast('hide');
    }, 8000);
  }
}
