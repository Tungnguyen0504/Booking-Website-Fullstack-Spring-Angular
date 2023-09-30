import { Component, OnInit } from '@angular/core';
import { City } from 'src/app/model/City.model';
import { AccommodationType } from 'src/app/model/AccommodationType.model';
import { CityService } from 'src/app/service/city.service';
import { AccommodationTypeService } from 'src/app/service/accommodation-type.service';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css'],
})
export class UserHomeComponent implements OnInit {
  lstCity: City[] = [];
  lstAccommodationType: AccommodationType[] = [];

  constructor(
    private $cityService: CityService,
    private $accommodationTypeService: AccommodationTypeService
    ) {}

  ngOnInit(): void {
    this.$cityService.getTopCity(5).subscribe((response) => {
      this.lstCity = response;
    });

    this.$accommodationTypeService.getAllAccommodationType().subscribe((response) => {
      this.lstAccommodationType = response;
    });
  }
}
