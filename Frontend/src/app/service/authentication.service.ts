import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import {
  BASE_URL,
  JWT_TOKEN,
  PATH_AUTH,
  PATH_V1,
} from '../constant/Abstract.constant';
import { Observable, catchError, switchMap, throwError } from 'rxjs';
import { AuthenticationResponse } from '../model/response/AuthenticationResponse.model';
import { RegisterRequest } from '../model/request/RegisterRequest.model';
import { LoginRequest } from '../model/request/LoginRequest.model';
import { User } from '../model/User.model';
import { Route, Router } from '@angular/router';

const URL = BASE_URL + PATH_V1 + PATH_AUTH;

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private httpClient: HttpClient, private router: Router) {}

  register(registerRequest: RegisterRequest) {
    return this.httpClient.post(URL + '/register', registerRequest);
  }

  login(loginRequest: LoginRequest): Observable<any> {
    return this.httpClient.post(URL + '/login', loginRequest);
  }

  logout() {
    const jwt = localStorage.getItem(JWT_TOKEN);
    if (jwt) {
      this.httpClient.post(URL + '/logout', jwt).subscribe(() => {
        localStorage.removeItem(JWT_TOKEN);
      });
    }
    this.router.navigate(['/login']);
  }

  verification(email: string, password: string): Observable<any> {
    const verificationRequest = {
      email: email,
      password: password,
    };
    return this.httpClient.post(URL + '/verification', verificationRequest);
  }

  getCurrentUser(): Observable<User> {
    return this.httpClient.get<User>(URL + '/get-current-user');
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() ? true : false;
  }

  getJwtToken() {
    return localStorage.getItem(JWT_TOKEN);
  }
}
