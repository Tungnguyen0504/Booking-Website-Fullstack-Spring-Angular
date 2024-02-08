import { Component, OnInit } from '@angular/core';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { FileService } from 'src/app/service/file.service';

@Component({
  selector: 'app-search-accommodation',
  templateUrl: './search-accommodation.component.html',
  styleUrls: ['./search-accommodation.component.css'],
})
export class SearchAccommodationComponent implements OnInit {
  listAccommodation?: Accommodation[] = [];

  constructor(
    private $accommodationService: AccommodationService,
    private $fileService: FileService
  ) {}

  ngOnInit(): void {
    this.initApi();
  }

  initApi() {
    this.$accommodationService.getAllAccommodation().subscribe({
      next: (response) => {
        this.listAccommodation = response;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  fakeArrStar(star: number): Array<any> {
    return new Array(star);
  }
}
