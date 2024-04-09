import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { NgForm, Validators } from '@angular/forms';
import { ACTION_LOGIN } from 'src/app/constant/Abstract.constant';
import { AlertService } from 'src/app/service/alert.service';
import { AuthenticationService } from 'src/app/service/authentication.service';

declare var $: any;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements AfterViewInit {
  @ViewChild('form', { static: false }) form!: NgForm;

  emailSent: boolean = false;
  verificationCode: string = '';
  action: string = '';

  constructor(private alertService: AlertService, private authService: AuthenticationService) {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.form.controls['email'].addValidators([Validators.required, Validators.email]);
      this.form.controls['email'].updateValueAndValidity();

      this.form.controls['password'].addValidators([
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(16),
      ]);
      this.form.controls['password'].updateValueAndValidity();
    }, 0);

    this.action = ACTION_LOGIN;
  }

  get loginOrRegisterButtonDisable(): boolean {
    return !!this.form?.invalid;
  }

  verifiy() {
    const loginRequest = {
      email: this.form.value.email,
      password: this.form.value.password,
    };
    this.authService.verifyLogin(loginRequest).subscribe(
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
