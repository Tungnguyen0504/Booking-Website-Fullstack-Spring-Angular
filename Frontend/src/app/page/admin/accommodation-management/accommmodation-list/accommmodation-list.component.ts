import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { BasePagingRequest } from 'src/app/model/request/BasePagingRequest.model';
import { FilterRequest } from 'src/app/model/request/FilterRequest.model';
import { SortRequest } from 'src/app/model/request/SortRequest.model';
import { BasePagingResponse } from 'src/app/model/response/BasePagingRequest.model';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { Util } from 'src/app/util/util';

@Component({
  selector: 'app-accommmodation-list',
  templateUrl: './accommmodation-list.component.html',
  styleUrls: ['./accommmodation-list.component.css'],
})
export class AccommmodationListComponent implements AfterViewInit {
  @ViewChild(MatPaginator) paginator: MatPaginator = {} as MatPaginator;
  @ViewChild(MatSort) sort: MatSort = {} as MatSort;

  displayedColumns: string[] = ['accommodationName', 'phone', 'email', 'fullAddress'];
  dataSource: MatTableDataSource<any> = new MatTableDataSource();

  listAccommodation: Accommodation[] = [];
  filterRequest: FilterRequest[] = [Util.filterActive()];
  sortRequest: SortRequest[] = [];

  currentPage: number = 0;
  totalPage: number = 3;
  totalItem: number = 0;
  totalPages: number[] = [3, 5, 10, 25, 50];

  constructor(private $accommodationService: AccommodationService) {
    this.getAccommodations();
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

  getAccommodations() {
    const request: BasePagingRequest = {
      filterRequest: this.filterRequest,
      sortRequest: this.sortRequest,
      currentPage: this.currentPage,
      totalPage: this.totalPage,
    };
    this.$accommodationService.getAccommodations(request).subscribe({
      next: (response: BasePagingResponse) => {
        this.listAccommodation = response.data;
        this.dataSource = new MatTableDataSource(this.listAccommodation);
        this.totalItem = response.totalItem;
        this.currentPage = response.currentPage;
        this.totalPage = response.totalPage;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  onPageChange($event: any) {
    this.currentPage = $event.pageIndex;
    this.totalPage = $event.pageSize;
    this.getAccommodations();
  }
}
