import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, filter, of, switchMap } from 'rxjs';
import { User } from 'src/app/model/User.model';
import { AlertService } from 'src/app/service/alert.service';
import { UserService } from 'src/app/service/user.service';
import { Util } from 'src/app/util/util';

@Component({
  selector: 'app-user-information',
  templateUrl: './user-information.component.html',
  styleUrls: ['./user-information.component.css'],
})
export class UserInformationComponent implements OnInit {
  form: FormGroup = {} as FormGroup;
  user?: User;
  filePaths$: Observable<string[]> = of([]);

  constructor(
    private $formBuilder: FormBuilder,
    private $alertSerive: AlertService,
    private $userService: UserService
  ) {}

  ngOnInit(): void {
    this.buildFormGroup();
    this.initApi();
  }

  buildFormGroup() {
    this.form = this.$formBuilder.group({
      id: new FormControl('', Validators.required),
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

  initApi() {
    this.filePaths$ = this.$userService.getCurrentUser().pipe(
      filter((response) => response !== null),
      switchMap((response) => {
        this.form.patchValue({
          id: response!.id,
          firstName: response!.firstName,
          lastName: response!.lastName,
          email: response!.email,
          phoneNumber: response!.phoneNumber,
          dateOfBirth: response!.dateOfBirth,
          fullAddress: response!.address?.fullAddress,
          wardId: response!.address?.wardId,
          specificAddress: response!.address?.specificAddress,
          images: Util.convertBase64ToFileList(response!.files),
        });
        return of(response!);
      }),
      switchMap((response) => of(Util.getFileImages(response)))
    );
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
    if (this.form.valid) {
      console.log(this.form.value);
      this.$userService.update(this.form.value).subscribe({
        next: (response) => this.$alertSerive.success(response.message),
      });
    }
  }
}
