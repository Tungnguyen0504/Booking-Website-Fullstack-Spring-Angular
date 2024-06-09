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
  formVerify: FormGroup = {} as FormGroup;
  code: string = '';
  invalidForm: boolean = false;

  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogData: VerificationDialogData,
    private dialogRef: MatDialogRef<FormVerificationDialogComponent>,
    private formBuilder: FormBuilder,
    private alertService: AlertService,
    private $userService: UserService
  ) {}

  ngOnInit(): void {
    this.buildFormGroup();
    this.initApi();
  }

  initApi() {
    this.$userService.verifyEmail(this.dialogData.email).subscribe({
      next: (response) => {
        this.code = response.verifyCode;
        console.log(this.code);
      },
    });
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
    if (this.formVerify.invalid) {
      this.invalidForm = true;
      return;
    }
    if (this.code != this.formVerify.get('verifyCode')?.value) {
      this.alertService.error('Mã xác nhận không đúng');
      return;
    }
    this.dialogRef.close({ isComplete: true });
  }
}
