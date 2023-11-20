import { LiveAnnouncer } from '@angular/cdk/a11y';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import {
  Component,
  ElementRef,
  OnInit,
  ViewChild,
  inject,
} from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import flatpickr from 'flatpickr';
import { Observable, map, startWith } from 'rxjs';
import { AccommodationType } from 'src/app/model/AccommodationType.model';
import { AccommodationTypeService } from 'src/app/service/accommodation-type.service';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { SpecialAroundService } from 'src/app/service/special-around.service';

declare var $: any;

@Component({
  selector: 'app-create-accommodation',
  templateUrl: './create-accommodation.component.html',
  styleUrls: ['./create-accommodation.component.css'],
})
export class CreateAccommodationComponent implements OnInit {
  form: FormGroup = {} as FormGroup;

  listAccommodationType: AccommodationType[] = [];

  separatorKeysCodes: number[] = [ENTER, COMMA];
  filteredspecialArounds!: Observable<string[]>;
  specialArounds: string[] = [];
  listSpecialAround: string[] = [];

  selectedImages!: FileList;

  @ViewChild('specialAroundInput')
  specialAroundInput!: ElementRef<HTMLInputElement>;

  announcer = inject(LiveAnnouncer);

  constructor(
    private $formBuilder: FormBuilder,
    private $accommodationService: AccommodationService,
    private $accommodationTypeService: AccommodationTypeService,
    private $specialAroundService: SpecialAroundService
  ) {
    this.buildFormGroup();

    this.$accommodationTypeService
      .getAllAccommodationType()
      .subscribe((response) => {
        this.listAccommodationType = response;
      });

    this.$specialAroundService.getAllSpecialAround().subscribe((response) => {
      this.listSpecialAround = response.map((sp) => sp.description);

      this.filteredspecialArounds = this.form
        .get('specialAroundCtrl')!
        .valueChanges.pipe(
          startWith(null),
          map((fruit: string | null) =>
            fruit ? this._filter(fruit) : this.listSpecialAround.slice()
          )
        );
    });
  }

  ngOnInit(): void {
    this.initComponentJquery();
  }

  initComponentJquery() {
    $(document).ready(function () {
      $('#file-input').fileinput({
        allowedFileTypes: ['image'],
        initialPreviewAsData: true,
        showUpload: false,
        showCancel: false,
      });

      flatpickr('#checkin', {
        enableTime: true,
        noCalendar: true,
        dateFormat: 'H:i',
        time_24hr: true,
      });

      flatpickr('#checkout', {
        enableTime: true,
        noCalendar: true,
        dateFormat: 'H:i',
        time_24hr: true,
      });
    });
  }

  buildFormGroup() {
    this.form = this.$formBuilder.group({
      accommodationName: new FormControl('', Validators.required),
      wardId: new FormControl(''),
      address: new FormControl('', Validators.required),
      specificAddress: new FormControl(''),
      phone: new FormControl('', Validators.required),
      accommodationType: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      description: new FormControl('', Validators.required),
      star: new FormControl(0, Validators.required),
      checkin: new FormControl('', Validators.required),
      checkout: new FormControl('', Validators.required),
      specialAroundCtrl: new FormControl(''),
      specialArounds: new FormControl({}),
      image: new FormControl(null),
    });
  }

  //=> Mat chips autocomplete
  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    if (value && !this.specialArounds.includes(value)) {
      this.specialArounds.push(value);
      this.form.get('specialArounds')!.setValue(this.specialArounds);
    }
    event.chipInput!.clear();
    this.form.get('specialAroundCtrl')!.setValue(null);
  }

  remove(fruit: string): void {
    const index = this.specialArounds.indexOf(fruit);
    if (index >= 0) {
      this.specialArounds.splice(index, 1);
      this.form.get('specialArounds')!.setValue(this.specialArounds);
      this.announcer.announce(`Removed ${fruit}`);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    if (!this.specialArounds.includes(event.option.viewValue)) {
      this.specialArounds.push(event.option.viewValue);
      this.form.get('specialArounds')!.setValue(this.specialArounds);
    }
    this.specialAroundInput.nativeElement.value = '';
    this.form.get('specialAroundCtrl')!.setValue(null);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.listSpecialAround.filter((fruit) =>
      fruit.toLowerCase().includes(filterValue)
    );
  }
  //=> Mat chips autocomplete

  onFileChange(event: any) {
    this.selectedImages = event.target.files;
  }

  create() {
    if (this.form.valid) {
      this.$accommodationService
        .createNewAccommodation(this.selectedImages, this.form.value)
        .subscribe({
          next: (response) => {
            console.log(response);
          },
          error: (error) => {
            console.log(error);
          },
        });
    }
  }
}
