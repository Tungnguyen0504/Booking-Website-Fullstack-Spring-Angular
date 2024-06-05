import { Component, Input } from '@angular/core';
import { DATETIME_FORMAT2 } from 'src/app/constant/Abstract.constant';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { CartStorage } from 'src/app/service/booking.service';
import { Util } from 'src/app/util/util';

@Component({
  selector: 'app-booking-detail-summary',
  templateUrl: './booking-detail-summary.component.html',
  styleUrls: ['./booking-detail-summary.component.css'],
})
export class BookingDetailSummaryComponent {
  @Input() roomGuestQty?: any;
  @Input() cartStorage?: CartStorage;
  @Input() accommodation?: Accommodation;

  constructor() {}

  formatDate(date: Date) {
    return Util.formatDate(date, DATETIME_FORMAT2);
  }

  subtractDate1(var1: Date, var2: Date) {
    return Util.subtractDate1(var1, var2);
  }
}
