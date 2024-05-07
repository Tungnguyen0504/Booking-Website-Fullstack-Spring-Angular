import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { AlertService } from 'src/app/service/alert.service';
import { FileService } from 'src/app/service/file.service';
import { RoomService } from 'src/app/service/room.service';

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
  selectedImages?: FileList;

  listViewSeletection: string[] = [];
  listDinningRoomSeletection: string[] = [];
  listBathRoomSeletection: string[] = [];
  listRoomServiceSeletection: string[] = [];

  form: FormGroup = {} as FormGroup;

  constructor(
    private $formBuilder: FormBuilder,
    private $accommodationService: AccommodationService,
    private $roomService: RoomService,
    private $alertService: AlertService
  ) {
    this.buildFormGroup();

    // this.listViewSeletection = ['1', '2', '3'];
  }

  initComponentJquery() {
    setTimeout(() => {
      if (this.imageCarousel) {
        const test = this.imageCarousel[0];
        $('#file-input').fileinput({
          allowedFileTypes: ['image'],
          initialPreviewAsData: true,
          showUpload: false,
          showCancel: false,
          initialPreview: this.imageCarousel,
        });
      }
    }, 500);
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

    this.initComponentJquery();
  }

  onViewEmitter(data: any) {
    this.form.get('views')?.setValue(data);
  }

  onDinningRoomEmitter(data: any) {
    this.form.get('dinningRooms')?.setValue(data);
  }

  onBathRoomEmitter(data: any) {
    this.form.get('bathRooms')?.setValue(data);
  }

  onRoomServiceEmitter(data: any) {
    this.form.get('roomServices')?.setValue(data);
  }

  onFileInputEmitter(data: any) {
    this.selectedImages = data;
  }

  onSelectedAccom($event: any) {
    this.$accommodationService.getById($event.options[0]._value).subscribe({
      next: (response) => {
        this.refreshSeletedAccom(response);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  refreshSeletedAccom(response: any) {
    this.selectedAccommodation = response;
  }

  create() {
    this.form.get('accommodationId')?.setValue(this.selectedAccommodation?.accommodationId);
    if (this.form.valid && this.selectedImages) {
      this.$roomService.createNewRoom(this.selectedImages, this.form.value).subscribe({
        next: (response) => {
          this.$alertService.success(response.message);
          this.form.reset();
        },
        error: (error) => {
          this.$alertService.error(error.error.message);
        },
      });
    }
  }

  buildFormGroup() {
    this.form = this.$formBuilder.group({
      accommodationId: new FormControl('', Validators.required),
      roomType: new FormControl('', Validators.required),
      roomArea: new FormControl('', Validators.required),
      bed: new FormControl('', Validators.required),
      capacity: new FormControl('', Validators.required),
      smoke: new FormControl('', Validators.required),
      quantity: new FormControl('', Validators.required),
      price: new FormControl('', Validators.required),
      discount: new FormControl('', Validators.required),
      dinningRooms: new FormControl(''),
      bathRooms: new FormControl(''),
      roomServices: new FormControl(''),
      views: new FormControl(''),
    });
  }
}
