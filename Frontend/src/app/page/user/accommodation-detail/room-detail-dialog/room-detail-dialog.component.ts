import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Room } from 'src/app/model/Room.model';
import { AlertService } from 'src/app/service/alert.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { BookingService, CartItem } from 'src/app/service/booking.service';

interface FormAddressDialogData {
  room: Room;
}

@Component({
  selector: 'app-room-detail-dialog',
  templateUrl: './room-detail-dialog.component.html',
  styleUrls: ['./room-detail-dialog.component.css'],
})
export class RoomDetailDialogComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: FormAddressDialogData,
    private dialogRef: MatDialogRef<RoomDetailDialogComponent>,
    private router: Router,
    private $alertService: AlertService,
    private $bookingService: BookingService,
    private $authenticationService: AuthenticationService
  ) {
  }

  ngOnInit(): void {}

  addToCart() {
    const cartItem: CartItem = {
      quantity: 1,
      room: this.data.room,
    };
    this.$authenticationService.isAuthenticated().subscribe({
      next: (res: boolean) => {
        if (res) {
          this.$bookingService.addToCart(cartItem);
        } else {
          this.$alertService.warning('Bạn cần đăng nhập trước');
        }
      },
    });
  }
}
