import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSelectionList } from '@angular/material/list';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { Accommodation } from 'src/app/model/Accommodation.model';
import { AccommodationType } from 'src/app/model/AccommodationType.model';
import { BasePagingRequest } from 'src/app/model/request/BasePagingRequest.model';
import { FilterRequest } from 'src/app/model/request/FilterRequest.model';
import { SortRequest } from 'src/app/model/request/SortRequest.model';
import { BasePagingResponse } from 'src/app/model/response/BasePagingRequest.model';
import { AccommodationTypeService } from 'src/app/service/accommodation-type.service';
import { AccommodationService } from 'src/app/service/accommodation.service';
import { CustomPaginatorIntl } from 'src/app/util/custom-paginator';

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
  @ViewChild('propertyRatingSelection')
  private propertyRatingSelection!: MatSelectionList;
  @ViewChild('reviewScoreSelection')
  private reviewScoreSelection!: MatSelectionList;

  listAccommodation?: Accommodation[] = [];
  filterRequest: FilterRequest[] = [];
  sortRequest: SortRequest[] = [];
  
  currentPage: number = 0;
  totalPage: number = 3;
  totalItem: number = 0;
  totalPages: number[] = [3, 5, 10, 25, 50];

  accTypeFilter: FilterObj[] = [];

  constructor(
    private $accommodationService: AccommodationService,
    private $accommodationTypeService: AccommodationTypeService
  ) {}

  ngOnInit(): void {
    this.getAccommodations();
    this.initApi();
  }

  initApi() {
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

  filterSelectionChange() {
    this.pushFilterRequest(
      'accommodationType.id',
      this.accTypeSelection.selectedOptions.selected,
      'OR'
    );
    this.pushFilterRequest(
      'star',
      this.propertyRatingSelection.selectedOptions.selected,
      'OR'
    );
    // console.log(this.reviewScoreSelection.selectedOptions.selected);
    this.getAccommodations();
  }

  pushFilterRequest(key: string, values: any[], operator: string) {
    const filterObj = this.filterRequest.find((req) => req.key === key);
    if (filterObj) {
      filterObj.values = values.map((seleted) => seleted.value);
    } else {
      this.filterRequest.push({
        key: key,
        values: values.map((seleted) => seleted.value),
        operator: operator,
      });
    }
  }
}
