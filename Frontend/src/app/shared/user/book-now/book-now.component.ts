import { formatDate } from '@angular/common';
import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import flatpickr from 'flatpickr';
import rangePlugin from 'flatpickr/dist/plugins/rangePlugin';
import * as moment from 'moment';
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
    private $elementRef: ElementRef,
    private $formBuilder: FormBuilder,
    private $bookingService: BookingService
  ) {
    this.buildFormGroup();
  }

  ngAfterViewInit(): void {
    flatpickr('#dateFrom', {
      minDate: 'today',
      dateFormat: 'y-M-d',
      plugins: [rangePlugin({ input: '#dateTo' })],
      onChange: (selectedDates) => {
        if (selectedDates.length === 2) {
          this.formSearch
            .get('dateFrom')
            ?.setValue(formatDate(selectedDates[0], 'yyyy-MM-dd', 'en-US'));
          this.formSearch
            .get('dateTo')
            ?.setValue(formatDate(selectedDates[1], 'yyyy-MM-dd', 'en-US'));
        }
      },
    });

    flatpickr('#dateTo', {
      minDate: 'today',
      dateFormat: 'y-M-d',
      plugins: [rangePlugin({ input: '#dateFrom' })],
      onChange: (selectedDates) => {
        if (selectedDates.length === 2) {
          this.formSearch
            .get('dateFrom')
            ?.setValue(formatDate(selectedDates[0], '', 'en-US'));
          this.formSearch
            .get('dateTo')
            ?.setValue(formatDate(selectedDates[1], 'yyyy-MM-dd', 'en-US'));
        }
      },
    });
  }

  buildFormGroup() {
    this.formSearch = this.$formBuilder.group({
      keyword: new FormControl('', Validators.required),
      dateFrom: new FormControl('', Validators.required),
      dateTo: new FormControl('', Validators.required),
    });
  }

  search() {
    const dateFrom = this.$elementRef.nativeElement.querySelector('#dateFrom');
    const dateTo = this.$elementRef.nativeElement.querySelector('#dateTo');

    this.formSearch.get('dateFrom')?.setValue(dateFrom.value);
    this.formSearch.get('dateTo')?.setValue(dateTo.value);

    this.$bookingService.saveCartDate(
      new Date(dateFrom.value),
      new Date(dateTo.value)
    );
    console.log(this.$bookingService.getCart());
  }
}
