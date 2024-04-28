import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { BaseApiService } from 'src/app/service/base-api.service';
import { BookingService, CartStorage } from 'src/app/service/booking.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  cartStorage?: CartStorage;

  constructor(
    public $baseApiService: BaseApiService,
    public $authService: AuthenticationService,
    private $bookingService: BookingService
  ) {}

  ngOnInit(): void {
    this.loadCart();
  }

  openCart() {
    this.loadCart();
  }

  loadCart() {
    this.$bookingService.loadCartFromLocalStorage().subscribe({
      next: (res) => {
        this.cartStorage = res;
      },
    });
  }
}
