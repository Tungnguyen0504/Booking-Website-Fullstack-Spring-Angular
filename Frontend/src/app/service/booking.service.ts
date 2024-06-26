import { Injectable } from '@angular/core';
import { Room } from '../model/Room.model';
import {
  CART_STORAGE,
  JWT_TOKEN_STORAGE,
  PATH_V1,
  TIME_EXPIRED,
} from '../constant/Abstract.constant';
import { BehaviorSubject, Observable, of, switchMap } from 'rxjs';
import { UserService } from './user.service';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Util } from '../util/util';
import { BasePagingRequest } from '../model/request/BasePagingRequest.model';
import { BasePagingResponse } from '../model/response/BasePagingRequest.model';
import { BaseApiService } from './base-api.service';

declare global {
  interface Window {
    paypal: any;
  }
}

export interface CartStorage {
  accommodationId: number;
  fromDate: Date;
  toDate: Date;
  cartItems: CartItem[];
}

export interface CartItem {
  quantity: number;
  room: Room;
}

const URL = environment.apiUrl + PATH_V1;

@Injectable({
  providedIn: 'root',
})
export class BookingService {
  private cartStorage: CartStorage = {
    accommodationId: 0,
    fromDate: new Date(),
    toDate: new Date(),
    cartItems: [],
  };
  private cartSubject = new BehaviorSubject<CartItem[]>([]);

  constructor(private $baseApiService: BaseApiService, private $userService: UserService) {
    this.loadCartFromLocalStorage().subscribe({
      next: (res) => {
        this.cartStorage = res;
      },
    });
  }

  saveCartDate(fromDate: Date, toDate: Date) {
    this.cartStorage.fromDate = fromDate;
    this.cartStorage.toDate = toDate;
    this.saveCartToLocalStorage();
  }

  saveCartStorage(cartStorage: CartStorage) {
    this.cartStorage = cartStorage;
    this.saveCartToLocalStorage();
  }

  addToCart(item: CartItem) {
    const existedItem = this.cartStorage.cartItems.find((i) => i.room.roomId === item.room.roomId);
    if (existedItem) {
      existedItem.quantity += item.quantity;
    } else {
      this.cartStorage.cartItems.push(item);
    }
    this.cartStorage.accommodationId = item.room.accommodationId;
    this.saveCartToLocalStorage();
    this.cartSubject.next(this.cartStorage.cartItems);
  }

  updateQuantity(itemId: number, quantity: number) {
    const existingItem = this.cartStorage.cartItems.find((i) => i.room.roomId === itemId);
    if (existingItem) {
      if (quantity == 0) {
        this.removeFromCart(itemId);
      } else {
        existingItem.quantity = quantity;
      }
    }
    this.saveCartToLocalStorage();
    this.cartSubject.next(this.cartStorage.cartItems);
    console.log(this.cartStorage);
  }

  removeFromCart(itemId: number) {
    const itemIndex = this.cartStorage.cartItems.findIndex((i) => i.room.roomId === itemId);
    if (itemIndex > -1) {
      this.cartStorage.cartItems.splice(itemIndex, 1);
      this.saveCartToLocalStorage();
      this.cartSubject.next(this.cartStorage.cartItems);
    }
  }

  clearCart() {
    this.cartStorage.cartItems = [];
    this.saveCartToLocalStorage();
    this.cartSubject.next(this.cartStorage.cartItems);
  }

  loadCartFromLocalStorage(): Observable<any> {
    return this.$userService.getCurrentUser().pipe(
      switchMap((res) => {
        if (res) {
          const userId = res.id;
          const storedCart = Util.getLocal(`${CART_STORAGE}/${userId}`);
          if (storedCart) {
            this.cartStorage = storedCart;
            this.cartSubject.next(this.cartStorage.cartItems);
          }
        }
        return of(this.cartStorage);
      })
    );
  }

  private saveCartToLocalStorage() {
    this.$userService.getCurrentUser().subscribe({
      next: (res) => {
        if (res) {
          const userId = res.id;
          Util.setLocal(`${CART_STORAGE}/${userId}`, this.cartStorage, TIME_EXPIRED);
        }
      },
    });
  }

  createBookingInfo(bookingInfo: any): Promise<any> {
    const token = Util.getLocal(JWT_TOKEN_STORAGE);
    return fetch(`${URL}/booking/create`, {
      method: 'post',
      headers: {
        Accept: 'application/json, text/plain, */*',
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(bookingInfo),
    }).then((response) => response.json());
  }

  createPaymentOrder(bookingInfo: any): Promise<any> {
    const token = Util.getLocal(JWT_TOKEN_STORAGE);
    return fetch(`${URL}/booking/payment/create`, {
      method: 'post',
      headers: {
        Accept: 'application/json, text/plain, */*',
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(bookingInfo),
    }).then((response) => response.json());
  }

  capturePaymentOrder(data: any): Promise<any> {
    const token = Util.getLocal(JWT_TOKEN_STORAGE);
    return fetch(`${URL}/booking/payment/capture`, {
      method: 'post',
      headers: {
        Accept: 'application/json, text/plain, */*',
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        paymentId: data.paymentID,
      }),
    }).then((response) => response.json());
  }

  changeStatus(request: any) {
    return this.$baseApiService.putWithRequestBody(`${URL}/booking/change-status`, request);
  }

  getBookings(request: BasePagingRequest): Observable<BasePagingResponse> {
    return this.$baseApiService.postWithRequestBody(`${URL}/booking/get-bookings`, request);
  }
}
