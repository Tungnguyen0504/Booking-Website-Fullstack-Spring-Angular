import { Component, OnInit } from '@angular/core';
import { BookingService, CartStorage } from 'src/app/service/booking.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  cartStorage!: CartStorage;

  constructor(private $bookingService: BookingService) {}

  ngOnInit(): void {
    setTimeout(() => {
      this.cartStorage = this.$bookingService.getCart();
      console.log(this.cartStorage);
    }, 500);
  }
}
