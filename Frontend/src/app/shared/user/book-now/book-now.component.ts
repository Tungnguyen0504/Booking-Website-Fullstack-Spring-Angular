import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import {
  BOX_SEARCH_STORAGE,
  DATETIME_FORMAT3,
  TIME_EXPIRED,
} from 'src/app/constant/Abstract.constant';
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
    this.initData();
  }

  buildFormGroup() {
    this.formSearch = this.formBuilder.group({
      keySearch: new FormControl(''),
      fromDate: new FormControl('', Validators.required),
      toDate: new FormControl('', Validators.required),
    });
  }

  initData() {
    const boxSearchStorage = Util.getLocal(BOX_SEARCH_STORAGE);
    if (boxSearchStorage) {
      setTimeout(() => {
        this.formSearch.patchValue({
          keySearch: boxSearchStorage.keySearch,
          fromDate: Util.parseDate2(boxSearchStorage.fromDate, DATETIME_FORMAT3),
          toDate: Util.parseDate2(boxSearchStorage.toDate, DATETIME_FORMAT3),
        });
      });
    }
  }

  search() {
    if (this.formSearch.valid) {
      this.$bookingService.saveCartDate(
        this.formSearch.get('fromDate')?.value,
        this.formSearch.get('toDate')?.value
      );
      const boxSearchStorage = {
        keySearch: this.formSearch.get('keySearch')?.value,
        fromDate: Util.formatDate(this.formSearch.get('fromDate')?.value, DATETIME_FORMAT3),
        toDate: Util.formatDate(this.formSearch.get('toDate')?.value, DATETIME_FORMAT3),
      };
      Util.setLocal(BOX_SEARCH_STORAGE, boxSearchStorage, TIME_EXPIRED);
      this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
        this.router.navigate(['/search-accommodation']);
      });
    } else {
      var msg = '';
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
