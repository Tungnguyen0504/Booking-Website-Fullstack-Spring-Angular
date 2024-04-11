import { Injectable } from '@angular/core';
import { SortRequest } from '../model/request/SortRequest.model';

@Injectable({
  providedIn: 'root',
})
export class BasePagingService {
  constructor() {}

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
