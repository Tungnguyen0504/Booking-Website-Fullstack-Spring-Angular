import { AfterViewInit, Component, ViewChild, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { BeforeSlideDetail } from 'lightgallery/lg-events';
import { Room } from 'src/app/model/Room.model';
import { DATETIME_FORMAT1, ROOM_GUEST_QTY_STORAGE } from 'src/app/constant/Abstract.constant';
import lgZoom from 'lightgallery/plugins/zoom';
import * as moment from 'moment';
import { MatDialog } from '@angular/material/dialog';
import { RoomDetailDialogComponent } from './room-detail-dialog/room-detail-dialog.component';
import { DialogData } from 'src/app/shared/admin/form-address/form-address-dialog/form-address-dialog.component';
import { BookingService, CartItem, CartStorage } from 'src/app/service/booking.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AlertService } from 'src/app/service/alert.service';
import { MatExpansionPanel } from '@angular/material/expansion';
import { Util } from 'src/app/util/util';

@Component({
  selector: 'app-accommodation-detail',
  templateUrl: './accommodation-detail.component.html',
  styleUrls: ['./accommodation-detail.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class AccommodationDetailComponent implements AfterViewInit {
  @ViewChild('expansionPanel') expansionPanel: MatExpansionPanel = {} as MatExpansionPanel;
  formUpdateQty: FormGroup = {} as FormGroup;

  accommodation?: Accommodation;
  listRoomInAccommodation?: Room[];
  settings = {
    counter: false,
    plugins: [lgZoom],
  };

  constructor(
    private $route: ActivatedRoute,
    private $router: Router,
    private $dialog: MatDialog,
    private $formBuilder: FormBuilder,
    private $alertService: AlertService,
    private $accommodationService: AccommodationService,
    private $bookingService: BookingService,
    private $authenticationService: AuthenticationService
  ) {
    this.buildForm();
  }

  ngOnInit(): void {
    const accommodationId: number = Number.parseInt(
      <string>this.$route.snapshot.paramMap.get('accommodationId')
    );
    this.initApi(accommodationId);
  }

  ngAfterViewInit(): void {
    const data = Util.getLocal(ROOM_GUEST_QTY_STORAGE);
    if (Object.keys(data).length !== 0) {
      setTimeout(() => {
        this.formUpdateQty.patchValue({
          roomQty: data.roomQty,
          guestQty: data.guestQty,
        });
      });
    }
  }

  onBeforeSlide = (detail: BeforeSlideDetail): void => {
    const { index, prevIndex } = detail;
    console.log(index, prevIndex);
  };

  onRoomQtyEmitter(data: any) {
    this.formUpdateQty.get('roomQty')?.setValue(data);
  }

  onGuestQtyEmitter(data: any) {
    this.formUpdateQty.get('guestQty')?.setValue(data);
  }

  initApi(accommodationId: number) {
    this.$accommodationService.getById(accommodationId).subscribe({
      next: (response: any) => {
        this.accommodation = response;
        if (this.accommodation) {
          this.accommodation.createdAt = moment(response.createdAt, DATETIME_FORMAT1).toDate();
        }
      },
    });
  }

  buildForm() {
    this.formUpdateQty = this.$formBuilder.group({
      roomQty: new FormControl(0, Validators.min(1)),
      guestQty: new FormControl(0, [Validators.min(1), Validators.max(10)]),
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
    if (!this.preCheck()) {
      return;
    }
    const room = this.accommodation?.rooms?.find((room) => room.roomId == id);
    const cartItem: CartItem = {
      quantity: this.formUpdateQty.get('roomQty')?.value,
      room: room!,
    };
    this.$bookingService.addToCart(cartItem);
    this.$alertService.success('Thêm thành công');
  }

  updateRoomGuest() {
    if (!this.preCheck()) {
      return;
    }
    Util.setLocal(ROOM_GUEST_QTY_STORAGE, this.formUpdateQty.value);
    this.expansionPanel.close();
    this.$alertService.success('Cập nhật thành công');
  }

  preCheck(): boolean {
    if (!this.$authenticationService.isLoggedIn()) {
      this.$alertService.warning('Bạn cần đăng nhập trước');
      return false;
    }
    if (!this.formUpdateQty.valid) {
      this.$alertService.warning('Số lượng khách và phòng không hợp lệ');
      return false;
    }
    return true;
  }
}
