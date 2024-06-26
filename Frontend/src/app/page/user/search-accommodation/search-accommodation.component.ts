import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSelectionList } from '@angular/material/list';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { BOX_SEARCH_STORAGE } from 'src/app/constant/Abstract.constant';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { AccommodationType } from 'src/app/model/AccommodationType.model';
import { BasePagingRequest } from 'src/app/model/request/BasePagingRequest.model';
import { FilterRequest } from 'src/app/model/request/FilterRequest.model';
import { SortRequest } from 'src/app/model/request/SortRequest.model';
import { BasePagingResponse } from 'src/app/model/response/BasePagingRequest.model';
import { AccommodationTypeService } from 'src/app/service/accommodation-type.service';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { BasePagingService } from 'src/app/service/base-paging.service';
import { CustomPaginatorIntl } from 'src/app/util/custom-paginator';
import { Util } from 'src/app/util/util';

interface FilterObj {
  text: string;
  value: number;
}

@Component({
  selector: 'app-search-accommodation',
  templateUrl: './search-accommodation.component.html',
  styleUrls: ['./search-accommodation.component.css'],
  providers: [{ provide: MatPaginatorIntl, useClass: CustomPaginatorIntl }],
})
export class SearchAccommodationComponent implements OnInit {
  @ViewChild('accTypeSelection') private accTypeSelection!: MatSelectionList;
  @ViewChild('propertyRatingSelection') private propertyRatingSelection!: MatSelectionList;
  @ViewChild('reviewScoreSelection') private reviewScoreSelection!: MatSelectionList;

  listAccommodation: Accommodation[] = [];

  searchRequest: any = {
    filterRequest: [Util.filterActive()],
    sortRequest: [],
    customSortOption: '',
    currentPage: 0,
    totalPage: 3,
  };

  totalItem: number = 0;
  pageOptions: number[] = [3, 5, 10, 25, 50];

  accTypeFilter: FilterObj[] = [];

  constructor(
    private $basePagingService: BasePagingService,
    private $accommodationService: AccommodationService,
    private $accommodationTypeService: AccommodationTypeService
  ) {}

  ngOnInit(): void {
    this.initData();
    this.getAccommodations();
  }

  getAccommodations() {
    this.$accommodationService.getAccommodations(this.searchRequest).subscribe({
      next: (response: BasePagingResponse) => {
        if (response) {
          this.listAccommodation = response.data;
          this.totalItem = response.totalItem;
          this.searchRequest.currentPage = response.currentPage;
          this.searchRequest.totalPage = response.totalPage;
        }
      },
    });
  }

  onPageChange($event: any) {
    this.searchRequest.currentPage = $event.pageIndex;
    this.searchRequest.totalPage = $event.pageSize;
    this.getAccommodations();
  }

  filterSelectionChange() {
    this.$basePagingService.pushFilterRequest(
      'accommodationType.id',
      this.accTypeSelection.selectedOptions.selected.map((seleted) => seleted.value),
      'OR',
      'INTEGER',
      this.searchRequest.filterRequest
    );
    this.$basePagingService.pushFilterRequest(
      'star',
      this.propertyRatingSelection.selectedOptions.selected.map((seleted) => seleted.value),
      'OR',
      'INTEGER',
      this.searchRequest.filterRequest
    );
    // console.log(this.reviewScoreSelection.selectedOptions.selected);
    this.getAccommodations();
  }

  sortChanged($event: any) {
    this.searchRequest.customSortOption = $event.value;
    console.log(this.searchRequest);
    this.getAccommodations();
  }

  getMinRoomPrice(accId: number) {
    const roomArr = this.listAccommodation?.find((acc) => acc.accommodationId === accId)?.rooms;
    const roomMinPrice = roomArr?.sort(
      (r1, r2) =>
        (r1.price * (100 - r1.discountPercent)) / 100 -
        (r2.price * (100 - r2.discountPercent)) / 100
    )[0];
    if (roomMinPrice) {
      return {
        price: roomMinPrice.price,
        priceDiscount: (roomMinPrice.price * (100 - roomMinPrice.discountPercent)) / 100,
      };
    }
    return null;
  }

  isRoomAvailable(accId: number) {
    const roomArr = this.listAccommodation?.find((acc) => acc.accommodationId === accId)?.rooms;
    return Array.isArray(roomArr) && roomArr.length > 0;
  }

  initData() {
    const boxSearchStorage = Util.getLocal(BOX_SEARCH_STORAGE);
    if (boxSearchStorage) {
      this.$basePagingService.pushFilterRequest(
        'accommodationName',
        [boxSearchStorage.keySearch],
        'LIKE',
        'STRING',
        this.searchRequest.filterRequest
      );
    }
    this.$accommodationTypeService.getAllAccommodationType().subscribe({
      next: (res: AccommodationType[]) => {
        this.accTypeFilter = res.map((accType: AccommodationType) => {
          return {
            text: accType.accommodationTypeName,
            value: accType.accommodationTypeId,
          };
        });
      },
    });
  }
}
