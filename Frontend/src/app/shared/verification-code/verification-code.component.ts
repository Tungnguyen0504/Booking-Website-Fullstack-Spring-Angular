import { AfterViewInit, Component, Input, ViewChild } from '@angular/core';
import { NgForm, Validators } from '@angular/forms';
import { AlertService } from 'src/app/service/alert.service';
import { AuthenticationService } from 'src/app/service/authentication.service';

@Component({
  selector: 'app-verification-code',
  templateUrl: './verification-code.component.html',
  styleUrls: ['./verification-code.component.css'],
})
export class VerificationCodeComponent implements AfterViewInit {
  @ViewChild('formVerifyCode', { static: false }) formVerifyCode!: NgForm;
  @Input() form: any;

  readonly VERIFICATION_CODE = 'VERIFICATION_CODE';

  constructor(
    private alertService: AlertService,
    private authService: AuthenticationService
  ) {
    console.log(this.form)
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.formVerifyCode.controls['verificationCode'].addValidators([
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(6),
      ]);
      this.formVerifyCode.controls['verificationCode'].updateValueAndValidity();
    }, 0);
    console.log(this.form)
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
      (response) => {},
      (error) => {
        this.alertService.error(error.error);
      }
    );
  }
}
