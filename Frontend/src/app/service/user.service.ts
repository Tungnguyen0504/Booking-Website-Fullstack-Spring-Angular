import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  PATH_USER,
  PATH_V1,
} from '../constant/Abstract.constant';
import { Observable } from 'rxjs';
import { User } from '../model/User.model';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';

const URL = environment.apiUrl + PATH_V1 + PATH_USER;

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private httpClient: HttpClient, private router: Router) {}

  getCurrentUser(): Observable<User> {
    console.log(new Date());
    return this.httpClient.get<User>(URL + '/get-current-user');
  }
}
