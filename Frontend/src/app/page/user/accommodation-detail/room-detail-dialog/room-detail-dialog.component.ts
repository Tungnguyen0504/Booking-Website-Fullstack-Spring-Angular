import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Room } from 'src/app/model/Room.model';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { BookingService, CartItem } from 'src/app/service/booking.service';

interface DialogData {
  room: Room;
}

@Component({
  selector: 'app-room-detail-dialog',
  templateUrl: './room-detail-dialog.component.html',
  styleUrls: ['./room-detail-dialog.component.css'],
})
export class RoomDetailDialogComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private dialogRef: MatDialogRef<RoomDetailDialogComponent>,
    private router: Router,
    private $bookingService: BookingService,
    private $authenticationService: AuthenticationService
  ) {
    console.log(data);
  }

  ngOnInit(): void {}

  addToCart() {
    const cartItem: CartItem = {
      quantity: 1,
      room: this.data.room,
    };
    if (!this.$authenticationService.isLoggedIn()) {
      this.dialogRef.close();
      this.router.navigate(['/authentication/login']);
      return;
    }
    this.$bookingService.addToCart(cartItem);
  }
}
