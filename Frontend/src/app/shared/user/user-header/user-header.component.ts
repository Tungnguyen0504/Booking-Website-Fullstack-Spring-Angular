import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDrawer } from '@angular/material/sidenav';
import { of, switchMap } from 'rxjs';
import { User } from 'src/app/model/User.model';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { BookingService, CartStorage } from 'src/app/service/booking.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.css'],
})
export class UserHeaderComponent implements OnInit {
  @Input() drawer!: MatDrawer;

  cartStorage?: CartStorage;

  user?: User;

  constructor(
    private $userService: UserService,
    private $authenticationService: AuthenticationService,
    private $bookingService: BookingService
  ) {}

  ngOnInit(): void {
    this.$userService.getCurrentUser().subscribe({
      next: (res: User | null) => {
        if (res) {
          this.user = res;
          console.log(this.user)
        }
      },
    });

    this.$bookingService.loadCartFromLocalStorage().subscribe({
      next: (res) => {
        this.cartStorage = res;
      },
    });
  }

  toggleCart() {
    this.drawer.toggle();
  }
}
