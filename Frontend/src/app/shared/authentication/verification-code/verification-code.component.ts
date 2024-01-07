import { AfterViewInit, Component, Input, ViewChild } from '@angular/core';
import { NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import {
  ACTION_LOGIN,
  ACTION_REGISTER,
  JWT_TOKEN,
} from 'src/app/constant/Abstract.constant';
import { AlertService } from 'src/app/service/alert.service';
import { AuthenticationService } from 'src/app/service/authentication.service';

declare var $: any;

@Component({
  selector: 'app-verification-code',
  templateUrl: './verification-code.component.html',
  styleUrls: ['./verification-code.component.css'],
})
export class VerificationCodeComponent implements AfterViewInit {
  @ViewChild('formVerifyCode', { static: false }) formVerifyCode!: NgForm;
  @Input() formLogin!: NgForm;
  @Input() formRegister!: NgForm;
  @Input() verifyCode: string = '';
  @Input() email: string = '';
  @Input() action: string = '';

  readonly VERIFICATION_CODE = 'VERIFICATION_CODE';

  constructor(
    private router: Router,
    private alertService: AlertService,
    private authService: AuthenticationService
  ) {}

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.formVerifyCode.controls['verificationCode'].addValidators([
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(6),
      ]);
      this.formVerifyCode.controls['verificationCode'].updateValueAndValidity();
    }, 0);
  }

  verifyCodeAction() {
    if (this.verifyCode != this.formVerifyCode.value.verificationCode) {
      this.alertService.error('Mã xác nhận không đúng');
      return;
    }

    if (this.action === ACTION_LOGIN) {
      this.authService.login(this.formLogin.value).subscribe({
        next: (response) => {
          localStorage.setItem(JWT_TOKEN, response.accessToken.toString());
          this.navigateToHomePage();
          console.log("a: " + localStorage.getItem(JWT_TOKEN));
        },
        error: (error) => {
          console.log(error);
          this.alertService.error(error.error.message);
        },
      });
    } else if (this.action === ACTION_REGISTER) {
      this.authService.register(this.formRegister.value).subscribe(
        (response) => {
          this.alertRegisterSuccess();
        },
        (error) => {
          console.log(error);
          this.alertService.error(error.error.message);
        }
      );
    }
  }

  navigateToHomePage() {
    $('#verifyCodeModal').modal('hide');
    this.router.navigate(['/user/home']).then(() => window.location.reload());
  }

  alertRegisterSuccess() {
    $('#verifyCodeModal').modal('hide');
    this.alertService.success('Đăng ký thành công.');
  }
}
