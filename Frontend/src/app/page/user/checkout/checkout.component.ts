import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { ActivatedRoute } from '@angular/router';
import { AccommodationService } from 'src/app/service/accommodation.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent implements OnInit {
  @ViewChild('stepper') private stepper!: MatStepper;

  constructor(
    private _formBuilder: FormBuilder,
    private $route: ActivatedRoute,
    private $accommodationService: AccommodationService
  ) {
    // const accommodationId: number = Number.parseInt(
    //   <string>this.$route.snapshot.paramMap.get('accommodationId')
    // );
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.stepper.next();
    }, 200);
  }

  secondFormGroup = this._formBuilder.group({
    secondCtrl: ['', Validators.required],
  });

  // initApi(accommodationId: number) {
  //   this.$accommodationService.getById(accommodationId).subscribe({
  //     next: (response: any) => {
  //       this.accommodation = response;
  //       if (this.accommodation) {
  //         this.accommodation.createdAt = moment(
  //           response.createdAt,
  //           DATETIME_FORMAT1
  //         ).toDate();
  //       }
  //       console.log(this.accommodation);
  //     },
  //   });
  // }
}
