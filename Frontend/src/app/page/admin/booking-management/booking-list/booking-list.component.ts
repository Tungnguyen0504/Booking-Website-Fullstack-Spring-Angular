import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { BasePagingRequest } from 'src/app/model/request/BasePagingRequest.model';
import { FilterRequest } from 'src/app/model/request/FilterRequest.model';
import { SortRequest } from 'src/app/model/request/SortRequest.model';
import { BasePagingResponse } from 'src/app/model/response/BasePagingRequest.model';
import { BasePagingService } from 'src/app/service/base-paging.service';
import { BookingService } from 'src/app/service/booking.service';
import { ChangeStatusDialogComponent } from './change-status-dialog/change-status-dialog.component';

@Component({
  selector: 'app-booking-list',
  templateUrl: './booking-list.component.html',
  styleUrls: ['./booking-list.component.css'],
})
export class BookingListComponent implements AfterViewInit {
  @ViewChild(MatPaginator) paginator: MatPaginator = {} as MatPaginator;
  @ViewChild(MatSort) sort: MatSort = {} as MatSort;

  displayedColumns: string[] = [
    'firstName',
    'lastName',
    'email',
    'phoneNumber',
    'guestNumber',
    'note',
    'estCheckinTime',
    'paymentMethod',
    'totalAmount',
    'fromDate',
    'toDate',
    'status',
    'action',
  ];
  dataSource: MatTableDataSource<any> = new MatTableDataSource();

  filterRequest: FilterRequest[] = [];
  sortRequest: SortRequest[] = [];

  currentPage: number = 0;
  totalPage: number = 3;
  totalItem: number = 0;
  totalPages: number[] = [3, 5, 10, 25, 50];

  constructor(
    private dialog: MatDialog,
    private $bookingService: BookingService,
    private $basePagingService: BasePagingService
  ) {
    this.getBookings();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  getBookings() {
    const request: BasePagingRequest = {
      filterRequest: this.filterRequest,
      sortRequest: this.sortRequest,
      currentPage: this.currentPage,
      totalPage: this.totalPage,
    };
    this.$bookingService.getBookings(request).subscribe({
      next: (response: BasePagingResponse) => {
        if (response) {
          this.dataSource = new MatTableDataSource(response.data);
          this.totalItem = response.totalItem;
          this.currentPage = response.currentPage;
          this.totalPage = response.totalPage;
        }
      },
    });
  }

  sortData(sort: Sort) {
    if (sort.direction) {
      this.$basePagingService.pushSortRequest(
        sort.active,
        sort.direction.toUpperCase(),
        this.sortRequest
      );
    } else {
      this.sortRequest = [];
    }
    this.getBookings();
  }

  onPageChange($event: any) {
    this.currentPage = $event.pageIndex;
    this.totalPage = $event.pageSize;
    this.getBookings();
  }

  changeStatusPopup() {
    const dialogRef = this.dialog.open(ChangeStatusDialogComponent, {
      data: {},
      position: {
        top: '200px',
      },
      width: '576px',
      disableClose: true,
      autoFocus: false,
    });

    dialogRef.afterClosed().subscribe((result) => {
      // if (result && result.isComplete) {
      // }
      window.location.reload();
    });
  }
}
