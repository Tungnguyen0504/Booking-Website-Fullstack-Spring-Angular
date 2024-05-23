import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'src/app/service/alert.service';
import { BookingService } from 'src/app/service/booking.service';

@Component({
  selector: 'app-change-status-dialog',
  templateUrl: './change-status-dialog.component.html',
  styleUrls: ['./change-status-dialog.component.css'],
})
export class ChangeStatusDialogComponent implements OnInit {
  form: FormGroup = {} as FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    private dialogRef: MatDialogRef<ChangeStatusDialogComponent>,
    private $alertService: AlertService,
    private $formBuilder: FormBuilder,
    private $bookingService: BookingService
  ) {}

  ngOnInit(): void {
    this.buildFormGroup();
  }

  buildFormGroup() {
    this.form = this.$formBuilder.group({
      status: new FormControl('', Validators.required),
      reason: new FormControl(''),
    });
  }

  selectionChange($event: any) {
    console.log(this.form.value);
  }

  submit() {
    if (this.form.invalid) {
      this.$alertService.error('Không hợp lệ');
      return;
    }
    this.$bookingService
      .changeStatus({
        bookingId: this.dialogData.bookingId,
        status: this.form.get('status')?.value,
        reason: this.form.get('reason')?.value,
      })
      .subscribe({
        next: (res) => {
          this.$alertService.success(res.message);
          this.dialogRef.close({
            isComplete: true,
          });
        },
      });
  }
}
