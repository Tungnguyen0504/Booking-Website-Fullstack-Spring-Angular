import { Component, OnInit } from '@angular/core';
import { AccommodationType } from 'src/app/model/AccommodationType.model';
import { Province } from 'src/app/model/Province.model';
import { AccommodationTypeService } from 'src/app/service/accommodation-type.service';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css'],
})
export class UserHomeComponent implements OnInit {
  lstProvince: Province[] = [];
  lstAccommodationType: AccommodationType[] = [];

  constructor(
    private $accommodationTypeService: AccommodationTypeService
    ) {}

  ngOnInit(): void {
    // this.$cityService.getTopProvince(5).subscribe((response) => {
    //   this.lstProvince = response;
    // });

    // this.$accommodationTypeService.getAllAccommodationType().subscribe((response) => {
    //   this.lstAccommodationType = response;
    // });
  }
}
