import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { DATETIME_FORMAT1 } from 'src/app/constant/Abstract.constant';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { BookingService, CartStorage } from 'src/app/service/booking.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent implements OnInit {
  @ViewChild('stepper') private stepper!: MatStepper;
  form: FormGroup = {} as FormGroup;

  cartStorage?: CartStorage;
  accommodation?: Accommodation;

  constructor(
    private $formBuilder: FormBuilder,
    private $route: ActivatedRoute,
    private $accommodationService: AccommodationService,
    private $bookingService: BookingService
  ) {
    // const accommodationId: number = Number.parseInt(
    //   <string>this.$route.snapshot.paramMap.get('accommodationId')
    // );
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.stepper.next();
    }, 200);
    this.initApi();
  }

  secondFormGroup = this.$formBuilder.group({
    secondCtrl: ['', Validators.required],
  });

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
              console.log(this.accommodation);
            },
          });
      },
    });
  }

  submit() {

  }
}
