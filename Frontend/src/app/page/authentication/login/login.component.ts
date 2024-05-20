import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { NgForm, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { JWT_TOKEN_STORAGE, TIME_EXPIRED } from 'src/app/constant/Abstract.constant';
import { AlertService } from 'src/app/service/alert.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { FormVerificationDialogComponent } from 'src/app/shared/generic/form-verification-dialog/form-verification-dialog.component';
import { Util } from 'src/app/util/util';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements AfterViewInit {
  @ViewChild('form', { static: false }) form!: NgForm;

  invalidForm: boolean = false;

  constructor(
    private router: Router,
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
  }

  verifiy() {
    if (this.form.invalid) {
      this.invalidForm = true;
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
        this.authService.login(this.form.value).subscribe({
          next: (response) => {
            if (response) {
              Util.setLocal(JWT_TOKEN_STORAGE, response.accessToken, TIME_EXPIRED);
              this.router.navigateByUrl('/home');
            }
          },
        });
      }
    });
  }
}
