import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-user-information',
  templateUrl: './user-information.component.html',
  styleUrls: ['./user-information.component.css'],
})
export class UserInformationComponent implements OnInit {
  form: FormGroup = {} as FormGroup;

  constructor(private $formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.buildFormGroup();
  }

  buildFormGroup() {
    this.form = this.$formBuilder.group({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      phoneNumber: new FormControl('', Validators.required),
      dateOfBirth: new FormControl('', Validators.required),
      fullAddress: new FormControl('', Validators.required),
      wardId: new FormControl('', Validators.required),
      specificAddress: new FormControl('', Validators.required),
      images: new FormControl([]),
    });
  }

  onAddressEmitter($event: any) {
    if ($event) {
      this.form.patchValue({
        fullAddress: $event.fullAddress,
        wardId: $event.wardId,
        specificAddress: $event.specificAddress,
      });
    }
  }

  onFileInputEmitter($event: any) {
    this.form.get('images')?.setValue($event);
  }

  submit() {
    console.log(this.form.value);
  }
}
