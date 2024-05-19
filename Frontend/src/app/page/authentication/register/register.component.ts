import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, NgForm, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ACTION_REGISTER } from 'src/app/constant/Abstract.constant';
import { AlertService } from 'src/app/service/alert.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { FormVerificationDialogComponent } from 'src/app/shared/generic/form-verification-dialog/form-verification-dialog.component';

declare var $: any;

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements AfterViewInit {
  @ViewChild('form', { static: false }) form!: NgForm;

  invalidForm: boolean = false;

  constructor(
    private router: Router,
    private dialog: MatDialog,
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

      this.form.controls['email'].addValidators([Validators.required, Validators.email]);
      this.form.controls['email'].updateValueAndValidity();

      this.form.controls['password'].addValidators([
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(16),
      ]);
      this.form.controls['password'].updateValueAndValidity();

      const validator = (): ValidatorFn => {
        return (control: AbstractControl<any, any>): ValidationErrors | null => {
          const password = this.form.controls['password'].value;
          if (password !== control.value) {
            return { passwordMismatch: true };
          }
          return null;
        };
      };
      this.form.controls['rePassword'].addValidators([Validators.required, validator()]);
      this.form.controls['rePassword'].updateValueAndValidity();
    }, 0);
  }

  verifiy() {
    if (this.form.invalid) {
      this.invalidForm = true;
      return;
    }
    const dialogRef = this.dialog.open(FormVerificationDialogComponent, {
      data: {
        email: this.form.controls['email'].value,
      },
      position: {
        top: '200px',
      },
      width: '576px',
      disableClose: true,
      autoFocus: false,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result && result.isComplete) {
        this.authService.register(this.form.value).subscribe({
          next: () => {
            this.router
              .navigateByUrl('/login')
              .then(() => this.alertService.success('Đăng ký thành công.'));
          },
        });
      }
    });
  }
}
