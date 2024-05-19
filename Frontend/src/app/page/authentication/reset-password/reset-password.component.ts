import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, NgForm, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/model/User.model';
import { AlertService } from 'src/app/service/alert.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css'],
})
export class ResetPasswordComponent implements OnInit, AfterViewInit {
  @ViewChild('form', { static: false }) form!: NgForm;

  user?: User;
  invalidForm: boolean = false;

  constructor(
    private router: Router,
    private $alertService: AlertService,
    private $userService: UserService
  ) {}

  ngOnInit(): void {
    this.$userService.getCurrentUser().subscribe({
      next: (res) => (this.user = res!),
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
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

  submit() {
    if (this.form.invalid) {
      this.invalidForm = true;
      return;
    }
    this.$userService
      .resetPassword({
        id: this.user?.id,
        password: this.form.controls['password'].value,
      })
      .subscribe({
        next: (res) => {
          if (res) {
            this.$alertService.success('Đặt lại mật khẩu thành công.');
          }
        },
      });
  }
}
