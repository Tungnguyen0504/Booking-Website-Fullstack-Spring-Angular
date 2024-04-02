import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { BookingService, CartStorage } from 'src/app/service/booking.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  cartStorage?: CartStorage;

  constructor(private $bookingService: BookingService) {}

  ngOnInit(): void {
    this.$bookingService.loadCartFromLocalStorage().subscribe({
      next: (res) => {
        this.cartStorage = res;
        console.log(this.cartStorage);
      },
    });
  }

  removeRoom(roomId: number) {
    this.$bookingService.removeFromCart(roomId);
  }

  increase(roomId: number, quantity: number) {
    this.$bookingService.updateQuantity(roomId, ++quantity);
  }

  decrease(roomId: number, quantity: number) {
    this.$bookingService.updateQuantity(roomId, --quantity);
  }
}
