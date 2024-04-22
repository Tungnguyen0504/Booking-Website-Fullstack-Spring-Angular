import { Component, ElementRef, ViewChild } from '@angular/core';
import { BOOKING_INFO_STORAGE, PATH_V1 } from 'src/app/constant/Abstract.constant';
import { AlertService } from 'src/app/service/alert.service';
import { Util } from 'src/app/util/util';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-booking-payment',
  templateUrl: './booking-payment.component.html',
  styleUrls: ['./booking-payment.component.css'],
})
export class BookingPaymentComponent {
  @ViewChild('paymentRef', { static: true }) paymentRef!: ElementRef;

  constructor(private $alertService: AlertService) {}

  ngOnInit(): void {
    const bookingInfo = Util.getLocal(BOOKING_INFO_STORAGE);
    console.log(bookingInfo);
    if (bookingInfo) {
      this.initPayment();
    } else {
      this.$alertService.error('Thông tin đặt phòng không hợp lệ');
    }
  }

  initPayment() {
    const URL = environment.apiUrl + PATH_V1;
    window.paypal
      .Buttons({
        style: {
          layout: 'horizontal',
          color: 'blue',
          shape: 'rect',
          label: 'paypal',
        },
        createOrder: (data: any, actions: any) => {
          const request = {};
          return fetch(`${URL}/booking/payment/create`, {
            method: 'post',
            headers: {
              Accept: 'application/json, text/plain, */*',
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(''),
          });
        },
        onApprove: (data: any, actions: any) => {
          const request = {};
          return fetch(`${URL}/booking/payment/success`, {
            method: 'post',
            headers: {
              Accept: 'application/json, text/plain, */*',
              'Content-Type': 'application/json',
            },
          })
            .then((response) => response.json())
            .then((details) => {
              this.$alertService.success(
                'Transaction completed by ' + details.payer.name.given_name
              );
            });
        },
        onError: (error: any) => {
          console.log(error);
        },
      })
      .render(this.paymentRef.nativeElement);
  }
}
