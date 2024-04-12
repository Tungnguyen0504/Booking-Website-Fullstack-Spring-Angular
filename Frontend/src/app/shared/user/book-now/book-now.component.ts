import { formatDate } from '@angular/common';
import { AfterViewInit, Component, ElementRef } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import flatpickr from 'flatpickr';
import rangePlugin from 'flatpickr/dist/plugins/rangePlugin';
import { DATETIME_FORMAT3 } from 'src/app/constant/Abstract.constant';
import { BookingService } from 'src/app/service/booking.service';
import { Util } from 'src/app/util/util';

declare var $: any;

@Component({
  selector: 'app-book-now',
  templateUrl: './book-now.component.html',
  styleUrls: ['./book-now.component.css'],
})
export class BookNowComponent implements AfterViewInit {
  formSearch: FormGroup = {} as FormGroup;

  constructor(
    private router: Router,
    private elementRef: ElementRef,
    private formBuilder: FormBuilder,
    private $bookingService: BookingService
  ) {
    this.buildFormGroup();
  }

  ngAfterViewInit(): void {
    flatpickr('#fromDate', {
      minDate: 'today',
      dateFormat: 'y-M-d',
      plugins: [rangePlugin({ input: '#toDate' })],
      onChange: (selectedDates) => {
        if (selectedDates.length === 2) {
          this.formSearch
            .get('fromDate')
            ?.setValue(formatDate(selectedDates[0], 'yyyy-MM-dd', 'en-US'));
          this.formSearch
            .get('toDate')
            ?.setValue(formatDate(selectedDates[1], 'yyyy-MM-dd', 'en-US'));
        }
      },
    });

    flatpickr('#toDate', {
      minDate: 'today',
      dateFormat: 'y-M-d',
      plugins: [rangePlugin({ input: '#fromDate' })],
      onChange: (selectedDates) => {
        if (selectedDates.length === 2) {
          this.formSearch.get('fromDate')?.setValue(formatDate(selectedDates[0], '', 'en-US'));
          this.formSearch
            .get('toDate')
            ?.setValue(formatDate(selectedDates[1], 'yyyy-MM-dd', 'en-US'));
        }
      },
    });
  }

  buildFormGroup() {
    this.formSearch = this.formBuilder.group({
      keyword: new FormControl('', Validators.required),
      fromDate: new FormControl('', Validators.required),
      toDate: new FormControl('', Validators.required),
    });
  }

  search() {
    const fromDate = this.elementRef.nativeElement.querySelector('#fromDate');
    const toDate = this.elementRef.nativeElement.querySelector('#toDate');

    this.formSearch.get('fromDate')?.setValue(fromDate.value);
    this.formSearch.get('toDate')?.setValue(toDate.value);

    this.$bookingService.saveCartDate(
      Util.parseDate(fromDate.value, DATETIME_FORMAT3),
      Util.parseDate(toDate.value, DATETIME_FORMAT3)
    );

    this.router.navigate(['/search-accommodation'], {
      queryParams: {
        searchKey: this.formSearch.get('keyword')?.value,
        fromDate: this.formSearch.get('fromDate')?.value,
        toDate: this.formSearch.get('toDate')?.value,
      },
    });
  }
}
