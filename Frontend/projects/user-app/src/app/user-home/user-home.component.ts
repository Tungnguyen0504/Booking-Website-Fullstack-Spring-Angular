import { Component, OnInit } from '@angular/core';
import { City } from 'src/app/model/City.model';
import { HotelType } from 'src/app/model/HotelType.model';
import { CityService } from 'src/app/service/city.service';
import { HotelTypeService } from 'src/app/service/hotel-type.service';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css'],
})
export class UserHomeComponent implements OnInit {
  lstCity: City[] = [];
  lstHotelType: HotelType[] = [];

  constructor(
    private $cityService: CityService,
    private $hotelTypeService: HotelTypeService
    ) {}

  ngOnInit(): void {
    this.$cityService.getTopCity(5).subscribe((response) => {
      this.lstCity = response;
    });

    this.$hotelTypeService.getAllHotelType().subscribe((response) => {
      this.lstHotelType = response;
    });
  }
}
