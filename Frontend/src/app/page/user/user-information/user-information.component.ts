import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-user-information',
  templateUrl: './user-information.component.html',
  styleUrls: ['./user-information.component.css'],
})
export class UserInformationComponent {
  form: FormGroup = {} as FormGroup;

  constructor(private $formBuilder: FormBuilder) {}

  buildFormGroup() {
    this.form = this.$formBuilder.group({
      firstName: new FormGroup('', Validators.required),
      lastName: new FormGroup('', Validators.required),
      email: new FormGroup('', Validators.required),
      phoneNumber: new FormGroup('', Validators.required),
      dateOfBirth: new FormGroup('', Validators.required),
    });
  }
}
