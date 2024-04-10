import { Component, OnInit, ViewChild } from '@angular/core';
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
  secondForm: FormGroup = {} as FormGroup;
  thirdForm: FormGroup = {} as FormGroup;

  cartStorage?: CartStorage;
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
  ) {
    console.log(Util.parseDate('1999-05-04', DATETIME_FORMAT3).toISOString());
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.stepper.next();
    });
    this.initApi();
    this.buildForm();
  }

  buildForm() {
    this.secondForm = this.$formBuilder.group({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      phoneNumber: new FormControl('', Validators.required),
      note: new FormControl(''),
      estCheckinTime: new FormControl(''),
    });
    this.thirdForm = this.$formBuilder.group({
      paymentMethod: new FormControl('', Validators.required),
    });
  }

  initApi() {
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
        this.user = res;
        console.log(this.user);
        this.secondForm.patchValue({
          firstName: this.user.firstName,
          lastName: this.user.lastName,
          email: this.user.email,
          phoneNumber: this.user.phoneNumber,
          address: this.user.address,
        });
      },
    });
  }

  selectPaymentChange(option: any) {
    this.thirdForm.get('paymentMethod')?.setValue(option._value[0]);
  }

  submit() {
    const data = Util.getLocal(ROOM_GUEST_QTY_STORAGE);
    if (
      this.secondForm.valid &&
      this.thirdForm.valid &&
      this.cartStorage &&
      Object.keys(data).length !== 0
    ) {
      const request = {
        firstName: this.secondForm.get('firstName')?.value,
        lastName: this.secondForm.get('lastName')?.value,
        email: this.secondForm.get('lastName')?.value,
        phoneNumber: this.secondForm.get('phoneNumber')?.value,
        guestNumber: data.guestQty,
        note: this.secondForm.get('note')?.value,
        estCheckinTime: this.secondForm.get('estCheckinTime')?.value,
        paymentMethod: this.thirdForm.get('paymentMethod')?.value,
        // fromDate
        // toDate
        // accommodationId
        // cartItems
      };
      console.log(this.cartStorage.fromDate);
      console.log(typeof this.cartStorage.fromDate);
      console.log(Util.formatDate(this.cartStorage.fromDate, DATETIME_FORMAT3));
      console.log(request);
    }
  }
}
