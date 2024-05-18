import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { NgForm, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ACTION_LOGIN } from 'src/app/constant/Abstract.constant';
import { AlertService } from 'src/app/service/alert.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import {
  FormVerificationDialogComponent,
  VerificationDialogData,
} from 'src/app/shared/generic/form-verification-dialog/form-verification-dialog.component';

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

  constructor(
    private dialog: MatDialog,
    private alertService: AlertService,
    private authService: AuthenticationService
  ) {}

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
    });

    this.action = ACTION_LOGIN;
  }

  get loginOrRegisterButtonDisable(): boolean {
    return !!this.form?.invalid;
  }

  verifyCodeEmitter($event: any) {
    console.log($event);
  }

  verifiy() {
    // const loginRequest = {
    //   email: this.form.value.email,
    //   password: this.form.value.password,
    // };
    // this.authService.verifyLogin(loginRequest).subscribe({
    //   next: (response) => {
    //     this.verificationCode = response;
    //     this.openModal();
    //     console.log(this.verificationCode);
    //   },
    //   error: (error) => {
    //     console.log(error);
    //     this.alertService.error(error.error.message);
    //   },
    // });
    const dialogRef = this.dialog.open(FormVerificationDialogComponent, {
      data: {
        email: this.form.controls['email'].value,
      },
      position: {
        top: '200px',
      },
      width: '576px',
      // disableClose: true,
      autoFocus: false,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result && result.isComplete) {
        console.log('oke');
      }
    });
  }
}
