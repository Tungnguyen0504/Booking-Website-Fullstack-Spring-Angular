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
    private $alertService: AlertService,
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
      this.httpClient.post(URL + '/logout', jwt).subscribe({
        next: () => {
          Util.removeLocal(JWT_TOKEN_STORAGE);
        },
        error: (error) => {
          this.$alertService.error(error.error.message);
          return;
        },
      });
    }
    this.$alertService.success('Đăng xuất thành công');
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return Util.getLocal(JWT_TOKEN_STORAGE) ? true : false;
  }

  isAuthenticated(): Observable<boolean> {
    return this.$userService.getCurrentUser().pipe(
      switchMap((res) => {
        if (res) {
          return of(true);
        }
        return of(false);
      }),
      catchError((error) => {
        console.log(error.error);
        return of(false);
      })
    );
  }
}
