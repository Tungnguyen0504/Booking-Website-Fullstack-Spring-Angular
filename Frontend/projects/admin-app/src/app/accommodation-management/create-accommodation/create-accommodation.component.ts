import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { CityService } from 'src/app/service/city.service';

declare var $: any;

@Component({
  selector: 'app-create-accommodation',
  templateUrl: './create-accommodation.component.html',
  styleUrls: ['./create-accommodation.component.css'],
})
export class CreateAccommodationComponent implements OnInit {
  @ViewChild('form', { static: false }) form: NgForm = {} as NgForm;

  accommodation: Accommodation = {} as Accommodation;

  constructor(private cityService: CityService) {
    // this.cityService.getTopCity(100).subscribe((response) => {
    //   this.cities = response;
    // });
    // console.log(this.cities);
  }

  ngOnInit(): void {}

  initComponentJquery() {
    $('input[name="image"]').fileinput({
      allowedFileTypes: ['image'],
      initialPreviewAsData: true,
      showUpload: false,
      showCancel: false,
    });
  }

  create() {
    console.log(this.form?.value);
  }
}
