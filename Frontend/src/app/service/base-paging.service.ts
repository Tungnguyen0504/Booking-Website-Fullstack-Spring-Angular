import { Injectable } from '@angular/core';
import { SortRequest } from '../model/request/SortRequest.model';
import { FilterRequest } from '../model/request/FilterRequest.model';

@Injectable({
  providedIn: 'root',
})
export class BasePagingService {
  constructor() {}

  pushFilterRequest(
    key: string,
    values: any[],
    operator: string,
    fieldType: string,
    filterRequest: FilterRequest[]
  ) {
    const filterObj = filterRequest.find((req) => req.key === key);
    if (filterObj) {
      filterObj.values = values;
    } else {
      filterRequest.push({
        key: key,
        values: values,
        operator: operator,
        fieldType: fieldType,
      });
    }
  }

  pushSortRequest(key: string, direction: string, sortRequest: SortRequest[]) {
    const sortObj = sortRequest.find((req) => req.key === key);
    if (sortObj) {
      sortObj.direction = direction;
    } else {
      sortRequest.push({
        key: key,
        direction: direction,
      });
    }
  }
}
