import { Injectable } from '@angular/core';
import { Room } from '../model/Room.model';
import { CART_STORAGE } from '../constant/Abstract.constant';
import { BehaviorSubject } from 'rxjs';
import { UserService } from './user.service';
import { AuthenticationService } from './authentication.service';
import { Router } from '@angular/router';

export interface CartStorage {
  startDate: Date;
  endDate: Date;
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
    startDate: new Date(),
    endDate: new Date(),
    cartItems: [],
  };
  private cartSubject = new BehaviorSubject<CartItem[]>([]);

  constructor(
    private $userService: UserService,
    private $authenticationService: AuthenticationService
  ) {
    this.loadCartFromLocalStorage();
  }

  getCart() {
    return this.cartStorage;
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

  private loadCartFromLocalStorage() {
    if (this.$authenticationService.isLoggedIn()) {
      this.$userService.getCurrentUser().subscribe({
        next: (res) => {
          const userId = res.id;
          const storedCart = localStorage.getItem(`${CART_STORAGE}/${userId}`);
          if (storedCart) {
            this.cartStorage = JSON.parse(storedCart);
            this.cartSubject.next(this.cartStorage.cartItems);
          }
        },
      });
    }
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
