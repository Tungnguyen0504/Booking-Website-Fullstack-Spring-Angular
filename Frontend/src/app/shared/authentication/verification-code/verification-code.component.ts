import {
  AfterViewInit,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  NgForm,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import {
  ACTION_LOGIN,
  ACTION_REGISTER,
  JWT_TOKEN_STORAGE,
  TIME_EXPIRED,
} from 'src/app/constant/Abstract.constant';
import { AlertService } from 'src/app/service/alert.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { UserService } from 'src/app/service/user.service';
import { Util } from 'src/app/util/util';

declare var $: any;

@Component({
  selector: 'app-verification-code',
  templateUrl: './verification-code.component.html',
  styleUrls: ['./verification-code.component.css'],
})
export class VerificationCodeComponent implements OnInit {
  @Input() email: string = '';
  @Output() eventEmitter: EventEmitter<boolean> = new EventEmitter<boolean>();

  formVerify: FormGroup = {} as FormGroup;

  verifyCode: string = '';

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private alertService: AlertService,
    private $userService: UserService
  ) {}

  ngOnInit(): void {
    setTimeout(() => {
      console.log(this.email);
      this.$userService.VerifyEmail(this.email).subscribe({
        next: (response) => {
          this.verifyCode = response.verifyCode;
        },
      });
    }, 3000);
  }

  buildFormGroup() {
    this.formVerify = this.formBuilder.group({
      verificationCode: new FormControl('', [
        Validators.required,
        this.lengthCodeValidator(),
        this.checkCorrectCodeValidator(),
      ]),
    });
  }

  lengthCodeValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      return control.value && control.value !== 6
        ? { invalidLengthCode: { value: control.value } }
        : null;
    };
  }

  checkCorrectCodeValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      return control.value && control.value === this.verifyCode
        ? { incorrectCode: { value: control.value } }
        : null;
    };
  }

  submit() {
    // if (this.verifyCode != this.formVerify.get('verifyCode')?.value) {
    //   this.alertService.error('Mã xác nhận không đúng');
    //   return;
    // }
    this.eventEmitter.emit(true);
    // this.closeModal();
    console.log(this.formVerify.valid);
  }

  navigateToHomePage() {
    $('#verifyCodeModal').modal('hide');
    this.router.navigate(['/home']).then(() => window.location.reload());
  }

  alertRegisterSuccess() {
    $('#verifyCodeModal').modal('hide');
    this.alertService.success('Đăng ký thành công.');
  }

  openModal(): void {
    $('#verifyCodeModal').modal('show');
  }

  closeModal(): void {
    $('#verifyCodeModal').modal('hide');
  }
}
