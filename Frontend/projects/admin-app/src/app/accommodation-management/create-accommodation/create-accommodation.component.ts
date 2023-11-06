import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';

declare var $: any;

interface Accommodation {
  wardId: number;
  specificAddress: string;
}

@Component({
  selector: 'app-create-accommodation',
  templateUrl: './create-accommodation.component.html',
  styleUrls: ['./create-accommodation.component.css'],
})
export class CreateAccommodationComponent implements OnInit {
  form: FormGroup = {} as FormGroup;

  accommodation: Accommodation = {} as Accommodation;

  constructor(
    private $formBuilder: FormBuilder
    ) {
    // this.cityService.getTopCity(100).subscribe((response) => {
    //   this.cities = response;
    // });
    // console.log(this.cities);
  }

  ngOnInit(): void {
    this.form = this.$formBuilder.group({});
  }

  initComponentJquery() {
    $('input[name="image"]').fileinput({
      allowedFileTypes: ['image'],
      initialPreviewAsData: true,
      showUpload: false,
      showCancel: false,
    });
  }

  buildFormGroup() {
    this.form = this.$formBuilder.group({
      // wardId: [this.accommodation.wardId, null],
      // specificAddress: [this.accommodation.specificAddress, null]
    })
  }

  create() {
    console.log(this.form?.value);
  }
}
