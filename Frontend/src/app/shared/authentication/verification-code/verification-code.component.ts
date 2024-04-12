import { AfterViewInit, Component, Input, ViewChild } from '@angular/core';
import { NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import {
  ACTION_LOGIN,
  ACTION_REGISTER,
  JWT_TOKEN_STORAGE,
  TIME_EXPIRED,
} from 'src/app/constant/Abstract.constant';
import { AlertService } from 'src/app/service/alert.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { Util } from 'src/app/util/util';

declare var $: any;

@Component({
  selector: 'app-verification-code',
  templateUrl: './verification-code.component.html',
  styleUrls: ['./verification-code.component.css'],
})
export class VerificationCodeComponent implements AfterViewInit {
  @ViewChild('formVerifyCode', { static: false }) formVerifyCode!: NgForm;
  @Input() formLogin: NgForm = {} as NgForm;
  @Input() formRegister: NgForm = {} as NgForm;
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
    });
  }

  verifyCodeAction() {
    if (this.verifyCode != this.formVerifyCode.value.verificationCode) {
      this.alertService.error('Mã xác nhận không đúng');
      return;
    }

    if (this.action === ACTION_LOGIN) {
      this.authService.login(this.formLogin.value).subscribe({
        next: (response) => {
          Util.setLocal(JWT_TOKEN_STORAGE, response.accessToken, TIME_EXPIRED);
          this.navigateToHomePage();
        },
        error: (error) => {
          this.alertService.error(error.error.message);
        },
      });
    } else if (this.action === ACTION_REGISTER) {
      this.authService.register(this.formRegister.value).subscribe(
        () => {
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
    this.router.navigate(['/home']).then(() => window.location.reload());
  }

  alertRegisterSuccess() {
    $('#verifyCodeModal').modal('hide');
    this.alertService.success('Đăng ký thành công.');
  }
}
