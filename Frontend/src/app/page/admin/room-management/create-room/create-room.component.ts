import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, of, switchMap } from 'rxjs';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { AlertService } from 'src/app/service/alert.service';
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

  // test: Observable<Accommodation> = new Observable<Accommodation>();

  form: FormGroup = {} as FormGroup;

  constructor(
    private $formBuilder: FormBuilder,
    private $accommodationService: AccommodationService,
    private $roomService: RoomService,
    private $alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.buildFormGroup();

    this.$accommodationService.getAllAccommodation().subscribe({
      next: (response) => {
        this.listAccommodation = response;
        this.selectedAccommodation = response[0];
      },
      error: (error) => {
        console.log(error);
      },
    });
    // this.test = this.$accommodationService.getAllAccommodation().pipe(
    //   switchMap((response) => {
    //     this.listAccommodation = response;
    //     this.selectedAccommodation = response[0];
    //     return of(this.selectedAccommodation);
    //   })
    // );
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
    this.form.get('images')?.setValue(data);
  }

  onSelectedAccom($event: any) {
    this.selectedAccommodation = $event.options[0]._value;
  }

  create() {
    this.form.get('accommodationId')?.setValue(this.selectedAccommodation?.accommodationId);
    if (this.form.valid) {
      this.$roomService.createNewRoom(this.form.value).subscribe({
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
      images: new FormControl([]),
    });
  }
}
