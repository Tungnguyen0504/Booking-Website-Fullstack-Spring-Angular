import { Component, ViewChild } from '@angular/core';
import {
  AbstractControl,
  NgForm,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationResponse } from 'src/app/model/response/AuthenticationResponse.model';
import { AlertService } from 'src/app/service/alert.service';
import { AuthenticationService } from 'src/app/service/authentication.service';

declare var $: any;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  @ViewChild('form', { static: false }) form!: NgForm;
  @ViewChild('formVerifyCode', { static: false }) formVerifyCode!: NgForm;

  readonly VERIFICATION_CODE = 'VERIFICATION_CODE';
  readonly JWT_TOKEN = 'JWT_TOKEN';

  emailSent: boolean = false;

  constructor(
    private router: Router,
    private alertService: AlertService,
    private authService: AuthenticationService
  ) {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    setTimeout(() => {
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

      this.formVerifyCode.controls['verificationCode'].addValidators([
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(6),
      ]);
      this.formVerifyCode.controls['verificationCode'].updateValueAndValidity();
    }, 0);
  }

  get loginOrRegisterButtonDisable(): boolean {
    return !!this.form?.invalid;
  }

  login() {
    const randomVerificationNumber = Math.floor(
      100000 + Math.random() * 900000
    );
    localStorage.setItem(
      this.VERIFICATION_CODE,
      randomVerificationNumber.toString()
    );

    this.authService
      .sendVerificationCode(
        this.form.value.email,
        randomVerificationNumber.toString()
      )
      .subscribe(
        (data) => {},
        (error) => {
          console.log(error);
          this.alertService.error(error.error);
        }
      );
  }

  verifyCode() {
    if (
      localStorage.getItem(this.VERIFICATION_CODE) !==
      this.formVerifyCode.value.verificationCode
    ) {
      this.alertService.error('Mã xác nhận không đúng');
      return;
    }

    this.authService.login(this.form.value).subscribe(
      (response) => {
        const authenticatedResponse = response as AuthenticationResponse;
        localStorage.setItem(this.JWT_TOKEN, authenticatedResponse.accessToken);
        this.navigateToHomePage();
      },
      (error) => {
        this.alertService.error(error.error);
      }
    );
  }

  navigateToHomePage() {
    $('#verifyCodeModal').modal('hide');
    this.router.navigate(['/user/home']);
  }
}
