import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/service/alert.service';
import { UserService } from 'src/app/service/user.service';

export interface VerificationDialogData {
  email: string;
}

@Component({
  selector: 'app-form-verification-dialog',
  templateUrl: './form-verification-dialog.component.html',
  styleUrls: ['./form-verification-dialog.component.css'],
})
export class FormVerificationDialogComponent implements OnInit {
  @Input() email: string = '';

  formVerify: FormGroup = {} as FormGroup;

  test: string = '';

  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogData: VerificationDialogData,
    private dialogRef: MatDialogRef<FormVerificationDialogComponent>,
    private router: Router,
    private formBuilder: FormBuilder,
    private alertService: AlertService,
    private $userService: UserService
  ) {}

  ngOnInit(): void {
    // console.log(this.email);
    // this.$userService.VerifyEmail(this.email).subscribe({
    //   next: (response) => {
    //     this.test = response.verifyCode;
    //   },
    // });
    this.buildFormGroup();
  }

  buildFormGroup() {
    this.formVerify = this.formBuilder.group({
      verifyCode: new FormControl('', [Validators.required, this.lengthCodeValidator()]),
    });
  }

  lengthCodeValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      return control.value && control.value.length !== 6 ? { invalidLengthCode: true } : null;
    };
  }

  submit() {
    // if (111111 != this.formVerify.get('verifyCode')?.value) {
    //   this.alertService.error('Mã xác nhận không đúng');
    //   return;
    // }
    // this.closeModal();
    if (this.formVerify.invalid) {
      return;
    }
    console.log(this.formVerify.controls['verifyCode'].errors?.['invalidLengthCode']);
  }
}
