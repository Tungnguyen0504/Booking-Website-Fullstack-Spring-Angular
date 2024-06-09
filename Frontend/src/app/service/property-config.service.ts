import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import {
  DATETIME_FORMAT3,
  JWT_TOKEN_STORAGE,
  PATH_USER,
  PATH_V1,
} from '../constant/Abstract.constant';
import { Observable, of } from 'rxjs';
import { User } from '../model/User.model';
import { environment } from 'src/environments/environment';
import { Util } from '../util/util';
import { BaseApiService } from './base-api.service';

const URL = environment.apiUrl + PATH_V1 + '/property-config';

@Injectable({
  providedIn: 'root',
})
export class PropertyConfigService {
  constructor(private $baseApiService: BaseApiService) {}

  getContents(property: string) {
    const params = new HttpParams().set('property', property);
    return this.$baseApiService.getWithParams(`${URL}/get-by-property`, params);
  }
}
