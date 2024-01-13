import {
  Component,
  OnInit,
} from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AccommodationType } from 'src/app/model/AccommodationType.model';
import { AccommodationTypeService } from 'src/app/service/accommodation-type.service';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { AlertService } from 'src/app/service/alert.service';
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
  selectedImages!: FileList;

  specialArounds: string[] = [];
  listSpecialAround: string[] = [];

  constructor(
    private $formBuilder: FormBuilder,
    private $accommodationService: AccommodationService,
    private $accommodationTypeService: AccommodationTypeService,
    private $specialAroundService: SpecialAroundService,
    private $alertService: AlertService
  ) {
    this.buildFormGroup();

    this.$accommodationTypeService
      .getAllAccommodationType()
      .subscribe((response) => {
        this.listAccommodationType = response;
      });

    this.$specialAroundService.getAllSpecialAround().subscribe((response) => {
      this.listSpecialAround = response.map((sp) => sp.description);
    });
  }

  onEmitter(data: any) {
    this.form.get('specialArounds')?.setValue(data);
  }

  ngOnInit(): void {
    this.initComponentJquery();
  }

  initComponentJquery() {
      $('#file-input').fileinput({
        allowedFileTypes: ['image'],
        initialPreviewAsData: true,
        showUpload: false,
        showCancel: false,
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
      specialArounds: new FormControl([], Validators.required),
    });
  }

  onFileChange(event: any) {
    this.selectedImages = event.target.files;
  }

  create() {
    if (this.form.valid) {
      this.$accommodationService
        .createNewAccommodation(this.selectedImages, this.form.value)
        .subscribe({
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
