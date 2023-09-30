import { Injectable, ViewContainerRef } from '@angular/core';

declare var $: any;

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  bgColor: string = '';
  icon: string = '';

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
    this.clearAlert();
    this.createAlert(title, message, bgColor, icon);
  }

  clearAlert() {
    $('#alert-toast .alert').removeClass(this.bgColor);
    $('#alert-toast .alert .alert-icon').removeClass(this.icon);
  }

  createAlert(title: string, message: string, bgColor: string, icon: string) {
    this.bgColor = bgColor;
    this.icon = icon;

    $('#alert-toast .alert').addClass(bgColor);
    $('#alert-toast .alert .alert-icon').addClass(icon);
    $('#alert-toast .toast-title').html(title);
    $('#alert-toast .toast-message').html(message);
    $('#alert-toast').toast('show');
    setTimeout(() => {
      $('#alert-toast').toast('hide');
    }, 8000);
  }
}
