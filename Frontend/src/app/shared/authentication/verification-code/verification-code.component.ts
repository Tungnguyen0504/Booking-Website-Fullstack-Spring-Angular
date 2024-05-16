import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/service/alert.service';
import { UserService } from 'src/app/service/user.service';

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

  test: string = '';

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private alertService: AlertService,
    private $userService: UserService
  ) {}

  ngOnInit(): void {
    // console.log(this.email);
    // this.$userService.VerifyEmail(this.email).subscribe({
    //   next: (response) => {
    //     this.test = response.verifyCode;
    //   },
    // });
    this.buildFormGroup();
  }

  buildFormGroup() {
    this.formVerify = this.formBuilder.group({
      verifyCode: new FormControl('', [Validators.required, this.lengthCodeValidator()]),
    });
  }

  lengthCodeValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      return control.value && control.value.length !== 6 ? { invalidLengthCode: true } : null;
    };
  }

  submit() {
    // if (111111 != this.formVerify.get('verifyCode')?.value) {
    //   this.alertService.error('Mã xác nhận không đúng');
    //   return;
    // }
    this.eventEmitter.emit(true);
    // this.closeModal();
    console.log(this.formVerify.controls['verifyCode'].errors?.['invalidLengthCode']);
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
