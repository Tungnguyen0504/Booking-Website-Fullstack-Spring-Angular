import { Component, OnInit } from '@angular/core';
import { Observable, map } from 'rxjs';
import { AccommodationType } from 'src/app/model/AccommodationType.model';
import { Province } from 'src/app/model/Province.model';
import { AccommodationTypeService } from 'src/app/service/accommodation-type.service';
import { FileService } from 'src/app/service/file.service';

interface City {
  name: string;
  image: string;
}

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css'],
})
export class UserHomeComponent implements OnInit {
  lstProvince: Province[] = [];
  lstAccommodationType: AccommodationType[] = [];
  cityList: City[] = [];

  constructor(
    private $accommodationTypeService: AccommodationTypeService,
    private $fileService: FileService
  ) {}

  ngOnInit(): void {
    this.initApi();
    this.getImageCity();
  }

  initApi() {
    this.$accommodationTypeService
      .getAllAccommodationType()
      .subscribe((response) => {
        this.lstAccommodationType = response;
      });
  }

  getImageCity() {
    const listCityName = [
      'Hà Nội',
      'Hồ Chí Minh',
      'Hội An',
      'Huế',
      'Nha Trang',
    ];
    const listImagePath = listCityName.map(
      (name: string) => `city/${name}.jpg`
    );
    this.$fileService.getMultipleImages(listImagePath).subscribe({
      next: (response: any) => {
        for (let i = 0; i < 5; i++) {
          const city: City = {
            name: listCityName[i],
            image: 'data:image/jpeg;base64,' + response[i].fileByte,
          };
          this.cityList?.push(city);
        }
        console.log(this.cityList);
      },
    });
  }
}
