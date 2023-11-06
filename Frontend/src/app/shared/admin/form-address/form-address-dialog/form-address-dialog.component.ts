import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatStepper } from '@angular/material/stepper';
import { AddressService } from 'src/app/service/address.service';

declare var $: any;

export interface DialogData {
  action: string;
  address: string;
  specificAddress: string;
  wardId: number;
}

@Component({
  selector: 'app-form-address-dialog',
  templateUrl: './form-address-dialog.component.html',
  styleUrls: ['./form-address-dialog.component.css'],
})
export class FormAddressDialogComponent implements OnInit {
  @ViewChild('stepper') private stepper!: MatStepper;
  // @Input() data: any;

  listProvince: any[] = [];
  listDistrict: any[] = [];
  listWard: any[] = [];
  dialogForm: FormGroup = {} as FormGroup;
  provinceForm: FormGroup = {} as FormGroup;
  districtForm: FormGroup = {} as FormGroup;
  wardForm: FormGroup = {} as FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<FormAddressDialogComponent>,
    private $formBuilder: FormBuilder,
    private $addressService: AddressService
  ) {}

  ngOnInit(): void {
    this.getListProvince();
    this.buildDialogForm();
  }

  buildDialogForm() {
    this.provinceForm = this.$formBuilder.group({
      selectedProvince: ['', Validators.required],
    });
    this.districtForm = this.$formBuilder.group({
      selectedDistrict: ['', Validators.required],
    });
    this.wardForm = this.$formBuilder.group({
      selectedWard: ['', Validators.required],
    });
    this.dialogForm = this.$formBuilder.group({
      address: new FormControl('', [Validators.required]),
      specificAddress: new FormControl('', [Validators.required]),
      provinceForm: this.provinceForm,
      districtForm: this.districtForm,
      wardForm: this.wardForm,
    });
  }

  getListProvince() {
    this.$addressService.getAllProvince().subscribe({
      next: (response) => {
        this.listProvince = response;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  onProvinceChanged(selectedValue: string) {
    this.stepper.next();

    const provinceName = this.listProvince.filter(
      (p) => p.provinceId == selectedValue
    )[0].provinceName;
    const oldAddressValue = this.dialogForm.get('address')?.value;
    const newAddressValue =
      oldAddressValue == ''
        ? `${provinceName}`
        : `${oldAddressValue}, ${provinceName}`;
    this.dialogForm.get('address')?.setValue(newAddressValue);

    const selectedProvince = this.provinceForm.get('selectedProvince')?.value;
    if (selectedProvince) {
      this.$addressService
        .getDistrictsByProvince(parseInt(selectedProvince))
        .subscribe({
          next: (response) => {
            this.listDistrict = response;
          },
          error: (error) => {
            console.log(error);
          },
        });
    }
  }

  onDistrictChanged(selectedValue: string) {
    this.stepper.next();

    const districtName = this.listDistrict.filter(
      (p) => p.districtId == selectedValue
    )[0].districtName;
    const oldAddressValue = this.dialogForm.get('address')?.value;
    const newAddressValue =
      oldAddressValue == ''
        ? `${districtName}`
        : `${oldAddressValue}, ${districtName}`;
    this.dialogForm.get('address')?.setValue(newAddressValue);

    const selectedDistrict = this.districtForm.get('selectedDistrict')?.value;
    if (selectedDistrict) {
      this.$addressService
        .getWardsByDistrict(parseInt(selectedDistrict))
        .subscribe({
          next: (response) => {
            this.listWard = response;
          },
          error: (error) => {
            console.log(error);
          },
        });
    }
  }

  onWardChanged(selectedValue: string) {
    const wardName = this.listWard.filter((p) => p.wardId == selectedValue)[0]
      .wardName;
    const oldAddressValue = this.dialogForm.get('address')?.value;
    const newAddressValue =
      oldAddressValue == '' ? `${wardName}` : `${oldAddressValue}, ${wardName}`;
    this.dialogForm.get('address')?.setValue(newAddressValue);

    $('#collapseAddress').removeClass('show');
  }

  update() {}

  test() {
    this.data.address = this.dialogForm.get('address')?.value;
    this.data.specificAddress = this.dialogForm.get('specificAddress')?.value;
    this.data.wardId = this.wardForm.get('selectedWard')?.value;
    if (
      this.data.address !== '' ||
      this.data.specificAddress !== '' ||
      this.data.wardId != 0
    ) {
      this.dialogRef.close(this.data);
    }
    // if (this.areAllStepsCompleted()) {
    //   console.log('completed');
    // } else {
    //   console.log('not completed');
    // }
    // if (this.dialogForm.valid) {
    // }
  }
}
