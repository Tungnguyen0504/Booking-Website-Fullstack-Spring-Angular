import { Component, Input, OnInit } from '@angular/core';
import { BookingService, CartStorage } from 'src/app/service/booking.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  @Input() cartStorage?: CartStorage;

  constructor(private $bookingService: BookingService) {}

  ngOnInit(): void {}

  removeRoom(roomId: number) {
    this.$bookingService.removeFromCart(roomId);
  }

  onQuantityEmitter(roomId: number, data: any) {
    this.$bookingService.updateQuantity(roomId, data);
  }
}
