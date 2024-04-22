import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { DATETIME_FORMAT3, ROOM_GUEST_QTY_STORAGE } from 'src/app/constant/Abstract.constant';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { User } from 'src/app/model/User.model';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { BookingService, CartStorage } from 'src/app/service/booking.service';
import { UserService } from 'src/app/service/user.service';
import { Util } from 'src/app/util/util';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent implements OnInit {
  @ViewChild('stepper') private stepper!: MatStepper;
  @ViewChild('paymentRef', { static: true }) paymentRef!: ElementRef;

  secondForm: FormGroup = {} as FormGroup;
  thirdForm: FormGroup = {} as FormGroup;

  cartStorage?: CartStorage;
  roomGuestQty: any;
  user?: User;
  accommodation?: Accommodation;

  paymentMethods: any[] = [
    {
      value: 'paypal',
      title: 'Thanh toán bằng Paypal',
      description: 'Phí thanh toán 0đ',
      img: 'assets/img/payment-method/paypal.jpg',
    },
    {
      value: 'momo',
      title: 'Thanh toán bằng Momo',
      description: 'Phí thanh toán 0đ',
      img: 'assets/img/payment-method/momo.jpg',
    },
  ];

  constructor(
    private $formBuilder: FormBuilder,
    private $userService: UserService,
    private $accommodationService: AccommodationService,
    private $bookingService: BookingService
  ) {}

  ngOnInit(): void {
    setTimeout(() => {
      this.stepper.next();
    });
    this.initApi();
    this.buildForm();
    this.initPayment();
  }

  initPayment() {
    window.paypal
      .Buttons({
        style: {
          layout: 'horizontal',
          color: 'blue',
          shape: 'rect',
          label: 'paypal',
        },
        // createOrder: (data, actions) => {
        //   return fetch('http://localhost:3000/paypal/orders', {
        //     method: 'post',
        //     headers: {
        //       Accept: 'application/json, text/plain, */*',
        //       'Content-Type': 'application/json',
        //     },
        //     body: JSON.stringify(postData),
        //   })
        //     .then((response) => response.json())
        //     .then((response) => {
        //       orderRef = response.orderRef;
        //       return response.id;
        //     });
        // },
        // onApprove: (data, actions) => {
        //   return fetch(`http://localhost:3000/paypal/orders/${data.orderID}/capture`, {
        //     method: 'post',
        //     headers: {
        //       Accept: 'application/json, text/plain, */*',
        //       'Content-Type': 'application/json',
        //     },
        //   })
        //     .then((response) => response.json())
        //     .then((orderData) => {
        //       var errorDetail = Array.isArray(orderData.details) && orderData.details[0];

        //       if (errorDetail && errorDetail.issue === 'INSTRUMENT_DECLINED') {
        //         return actions.restart();
        //       }
        //       var transaction = orderData.purchase_units[0].payments.captures[0];

        //       this.paypalRedirect(transaction.status, orderData.orderRef);
        //     });
        // },
        onError: (error: any) => {
          console.log(error);
        },
      })
      .render(this.paymentRef.nativeElement);
  }

  buildForm() {
    this.secondForm = this.$formBuilder.group({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      phoneNumber: new FormControl('', Validators.required),
      note: new FormControl(''),
      estCheckinTime: new FormControl('', Validators.required),
    });
    this.thirdForm = this.$formBuilder.group({
      paymentMethod: new FormControl('', Validators.required),
    });
  }

  initApi() {
    this.roomGuestQty = Util.getLocal(ROOM_GUEST_QTY_STORAGE);
    this.$bookingService.loadCartFromLocalStorage().subscribe({
      next: (res) => {
        this.cartStorage = res;
        console.log(this.cartStorage);
        this.$accommodationService.getById(this.cartStorage!.accommodationId).subscribe({
          next: (response: any) => {
            this.accommodation = response;
          },
        });
      },
    });

    this.$userService.getCurrentUser().subscribe({
      next: (res) => {
        if (res) {
          this.user = res;
          this.secondForm.patchValue({
            firstName: this.user.firstName,
            lastName: this.user.lastName,
            email: this.user.email,
            phoneNumber: this.user.phoneNumber,
            address: this.user.fullAddress,
          });
        }
      },
    });
  }

  selectPaymentChange(option: any) {
    this.thirdForm.get('paymentMethod')?.setValue(option._value[0]);
  }

  submit() {
    if (
      this.secondForm.valid &&
      this.thirdForm.valid &&
      this.cartStorage &&
      Object.keys(this.roomGuestQty).length !== 0
    ) {
      const request = {
        firstName: this.secondForm.get('firstName')?.value,
        lastName: this.secondForm.get('lastName')?.value,
        email: this.secondForm.get('email')?.value,
        phoneNumber: this.secondForm.get('phoneNumber')?.value,
        guestNumber: this.roomGuestQty.guestQty,
        note: this.secondForm.get('note')?.value,
        estCheckinTime: this.secondForm.get('estCheckinTime')?.value,
        paymentMethod: this.thirdForm.get('paymentMethod')?.value,
        fromDate: Util.formatDate(this.cartStorage.fromDate, DATETIME_FORMAT3),
        toDate: Util.formatDate(this.cartStorage.toDate, DATETIME_FORMAT3),
        accommodationId: this.cartStorage.accommodationId,
        cartItems: this.cartStorage.cartItems.map((item) => {
          return {
            quantity: item.quantity,
            roomId: item.room.roomId,
          };
        }),
      };
      console.log(request);
      this.$bookingService.createPayment(request).subscribe({
        next: (res) => {
          console.log('oke');
        },
      });
    }
  }
}
