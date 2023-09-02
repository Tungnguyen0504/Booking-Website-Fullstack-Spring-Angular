import { Injectable } from '@angular/core';
import { BASE_URL, PATH_USER, PATH_V1 } from '../constant/Abstract.constant';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HotelType } from '../model/HotelType.model';

@Injectable({
  providedIn: 'root'
})
export class HotelTypeService {
  URL = BASE_URL + PATH_V1 + PATH_USER;

  constructor(private httpClient: HttpClient) {}

  getAllHotelType(): Observable<HotelType[]> {
    return this.httpClient.get<HotelType[]>(`${this.URL}/get-all-hotel-type`);
  }
}
