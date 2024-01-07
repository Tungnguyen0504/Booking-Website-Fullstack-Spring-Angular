import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { FileService } from 'src/app/service/file.service';

declare var $: any;

@Component({
  selector: 'app-create-room',
  templateUrl: './create-room.component.html',
  styleUrls: ['./create-room.component.css'],
})
export class CreateRoomComponent implements OnInit {
  selectedAccommodation?: Accommodation;
  listAccommodation: Accommodation[] = [];
  imageCarousel?: string;

  constructor(
    private $accommodationService: AccommodationService,
    private $fileService: FileService
  ) {}

  onSelectedAccom(option: any) {
    this.$accommodationService.getById(option._value).subscribe({
      next: (response) => {
        this.refreshSeletedAccom(response);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  ngOnInit(): void {
    this.$accommodationService.getAllAccommodation().subscribe({
      next: (response) => {
        this.listAccommodation = response;
        this.refreshSeletedAccom(response[0]);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  fakeArrStar(star: number): Array<any> {
    return new Array(star);
  }

  refreshSeletedAccom(response: any) {
    this.selectedAccommodation = response;
    if (this.selectedAccommodation) {
      this.$fileService
        .getMultipleImages(this.selectedAccommodation.filePaths)
        .subscribe((response: any) => {
          this.imageCarousel = response.map(
            (res: any) => 'data:image/jpeg;base64,' + res.fileByte
          );
        });
    }
  }
}
