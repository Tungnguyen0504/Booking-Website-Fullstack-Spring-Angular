import { Injectable } from '@angular/core';
import { Room } from '../model/Room.model';
import { BOOKING_STORAGE } from '../constant/Abstract.constant';

export interface BookingStorage {
  startDate: Date;
  endDate: Date;
  accommodationId: number;
  items: Item[];
}

export interface Item {
  quantity: number;
  room: Room;
}

@Injectable({
  providedIn: 'root',
})
export class BookingService {

  setBookingStorage(
    startDate: Date,
    endDate: Date,
    userId: number,
    accommodationId: number,
    items: Item[]
  ) {
    const bookingStorage = {
      startDate: startDate,
      endDate: endDate,
      userId: userId,
      accommodationId: accommodationId,
      items: items,
    };
    console.log(bookingStorage);
    localStorage.setItem(BOOKING_STORAGE, JSON.stringify(bookingStorage));
  }

  getBookingStorage(): any {
    const data = localStorage.getItem(BOOKING_STORAGE);
    if (data) {
      return JSON.parse(data);
    }
    return {};
  }
}
