import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import {
  AbstractControl,
  NgForm,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/service/alert.service';
import { AuthenticationService } from 'src/app/service/authentication.service';

declare var $: any;

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements AfterViewInit, OnInit {
  @ViewChild('form', { static: false }) form!: NgForm;
  @ViewChild('formVerifyCode', { static: false }) formVerifyCode!: NgForm;

  readonly VERIFICATION_CODE = 'VERIFICATION_CODE';
  emailSent: boolean = false;

  constructor(
    private router: Router,
    private alertService: AlertService,
    private authService: AuthenticationService
  ) {}

  ngOnInit(): void {}

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

  register() {
    const randomVerificationNumber = Math.floor(
      100000 + Math.random() * 900000
    );
    localStorage.setItem(
      this.VERIFICATION_CODE,
      randomVerificationNumber.toString()
    );

    console.log(localStorage.getItem(this.VERIFICATION_CODE));

    this.authService
      .sendVerificationCode(
        this.form.value.email,
        randomVerificationNumber.toString()
      )
      .subscribe(
        (data) => {},
        (error) => {
          console.log(error);
          this.alertService.error(error);
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

    this.authService.register(this.form.value).subscribe(
      (response) => {
        $('#verifyCodeModal').modal('hide');
        this.alertService.success('Đăng ký thành công');
      },
      (error) => {
        this.alertService.error(error.error);
      }
    );
  }
}
