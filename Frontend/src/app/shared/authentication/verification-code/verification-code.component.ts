import { AfterViewInit, Component, Input, ViewChild } from '@angular/core';
import { NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JWT_TOKEN } from 'src/app/constant/Abstract.constant';
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
  @Input() verifyCode: string = '';
  @Input() email: string = '';

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
    console.log(this.verifyCode);
    console.log(this.email);
    console.log(this.formLogin.value);
    
    if (this.verifyCode != this.formVerifyCode.value.verificationCode) {
      this.alertService.error('Mã xác nhận không đúng');
      return;
    }

    this.authService.login(this.formLogin.value).subscribe(
      (response) => {
        localStorage.setItem(JWT_TOKEN, response.accessToken.toString());
        this.navigateToHomePage();
        console.log(localStorage.getItem(JWT_TOKEN));
      },
      (error) => {
        console.log(error);
        this.alertService.error(error.error.errorMessage);
      }
    );
  }

  navigateToHomePage() {
    $('#verifyCodeModal').modal('hide');
    this.router.navigate(['/user/home']).then(() => window.location.reload());
  }
}