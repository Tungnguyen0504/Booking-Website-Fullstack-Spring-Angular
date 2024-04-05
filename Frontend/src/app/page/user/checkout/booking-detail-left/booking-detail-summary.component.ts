import { Component, Input } from '@angular/core';
import { DATETIME_FORMAT2, DATETIME_FORMAT3 } from 'src/app/constant/Abstract.constant';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { CartStorage } from 'src/app/service/booking.service';
import { Util } from 'src/app/util/util';

@Component({
  selector: 'app-booking-detail-summary',
  templateUrl: './booking-detail-summary.component.html',
  styleUrls: ['./booking-detail-summary.component.css'],
})
export class BookingDetailSummaryComponent {
  @Input() cartStorage?: CartStorage;
  @Input() accommodation?: Accommodation;

  constructor() {
    console.log(typeof this.cartStorage?.fromDate);
  }

  formatDate(date: Date) {
    return Util.formatDate(date, DATETIME_FORMAT3);
  }
}
