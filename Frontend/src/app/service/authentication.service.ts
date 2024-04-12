import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JWT_TOKEN_STORAGE, PATH_AUTH, PATH_V1 } from '../constant/Abstract.constant';
import { Observable, catchError, of, switchMap } from 'rxjs';
import { RegisterRequest } from '../model/request/RegisterRequest.model';
import { LoginRequest } from '../model/request/LoginRequest.model';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { UserService } from './user.service';
import { AlertService } from './alert.service';
import { Util } from '../util/util';

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
    const jwt = Util.getLocal(JWT_TOKEN_STORAGE);
    if (jwt) {
      this.httpClient.post(URL + '/logout', jwt.token).subscribe(() => {
        localStorage.removeItem(JWT_TOKEN_STORAGE);
      });
    }
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return Util.getLocal(JWT_TOKEN_STORAGE) ? true : false;
  }

  isAuthenticated(): Observable<boolean> {
    return this.$userService.getCurrentUser().pipe(
      switchMap(() => {
        return of(true);
      }),
      catchError((error) => {
        console.log(error.error);
        return of(false);
      })
    );
  }
}
