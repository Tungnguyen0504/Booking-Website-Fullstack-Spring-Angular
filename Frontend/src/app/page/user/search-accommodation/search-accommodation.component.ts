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
  filterRequest: FilterRequest[] = [Util.filterActive()];
  sortRequest: SortRequest[] = [];

  currentPage: number = 0;
  totalPage: number = 3;
  totalItem: number = 0;
  totalPages: number[] = [3, 5, 10, 25, 50];

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
    const request: BasePagingRequest = {
      filterRequest: this.filterRequest,
      sortRequest: this.sortRequest,
      currentPage: this.currentPage,
      totalPage: this.totalPage,
    };
    this.$accommodationService.getAccommodations(request).subscribe({
      next: (response: BasePagingResponse) => {
        if (response) {
          this.listAccommodation = response.data;
          this.totalItem = response.totalItem;
          this.currentPage = response.currentPage;
          this.totalPage = response.totalPage;
        }
      },
    });
  }

  onPageChange($event: any) {
    this.currentPage = $event.pageIndex;
    this.totalPage = $event.pageSize;
    this.getAccommodations();
  }

  filterSelectionChange() {
    this.$basePagingService.pushFilterRequest(
      'accommodationType.id',
      this.accTypeSelection.selectedOptions.selected.map((seleted) => seleted.value),
      'OR',
      'INTEGER',
      this.filterRequest
    );
    this.$basePagingService.pushFilterRequest(
      'star',
      this.propertyRatingSelection.selectedOptions.selected.map((seleted) => seleted.value),
      'OR',
      'INTEGER',
      this.filterRequest
    );
    // console.log(this.reviewScoreSelection.selectedOptions.selected);
    this.getAccommodations();
  }

  sortChanged($event: any) {
    console.log($event);
    var key = '';
    var direction = '';
    if ($event.value === 'option1') {
      
    }
    // if ($event.value === '') {
    //   this.$basePagingService.pushSortRequest(
    //     sort.active,
    //     sort.direction.toUpperCase(),
    //     this.sortRequest
    //   );
    // } else {
    //   this.sortRequest = [];
    // }
    // this.getAccommodations();
  }

  getMinRoomPrice(accId: number) {
    const roomArr = this.listAccommodation?.find((acc) => acc.accommodationId === accId)?.rooms;
    return roomArr?.map((r) => r.price).sort((r1, r2) => r1 - r2)[0];
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
        this.filterRequest
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
