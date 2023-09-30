import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { City } from '../model/City.model';
import { PATH_USER, PATH_V1 } from '../constant/Abstract.constant';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CityService {
  URL = environment.apiUrl + PATH_V1 + PATH_USER;

  constructor(private httpClient: HttpClient) {}

  getTopCity(range: number): Observable<City[]> {
    return this.httpClient.get<City[]>(`${this.URL}/get-top-city?range=${range}`);
  }
}
