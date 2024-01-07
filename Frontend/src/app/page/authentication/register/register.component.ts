import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import {
  AbstractControl,
  NgForm,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { ACTION_REGISTER } from 'src/app/constant/Abstract.constant';
import { AlertService } from 'src/app/service/alert.service';
import { AuthenticationService } from 'src/app/service/authentication.service';

declare var $: any;

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements AfterViewInit {
  @ViewChild('form', { static: false }) form!: NgForm;

  emailSent: boolean = false;
  verificationCode: string = '';
  action: string = "";

  constructor(
    private alertService: AlertService,
    private authService: AuthenticationService
  ) {}

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.form.controls['phoneNumber'].addValidators([
        Validators.required,
        Validators.pattern('^(([+]84)[3|5|7|8|9]|0[3|5|7|8|9])+([0-9]{8})$'),
      ]);
      this.form.controls['phoneNumber'].updateValueAndValidity();

      this.form.controls['email'].addValidators([
        Validators.required,
        Validators.email,
      ]);
      this.form.controls['email'].updateValueAndValidity();

      this.form.controls['password'].addValidators([
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(16),
      ]);
      this.form.controls['password'].updateValueAndValidity();

      //check confirm password
      const validator = (): ValidatorFn => {
        return (
          control: AbstractControl<any, any>
        ): ValidationErrors | null => {
          const password = this.form.controls['password'].value;
          if (password !== control.value) {
            return { passwordMismatch: true };
          }
          return null;
        };
      };
      this.form.controls['rePassword'].addValidators([
        Validators.required,
        validator(),
      ]);
      this.form.controls['rePassword'].updateValueAndValidity();
    }, 0);

    this.action = ACTION_REGISTER;
  }

  get loginOrRegisterButtonDisable(): boolean {
    return !!this.form?.invalid;
  }

  verifiy() {
    const registerRequest = {
      email: this.form.value.email,
      phoneNumber: this.form.value.phoneNumber,
      password: this.form.value.password
    };
    this.authService
      .verifyRegister(registerRequest)
      .subscribe(
        (data) => {
          this.verificationCode = data;
          this.openModal();
          console.log(this.verificationCode);
        },
        (error) => {
          this.alertService.error(error.error.message);
        }
      );
  }

  openModal(): void {
    $('#verifyCodeModal').modal('show');
  }

  closeModal(): void {
    $('#verifyCodeModal').modal('hide');
  }
}
