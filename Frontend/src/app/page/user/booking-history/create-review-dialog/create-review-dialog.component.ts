import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'src/app/service/alert.service';
import { ReviewService } from 'src/app/service/review.service';

@Component({
  selector: 'app-create-review-dialog',
  templateUrl: './create-review-dialog.component.html',
  styleUrls: ['./create-review-dialog.component.css'],
})
export class CreateReviewDialogComponent implements OnInit {
  form: FormGroup = {} as FormGroup;

  reviewInBooking: any;

  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    private dialogRef: MatDialogRef<CreateReviewDialogComponent>,
    private $formBuilder: FormBuilder,
    private $alertService: AlertService,
    private $reviewService: ReviewService
  ) {}

  ngOnInit(): void {
    this.buildFormGroup();
    this.$reviewService.getByBookingId(this.dialogData.bookingId).subscribe({
      next: (res) => {
        if (res) {
          this.reviewInBooking = res;
          this.form.setValue({
            clean: res.clean,
            amenity: res.amenity,
            service: res.service,
            description: res.description,
          });
        }
      },
    });
  }

  buildFormGroup() {
    this.form = this.$formBuilder.group({
      clean: new FormControl('', Validators.required),
      amenity: new FormControl('', Validators.required),
      service: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
    });
  }

  submit() {
    if (this.form.invalid) {
      this.$alertService.error('Không hợp lệ');
      return;
    }
    const request = {
      clean: this.form.get('clean')?.value,
      amenity: this.form.get('amenity')?.value,
      service: this.form.get('service')?.value,
      description: this.form.get('description')?.value,
      bookingId: this.dialogData.bookingId,
    };
    this.$reviewService.create(request).subscribe({
      next: () => {
        this.$alertService.success('Đánh giá thành công');
        this.dialogRef.close({
          isComplete: true,
        });
      },
    });
  }
}
