import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { JWT_TOKEN_STORAGE, PATH_USER, PATH_V1 } from '../constant/Abstract.constant';
import { Observable, of } from 'rxjs';
import { User } from '../model/User.model';
import { environment } from 'src/environments/environment';
import { Util } from '../util/util';
import { BaseApiService } from './base-api.service';

const URL = environment.apiUrl + PATH_V1 + PATH_USER;

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private httpClient: HttpClient, private $baseApiService: BaseApiService) {}

  getCurrentUser(): Observable<User | null> {
    const data = Util.getLocal(JWT_TOKEN_STORAGE);
    if (data) {
      const params = new HttpParams().set('jwt', data);
      return this.$baseApiService.getWithParams(`${URL}/get-current-user`, params);
    }
    return of(null);
  }
}
