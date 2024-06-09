import { Injectable } from '@angular/core';
import { PATH_V1 } from '../constant/Abstract.constant';
import { environment } from 'src/environments/environment';
import { BaseApiService } from './base-api.service';

@Injectable({
  providedIn: 'root',
})
export class ReviewService {
  URL = environment.apiUrl + PATH_V1 + '/review';

  constructor(private $baseApiService: BaseApiService) {}

  create(requestBody: any) {
    return this.$baseApiService.postWithRequestBody(`${this.URL}/create`, requestBody);
  }

  getByBookingId(bookingId: number) {
    return this.$baseApiService.getWithUrl(`${this.URL}/get-by-booking-id/${bookingId}`);
  }
}
