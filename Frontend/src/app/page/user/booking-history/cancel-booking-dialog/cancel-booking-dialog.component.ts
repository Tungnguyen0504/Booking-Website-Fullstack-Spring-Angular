import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'src/app/service/alert.service';
import { BookingService } from 'src/app/service/booking.service';

@Component({
  selector: 'app-cancel-booking-dialog',
  templateUrl: './cancel-booking-dialog.component.html',
  styleUrls: ['./cancel-booking-dialog.component.css'],
})
export class CancelBookingDialogComponent implements OnInit {
  form: FormGroup = {} as FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    private dialogRef: MatDialogRef<CancelBookingDialogComponent>,
    private $formBuilder: FormBuilder,
    private $alertService: AlertService,
    private $bookingService: BookingService
  ) {}

  ngOnInit(): void {
    this.buildFormGroup();
  }

  buildFormGroup() {
    this.form = this.$formBuilder.group({
      reason: new FormControl('', Validators.required),
    });
  }

  submit() {
    if (this.form.invalid) {
      this.$alertService.error('Không hợp lệ');
      return;
    }
    this.$bookingService
      .changeStatus({
        bookingId: this.dialogData.bookingId,
        status: 'CANCELED',
        reason: this.form.get('reason')?.value,
      })
      .subscribe({
        next: () => {
          this.$alertService.success('Hủy đặt phòng thành công');
          this.dialogRef.close({
            isComplete: true,
          });
        },
      });
  }
}
