import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JWT_EXPIRED, JWT_TOKEN, PATH_AUTH, PATH_V1 } from '../constant/Abstract.constant';
import { Observable, catchError, of, switchMap } from 'rxjs';
import { RegisterRequest } from '../model/request/RegisterRequest.model';
import { LoginRequest } from '../model/request/LoginRequest.model';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { UserService } from './user.service';
import { AlertService } from './alert.service';

const URL = environment.apiUrl + PATH_V1 + PATH_AUTH;

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(
    private httpClient: HttpClient,
    private router: Router,
    private $userService: UserService
  ) {}

  verifyRegister(registerRequest: RegisterRequest): Observable<any> {
    return this.httpClient.post(URL + '/verification/register', registerRequest);
  }

  register(registerRequest: RegisterRequest) {
    return this.httpClient.post(URL + '/register', registerRequest);
  }

  verifyLogin(loginRequest: LoginRequest): Observable<any> {
    return this.httpClient.post(URL + '/verification/login', loginRequest);
  }

  login(loginRequest: LoginRequest): Observable<any> {
    return this.httpClient.post(URL + '/login', loginRequest);
  }

  logout() {
    const jwt = this.getJwtToken();
    if (jwt) {
      this.httpClient.post(URL + '/logout', jwt).subscribe(() => {
        localStorage.removeItem(JWT_TOKEN);
      });
    }
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() ? true : false;
  }

  isAuthenticated(): Observable<boolean> {
    if (this.getJwtToken()) {
      this.$userService.getCurrentUser().pipe(
        switchMap(() => {
          return of(true);
        }),
        catchError((error) => {
          console.log(error.error);
          return of(false);
        })
      );
    }
    return of(false);
  }

  setJwtToken(token: string) {
    if (token) {
      const expirationMS = JWT_EXPIRED;
      const item = {
        token: token,
        expiration: Date.now() + expirationMS,
      };
      localStorage.setItem(JWT_TOKEN, JSON.stringify(item));
    }
  }

  getJwtToken() {
    const item = localStorage.getItem(JWT_TOKEN);
    if (item) {
      const parsedItem = JSON.parse(item);
      if (parsedItem.expiration && parsedItem.expiration < Date.now()) {
        localStorage.removeItem(JWT_TOKEN);
        return '';
      }
      return parsedItem.token;
    }
    return '';
  }
}
