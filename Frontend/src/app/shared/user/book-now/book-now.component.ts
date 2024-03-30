import { formatDate } from '@angular/common';
import {
  AfterViewInit,
  Component,
  ElementRef,
  ViewChild,
} from '@angular/core';
import { NgForm } from '@angular/forms';
import flatpickr from 'flatpickr';
import rangePlugin from 'flatpickr/dist/plugins/rangePlugin';
import { BookingService } from 'src/app/service/booking.service';

declare var $: any;

@Component({
  selector: 'app-book-now',
  templateUrl: './book-now.component.html',
  styleUrls: ['./book-now.component.css'],
})
export class BookNowComponent implements AfterViewInit {
  @ViewChild('formSearch', { static: false }) formSearch!: NgForm;

  constructor(
    private $elementRef: ElementRef,
    private $bookingService: BookingService
  ) {}

  ngAfterViewInit(): void {
    flatpickr('#dateFrom', {
      minDate: 'today',
      dateFormat: 'd M Y',
      plugins: [rangePlugin({ input: '#dateTo' })],
      onChange: (selectedDates) => {
        if (selectedDates.length === 2) {
          this.formSearch.value.dateFrom = formatDate(
            selectedDates[0],
            'dd MM yyyy',
            'en-US'
          );
          this.formSearch.value.dateTo = formatDate(
            selectedDates[1],
            'dd MM yyyy',
            'en-US'
          );
        }
      },
    });

    flatpickr('#dateTo', {
      minDate: 'today',
      dateFormat: 'd M Y',
      plugins: [rangePlugin({ input: '#dateFrom' })],
      onChange: (selectedDates) => {
        if (selectedDates.length === 2) {
          this.formSearch.value.dateFrom = formatDate(
            selectedDates[0],
            'dd MM yyyy',
            'en-US'
          );
          this.formSearch.value.dateTo = formatDate(
            selectedDates[1],
            'dd MM yyyy',
            'en-US'
          );
        }
      },
    });
  }

  search() {
    const dateFrom = this.$elementRef.nativeElement.querySelector('#dateFrom');
    const dateTo = this.$elementRef.nativeElement.querySelector('#dateTo');

    this.formSearch.value.dateFrom = dateFrom.value;
    this.formSearch.value.dateTo = dateTo.value;

    this.$bookingService.saveCartDate(
      new Date(dateFrom.value),
      new Date(dateTo.value)
    );
    console.log(this.$bookingService.getCart());
  }
}
