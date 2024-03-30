import { FilterRequest } from './FilterRequest.model';
import { SortRequest } from './SortRequest.model';

export interface BasePagingRequest {
  filterRequest: FilterRequest[];
  sortRequest: SortRequest[];
  currentPage: number;
  totalPage: number;
}
