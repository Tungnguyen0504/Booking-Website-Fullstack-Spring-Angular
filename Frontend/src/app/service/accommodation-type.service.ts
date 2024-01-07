import { Injectable } from '@angular/core';
import { PATH_V1 } from '../constant/Abstract.constant';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AccommodationType } from '../model/AccommodationType.model';

@Injectable({
  providedIn: 'root',
})
export class AccommodationTypeService {
  URL = environment.apiUrl + PATH_V1 + '/accommodation-type';

  constructor(private httpClient: HttpClient) {}

  getAllAccommodationType(): Observable<AccommodationType[]> {
    return this.httpClient.get<AccommodationType[]>(
      `${this.URL}/get-all-accommodation-type`
    );
  }
}
