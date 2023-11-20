import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JWT_TOKEN, PATH_AUTH, PATH_V1 } from '../constant/Abstract.constant';
import { Observable } from 'rxjs';
import { RegisterRequest } from '../model/request/RegisterRequest.model';
import { LoginRequest } from '../model/request/LoginRequest.model';
import { User } from '../model/User.model';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';

const URL = environment.apiUrl + PATH_V1 + PATH_AUTH;

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private httpClient: HttpClient, private router: Router) {}

  verifyRegister(registerRequest: RegisterRequest): Observable<any> {
    return this.httpClient.post(
      URL + '/verification/register',
      registerRequest
    );
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
    const jwt = localStorage.getItem(JWT_TOKEN);
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

  getJwtToken() {
    return localStorage.getItem(JWT_TOKEN);
  }
}
