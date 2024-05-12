import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSelect } from '@angular/material/select';
import { MatStepper } from '@angular/material/stepper';
import { AddressService } from 'src/app/service/address.service';
import { AlertService } from 'src/app/service/alert.service';

export interface DialogData {
  action: string;
  fullAddress: string;
  specificAddress: string;
  wardId: number;
  isComplete: boolean;
}

@Component({
  selector: 'app-form-address-dialog',
  templateUrl: './form-address-dialog.component.html',
  styleUrls: ['./form-address-dialog.component.css'],
})
export class FormAddressDialogComponent implements OnInit {
  @ViewChild('stepper') private stepper!: MatStepper;
  @ViewChild('districtSelect') private districtSelect!: MatSelect;
  @ViewChild('wardSelect') private wardSelect!: MatSelect;

  listProvince: any[] = [];
  listDistrict: any[] = [];
  listWard: any[] = [];

  selectedProvince: string = '';
  selectedDistrict: string = '';
  selectedWard: string = '';

  dialogForm: FormGroup = {} as FormGroup;
  firstForm: FormGroup = {} as FormGroup;
  secondForm: FormGroup = {} as FormGroup;
  thirdForm: FormGroup = {} as FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogData: DialogData,
    private dialogRef: MatDialogRef<FormAddressDialogComponent>,
    private $alertService: AlertService,
    private $formBuilder: FormBuilder,
    private $addressService: AddressService
  ) {}

  ngOnInit(): void {
    this.getListProvince();
    this.buildDialogForm();
  }

  buildDialogForm() {
    this.firstForm = this.$formBuilder.group({
      provinceId: ['', Validators.required],
    });
    this.secondForm = this.$formBuilder.group({
      districtId: ['', Validators.required],
    });
    this.thirdForm = this.$formBuilder.group({
      wardId: ['', Validators.required],
    });
    this.dialogForm = this.$formBuilder.group({
      fullAddress: new FormControl('', [Validators.required]),
      specificAddress: new FormControl('', [Validators.required]),
      firstForm: this.firstForm,
      secondForm: this.secondForm,
      thirdForm: this.thirdForm,
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

  onProvinceChanged($event: any) {
    this.listDistrict = [];
    this.listWard = [];
    this.selectedDistrict = '';
    this.selectedWard = '';
    this.districtSelect.value = null;
    this.wardSelect.value = null;

    this.selectedProvince = `${
      this.listProvince.find((p) => p.provinceId == $event.value).provinceName
    }, `;
    this.dialogForm.get('fullAddress')?.setValue(this.fullAddress);

    this.$addressService.getDistrictsByProvince($event.value).subscribe({
      next: (response) => {
        this.listDistrict = response;
      },
      error: (error) => {
        console.log(error);
      },
    });

    this.stepper.next();
  }

  onDistrictChanged($event: any) {
    this.listWard = [];
    this.selectedWard = '';
    this.wardSelect.value = null;

    this.selectedDistrict = `${
      this.listDistrict.find((p) => p.districtId == $event.value).districtName
    }, `;
    this.dialogForm.get('fullAddress')?.setValue(this.fullAddress);

    this.$addressService.getWardsByDistrict($event.value).subscribe({
      next: (response) => {
        this.listWard = response;
      },
      error: (error) => {
        console.log(error);
      },
    });

    this.stepper.next();
  }

  onWardChanged($event: any) {
    this.selectedWard = `${this.listWard.find((p) => p.wardId == $event.value).wardName}`;
    this.dialogForm.get('fullAddress')?.setValue(this.fullAddress);

    this.stepper.next();
  }

  get fullAddress() {
    return `${this.selectedProvince}${this.selectedDistrict}${this.selectedWard}`;
  }

  update() {
    if (this.dialogForm.valid) {
      this.dialogData.specificAddress = this.dialogForm.get('specificAddress')?.value;
      this.dialogData.wardId = this.thirdForm.get('wardId')?.value;
      this.dialogData.fullAddress = `${this.dialogForm.get('specificAddress')?.value}, ${
        this.fullAddress
      }`;
      this.dialogData.isComplete = true;

      this.dialogRef.close(this.dialogData);
    } else {
      this.$alertService.warning('Địa chỉ không hợp lệ');
    }
  }
}
