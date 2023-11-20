import { Injectable } from '@angular/core';
import { PATH_V1 } from '../constant/Abstract.constant';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SpecialAround } from '../model/SpecialAround.model';

@Injectable({
  providedIn: 'root',
})
export class SpecialAroundService {
  URL = environment.apiUrl + PATH_V1 + '/special-around';

  constructor(private httpClient: HttpClient) {}

  getAllSpecialAround(): Observable<SpecialAround[]> {
    return this.httpClient.get<SpecialAround[]>(
      `${this.URL}/get-all-special-around`
    );
  }
}
