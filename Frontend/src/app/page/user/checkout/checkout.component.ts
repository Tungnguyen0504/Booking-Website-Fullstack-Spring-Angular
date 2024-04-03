import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { ActivatedRoute } from '@angular/router';
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

  cartStorage?: CartStorage;
  user?: User;
  accommodation?: Accommodation;

  typesOfShoes: string[] = ['Boots', 'Clogs', 'Loafers', 'Moccasins', 'Sneakers'];

  constructor(
    private $formBuilder: FormBuilder,
    private $route: ActivatedRoute,
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
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
      address: [''],
      note: [''],
      timeReceiveRoom: [''],
    });
  }

  initApi() {
    this.$bookingService.loadCartFromLocalStorage().subscribe({
      next: (res) => {
        this.cartStorage = res;
        console.log(this.cartStorage);
        this.$accommodationService
          .getById(this.cartStorage!.cartItems[0].room.accommodationId)
          .subscribe({
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
          fullName: this.user.fullName,
          email: this.user.email,
          phoneNumber: this.user.phoneNumber,
          address: this.user.address,
        });
      },
    });
  }

  submit() {}
}
