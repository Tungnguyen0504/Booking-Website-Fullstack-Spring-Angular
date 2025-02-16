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

const URL = environment.apiUrl + PATH_V1 + PATH_USER;

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private $baseApiService: BaseApiService) {}

  getCurrentUser(): Observable<User | null> {
    return this.$baseApiService.getWithUrl(`${URL}/get-current-user`);
  }

  update(data: any) {
    const formData: FormData = new FormData();
    formData.append('id', data.id);
    formData.append('firstName', data.firstName);
    formData.append('lastName', data.lastName);
    formData.append('email', data.email);
    formData.append('phoneNumber', data.phoneNumber);
    formData.append('dateOfBirth', Util.formatDate(data.dateOfBirth, DATETIME_FORMAT3));
    formData.append('wardId', data.wardId);
    formData.append('specificAddress', data.specificAddress);
    formData.append('files', data.images[0]);
    return this.$baseApiService.putWithRequestBody(`${URL}/update`, formData);
  }

  resetPassword(request: any) {
    return this.$baseApiService.putWithRequestBody(`${URL}/reset-password`, request);
  }

  verifyEmail(email: string) {
    return this.$baseApiService.postWithRequestBody(`${URL}/verify-email`, { email: email });
  }
}
