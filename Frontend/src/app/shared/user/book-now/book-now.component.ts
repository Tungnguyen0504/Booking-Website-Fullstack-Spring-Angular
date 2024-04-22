import { formatDate } from '@angular/common';
import { AfterViewInit, Component, ElementRef } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import flatpickr from 'flatpickr';
import rangePlugin from 'flatpickr/dist/plugins/rangePlugin';
import { DATETIME_FORMAT3 } from 'src/app/constant/Abstract.constant';
import { AlertService } from 'src/app/service/alert.service';
import { BookingService } from 'src/app/service/booking.service';
import { Util } from 'src/app/util/util';

declare var $: any;

@Component({
  selector: 'app-book-now',
  templateUrl: './book-now.component.html',
  styleUrls: ['./book-now.component.css'],
})
export class BookNowComponent {
  formSearch: FormGroup = {} as FormGroup;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private $alertService: AlertService,
    private $bookingService: BookingService
  ) {
    this.buildFormGroup();
  }

  buildFormGroup() {
    this.formSearch = this.formBuilder.group({
      keySearch: new FormControl('', Validators.required),
      fromDate: new FormControl('', Validators.required),
      toDate: new FormControl('', Validators.required),
    });
  }

  search() {
    if (this.formSearch.valid) {
      this.$bookingService.saveCartDate(
        this.formSearch.get('fromDate')?.value,
        this.formSearch.get('toDate')?.value
      );
      // this.router.navigate(['/search-accommodation'], {
      //   queryParams: {
      //     keySearch: this.formSearch.get('keySearch')?.value,
      //     fromDate: Util.formatDate(this.formSearch.get('fromDate')?.value, DATETIME_FORMAT3),
      //     toDate: Util.formatDate(this.formSearch.get('toDate')?.value, DATETIME_FORMAT3),
      //   },
      // });
    } else {
      var msg = '';
      if (this.formSearch.get('keySearch')?.errors) {
        msg += 'Điểm đến không hợp lệ <br/>';
      }
      if (this.formSearch.get('fromDate')?.errors) {
        msg += 'Ngày nhận không hợp lệ <br/>';
      }
      if (this.formSearch.get('toDate')?.errors) {
        msg += 'Ngày trả không hợp lệ <br/>';
      }
      this.$alertService.error(msg);
    }
  }
}
