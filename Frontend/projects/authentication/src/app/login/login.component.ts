import { Component, ViewChild } from '@angular/core';
import { NgForm, Validators } from '@angular/forms';
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

  emailSent: boolean = false;
  verificationCode: string = '';

  constructor(
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
    }, 0);
  }

  get loginOrRegisterButtonDisable(): boolean {
    return !!this.form?.invalid;
  }

  login() {
    this.authService
      .verification(this.form.value.email, this.form.value.password)
      .subscribe(
        (data) => {
          this.verificationCode = data;
          this.openModal();
          console.log(this.verificationCode);
        },
        (error) => {
          this.alertService.error(error.error.errorMessage);
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
