import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import flatpickr from 'flatpickr';
import rangePlugin from 'flatpickr/dist/plugins/rangePlugin';

declare var $: any;

@Component({
  selector: 'app-book-now',
  templateUrl: './book-now.component.html',
  styleUrls: ['./book-now.component.css'],
})
export class BookNowComponent implements AfterViewInit {
  @ViewChild('formSearch', {static: false}) formSearch!: NgForm;

  constructor(
    private $elementRef: ElementRef
  ) {}

  ngAfterViewInit(): void {

    flatpickr("#dateFrom", {
        minDate: "today",
        dateFormat: "d M Y",
        plugins: [rangePlugin({ input: "#dateTo" })],
        // onChange: (selectedDates, instance) => {
        //   // Update both input fields' values when the range changes
        //   if (selectedDates.length === 2) {
        //     // dateFrom.value = selectedDates[0];
        //     // dateTo.value = selectedDates[1];
        //     // this.$cdr.detectChanges();
        //     this.formSearch.value.dateFrom = formatDate(selectedDates[0], 'dd MM yyyy', 'en-US');
        //     this.formSearch.value.dateTo = formatDate(selectedDates[1], 'dd MM yyyy', 'en-US');
        //     console.log(this.formSearch.value);
        //   }
        // },
      });
    
      flatpickr("#dateTo", {
        minDate: "today",
        dateFormat: "d M Y",
        plugins: [rangePlugin({ input: "#dateFrom" })],
        // onChange: (selectedDates) => {
        //   // Update both input fields' values when the range changes
        //   if (selectedDates.length === 2) {
        //     this.formSearch.value.dateFrom = formatDate(selectedDates[0], 'dd MM yyyy', 'en-US');
        //     this.formSearch.value.dateTo = formatDate(selectedDates[1], 'dd MM yyyy', 'en-US');
        //     console.log(this.formSearch.value);
        //   }
        // },
      });
  }

  search() {
    const dateFrom = this.$elementRef.nativeElement.querySelector('#dateFrom');
    const dateTo = this.$elementRef.nativeElement.querySelector('#dateTo');

    this.formSearch.value.dateFrom = dateFrom.value;
    this.formSearch.value.dateTo = dateTo.value;

    console.log(this.formSearch.value);

    
  }
}
