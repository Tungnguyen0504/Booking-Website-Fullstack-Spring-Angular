import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { User } from 'src/app/model/User.model';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { BookingService, CartStorage } from 'src/app/service/booking.service';
import { UserService } from 'src/app/service/user.service';

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
  ) {}

  ngOnInit(): void {
    setTimeout(() => {
      this.stepper.next();
    }, 200);
    this.initApi();
    this.buildForm();
  }

  buildForm() {
    this.secondForm = this.$formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
      address: [''],
      note: [''],
      estCheckinTime: [''],
    });
    this.thirdForm = this.$formBuilder.group({
      paymentMethod: ['', Validators.required],
    });
  }

  initApi() {
    this.$bookingService.loadCartFromLocalStorage().subscribe({
      next: (res) => {
        this.cartStorage = res;
        console.log(this.cartStorage);
        this.$accommodationService.getById(this.cartStorage!.cartItems[0].room.accommodationId).subscribe({
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
    // if (this.secondForm.valid && this.thirdForm.valid && this.cartStorage) {
    //   this.cartStorage.firstName = this.secondForm.get('firstName')?.value;
    //   this.cartStorage.lastName = this.secondForm.get('lastName')?.value;
    //   this.cartStorage.email = this.secondForm.get('email')?.value;
    //   this.cartStorage.phoneNumber = this.secondForm.get('phoneNumber')?.value;
    //   this.cartStorage.note = this.secondForm.get('note')?.value;
    //   this.cartStorage.estCheckinTime = this.secondForm.get('estCheckinTime')?.value;
    //   this.cartStorage.paymentMethod = this.thirdForm.get('paymentMethod')?.value;
    //   console.log(this.cartStorage);
    // }
  }
}
