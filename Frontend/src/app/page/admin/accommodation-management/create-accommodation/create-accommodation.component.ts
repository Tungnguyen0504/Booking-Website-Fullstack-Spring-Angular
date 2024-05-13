import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AccommodationType } from 'src/app/model/AccommodationType.model';
import { AccommodationTypeService } from 'src/app/service/accommodation-type.service';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { AlertService } from 'src/app/service/alert.service';

declare var $: any;

@Component({
  selector: 'app-create-accommodation',
  templateUrl: './create-accommodation.component.html',
  styleUrls: ['./create-accommodation.component.css'],
})
export class CreateAccommodationComponent implements OnInit {
  form: FormGroup = {} as FormGroup;

  listAccommodationType: AccommodationType[] = [];

  specialArounds: string[] = [];
  specialAroundOptions: string[] = [];
  bathRooms: string[] = [];
  bathRoomOptions: string[] = [];
  bedRooms: string[] = [];
  bedRoomOptions: string[] = [];
  dinningRooms: string[] = [];
  dinningRoomOptions: string[] = [];
  languages: string[] = [];
  languageOptions: string[] = [];
  internets: string[] = [];
  internetOptions: string[] = [];
  drinkAndFoods: string[] = [];
  drinkAndFoodOptions: string[] = [];
  receptionServices: string[] = [];
  receptionServiceOptions: string[] = [];
  cleaningServices: string[] = [];
  cleaningServiceOptions: string[] = [];
  pools: string[] = [];
  poolOptions: string[] = [];
  others: string[] = [];
  otherOptions: string[] = [];

  constructor(
    private $formBuilder: FormBuilder,
    private $accommodationService: AccommodationService,
    private $accommodationTypeService: AccommodationTypeService,
    private $alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.buildFormGroup();

    this.$accommodationTypeService.getAllAccommodationType().subscribe((response) => {
      this.listAccommodationType = response;
    });
  }

  onAddressEmitter($event: any) {
    if ($event) {
      this.form.patchValue({
        fullAddress: $event.fullAddress,
        wardId: $event.wardId,
        specificAddress: $event.specificAddress,
      });
    }
  }

  onSpecialAroundEmitter(data: any) {
    this.form.get('specialArounds')?.setValue(data);
  }

  onBathRoomEmitter(data: any) {
    this.form.get('bathRooms')?.setValue(data);
  }

  onBedRoomEmitter(data: any) {
    this.form.get('bedRooms')?.setValue(data);
  }

  onDinningRoomEmitter(data: any) {
    this.form.get('dinningRooms')?.setValue(data);
  }

  onLanguageEmitter(data: any) {
    this.form.get('languages')?.setValue(data);
  }

  onInternetEmitter(data: any) {
    this.form.get('internets')?.setValue(data);
  }

  onDrinkAndFoodEmitter(data: any) {
    this.form.get('drinkAndFoods')?.setValue(data);
  }

  onReceptionServiceEmitter(data: any) {
    this.form.get('receptionServices')?.setValue(data);
  }

  onCleaningServiceEmitter(data: any) {
    this.form.get('cleaningServices')?.setValue(data);
  }

  onPoolEmitter(data: any) {
    this.form.get('pools')?.setValue(data);
  }

  onOtherEmitter(data: any) {
    this.form.get('others')?.setValue(data);
  }

  onFileInputEmitter($event: any) {
    this.form.get('images')?.setValue($event);
  }

  buildFormGroup() {
    this.form = this.$formBuilder.group({
      accommodationName: new FormControl('', Validators.required),
      wardId: new FormControl(''),
      fullAddress: new FormControl('', Validators.required),
      specificAddress: new FormControl(''),
      phone: new FormControl('', Validators.required),
      accommodationType: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      description: new FormControl('', Validators.required),
      star: new FormControl(0, Validators.required),
      checkin: new FormControl('', Validators.required),
      checkout: new FormControl('', Validators.required),
      specialArounds: new FormControl([], Validators.required),
      bathRooms: new FormControl([]),
      bedRooms: new FormControl([]),
      dinningRooms: new FormControl([]),
      languages: new FormControl([]),
      internets: new FormControl([]),
      drinkAndFoods: new FormControl([]),
      receptionServices: new FormControl([]),
      cleaningServices: new FormControl([]),
      pools: new FormControl([]),
      others: new FormControl([]),
      images: new FormControl([]),
    });
  }

  create() {
    console.log(this.form.valid);
    if (this.form.valid) {
      this.$accommodationService.createNewAccommodation(this.form.value).subscribe({
        next: (response) => {
          this.$alertService.error(response.message);
        },
        error: (error) => {
          this.$alertService.error(error.error.message);
        },
      });
    }
  }
}
