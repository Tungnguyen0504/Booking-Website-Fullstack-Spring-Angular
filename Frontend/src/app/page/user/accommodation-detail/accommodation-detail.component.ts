import { Component, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { BeforeSlideDetail } from 'lightgallery/lg-events';
import { Room } from 'src/app/model/Room.model';
import { DATETIME_FORMAT1 } from 'src/app/constant/Abstract.constant';
import lgZoom from 'lightgallery/plugins/zoom';
import * as moment from 'moment';
import { MatDialog } from '@angular/material/dialog';
import { RoomDetailDialogComponent } from './room-detail-dialog/room-detail-dialog.component';
import { DialogData } from 'src/app/shared/admin/form-address/form-address-dialog/form-address-dialog.component';
import { BookingService, CartItem, CartStorage } from 'src/app/service/booking.service';
import { AuthenticationService } from 'src/app/service/authentication.service';

@Component({
  selector: 'app-accommodation-detail',
  templateUrl: './accommodation-detail.component.html',
  styleUrls: ['./accommodation-detail.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class AccommodationDetailComponent {
  accommodation?: Accommodation;
  listRoomInAccommodation?: Room[];
  settings = {
    counter: false,
    plugins: [lgZoom],
  };

  roomQuantity: number = 0;

  cartStorage?: CartStorage;

  constructor(
    private $route: ActivatedRoute,
    private $router: Router,
    private $accommodationService: AccommodationService,
    private $bookingService: BookingService,
    private $authenticationService: AuthenticationService,
    private $dialog: MatDialog
  ) {}

  ngOnInit(): void {
    const accommodationId: number = Number.parseInt(
      <string>this.$route.snapshot.paramMap.get('accommodationId')
    );
    this.initApi(accommodationId);
  }

  onBeforeSlide = (detail: BeforeSlideDetail): void => {
    const { index, prevIndex } = detail;
    console.log(index, prevIndex);
  };

  onRoomQtyEmitter(data: any) {
    this.roomQuantity = data;
  }

  onGuestQtyEmitter(data: any) {
    if (this.cartStorage) {
      this.cartStorage.guestNumber = data;
      this.$bookingService.saveCartStorage(this.cartStorage);
    }
  }

  initApi(accommodationId: number) {
    this.$bookingService.loadCartFromLocalStorage().subscribe({
      next: (res) => {
        this.cartStorage = res;
        console.log(this.cartStorage);
      },
    });

    this.$accommodationService.getById(accommodationId).subscribe({
      next: (response: any) => {
        this.accommodation = response;
        if (this.accommodation) {
          this.accommodation.createdAt = moment(response.createdAt, DATETIME_FORMAT1).toDate();
        }
        console.log(this.accommodation);
      },
    });
  }

  getPercentage(hour: number) {
    return `width: ${((24 - hour) / 24) * 100}%;`;
  }

  openRoomDetailDialog(room: Room) {
    const dialogRef = this.$dialog.open(RoomDetailDialogComponent, {
      data: {
        room: room,
      },
      autoFocus: false,
    });

    dialogRef.afterClosed().subscribe((result: DialogData) => {
      if (result && result.isCompleted) {
        // this.form.patchValue({
        //   wardId: result.wardId,
        //   address: result.address,
        //   specificAddress: result.specificAddress,
        // });
      }
    });
  }

  addToCart(id: number) {
    const room = this.accommodation?.rooms?.find((room) => room.roomId == id);
    const cartItem: CartItem = {
      quantity: 1,
      room: room!,
    };
    if (!this.$authenticationService.isLoggedIn()) {
      this.$router.navigate(['/login']);
      return;
    }
    this.$bookingService.addToCart(cartItem);
  }
}
