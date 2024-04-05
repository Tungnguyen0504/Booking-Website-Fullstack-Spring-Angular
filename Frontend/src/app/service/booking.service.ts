import { Injectable } from '@angular/core';
import { Room } from '../model/Room.model';
import { CART_STORAGE } from '../constant/Abstract.constant';
import { BehaviorSubject, Observable, of, switchMap } from 'rxjs';
import { UserService } from './user.service';
import { AuthenticationService } from './authentication.service';

export interface CartStorage {
  fromDate: Date;
  toDate: Date;
  cartItems: CartItem[];
}

export interface CartItem {
  quantity: number;
  room: Room;
}

@Injectable({
  providedIn: 'root',
})
export class BookingService {
  private cartStorage: CartStorage = {
    fromDate: new Date(),
    toDate: new Date(),
    cartItems: [],
  };
  private cartSubject = new BehaviorSubject<CartItem[]>([]);

  constructor(
    private $userService: UserService,
    private $authenticationService: AuthenticationService
  ) {
    this.loadCartFromLocalStorage().subscribe({
      next: (res) => {
        this.cartStorage = res;
      },
    });
  }

  getCart() {
    return this.cartStorage;
  }

  saveCartDate(fromDate: Date, toDate: Date) {
    this.cartStorage.fromDate = fromDate;
    this.cartStorage.toDate = toDate;
    this.saveCartToLocalStorage();
  }

  addToCart(item: CartItem) {
    const existingItem = this.cartStorage.cartItems.find(
      (i) => i.room.roomId === item.room.roomId
    );
    if (existingItem) {
      existingItem.quantity += item.quantity;
    } else {
      this.cartStorage.cartItems.push(item);
    }
    this.saveCartToLocalStorage();
    this.cartSubject.next(this.cartStorage.cartItems);
  }

  updateQuantity(itemId: number, quantity: number) {
    const existingItem = this.cartStorage.cartItems.find(
      (i) => i.room.roomId === itemId
    );
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
    const itemIndex = this.cartStorage.cartItems.findIndex(
      (i) => i.room.roomId === itemId
    );
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
    if (this.$authenticationService.isLoggedIn()) {
      return this.$userService.getCurrentUser().pipe(
        switchMap((res) => {
          const userId = res.id;
          const storedCart = localStorage.getItem(`${CART_STORAGE}/${userId}`);
          if (storedCart) {
            this.cartStorage = JSON.parse(storedCart);
            this.cartSubject.next(this.cartStorage.cartItems);
          }
          return of(this.cartStorage);
        })
      );
    }
    return of({});
  }

  private saveCartToLocalStorage() {
    if (this.$authenticationService.isLoggedIn()) {
      this.$userService.getCurrentUser().subscribe({
        next: (res) => {
          const userId = res.id;
          localStorage.setItem(
            `${CART_STORAGE}/${userId}`,
            JSON.stringify(this.cartStorage)
          );
        },
      });
    }
  }
}
