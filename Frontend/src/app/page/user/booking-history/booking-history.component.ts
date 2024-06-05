import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';
import { Observable, delay, forkJoin, of, switchMap } from 'rxjs';
import { DATETIME_FORMAT2, DATETIME_FORMAT3 } from 'src/app/constant/Abstract.constant';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { BasePagingResponse } from 'src/app/model/response/BasePagingRequest.model';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { BasePagingService } from 'src/app/service/base-paging.service';
import { BookingService } from 'src/app/service/booking.service';
import { RoomService } from 'src/app/service/room.service';
import { Util } from 'src/app/util/util';

@Component({
  selector: 'app-booking-history',
  templateUrl: './booking-history.component.html',
  styleUrls: ['./booking-history.component.css'],
})
export class BookingHistoryComponent implements OnInit {
  listBooking: any;
  listAccommodation: Accommodation[] = [];

  searchRequest: any = {
    filterRequest: [],
    sortRequest: [],
    currentPage: 0,
    totalPage: 3,
  };

  totalItem: number = 0;
  pageOptions: number[] = [3, 5, 10, 25, 50];

  constructor(
    private $basePagingService: BasePagingService,
    private $bookingService: BookingService,
    private $roomService: RoomService,
    private $accommodationService: AccommodationService
  ) {}

  ngOnInit(): void {
    this.$basePagingService.pushSortRequest('createdAt', 'DESC', this.searchRequest.sortRequest);
    this.getBookings();
  }

  getBookings() {
    this.$bookingService.getBookings(this.searchRequest).subscribe({
      next: (response: BasePagingResponse) => {
        if (response) {
          this.totalItem = response.totalItem;
          this.searchRequest.currentPage = response.currentPage;
          this.searchRequest.totalPage = response.totalPage;
          this.listBooking = response.data;

          response.data.forEach((element: any) => {
            this.$accommodationService.getById(element.accommodationId).subscribe({
              next: (accommodations) => this.listAccommodation.push(accommodations),
            });
          });
        }
      },
    });
  }

  onPageChange($event: any) {
    this.searchRequest.currentPage = $event.pageIndex;
    this.searchRequest.totalPage = $event.pageSize;
    this.getBookings();
  }

  getAccommodation(accommodationId: number): Accommodation {
    return this.listAccommodation.find((acc) => acc.accommodationId === accommodationId)!;
  }

  formatDate(param: string) {
    const date = Util.parseDate2(param, DATETIME_FORMAT3);
    return Util.formatDate(date, DATETIME_FORMAT2);
  }

  subtractDateToNow(var1: string) {
    const date1 = Util.parseDate2(var1, DATETIME_FORMAT3);
    return Util.subtractDate1(date1, new Date());
  }

  subtract2DaysFromNow() {
    return Util.formatDate(moment().subtract(2, 'days').toDate(), DATETIME_FORMAT2);
  }
}
