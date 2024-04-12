import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PATH_USER, PATH_V1 } from '../constant/Abstract.constant';
import { Observable, catchError, of, switchMap } from 'rxjs';
import { User } from '../model/User.model';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from './authentication.service';
import { AlertService } from './alert.service';

const URL = environment.apiUrl + PATH_V1 + PATH_USER;

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(
    private httpClient: HttpClient,
    private router: Router,
    // private $authService: AuthenticationService
  ) {}

  getCurrentUser(): Observable<User | null> {
    // if (!this.$authService.isLoggedIn()) {
    //   this.router.navigate(['/logout']);
    //   return of(null);
    // }
    return this.httpClient.get<User>(URL + '/get-current-user').pipe(
      switchMap((res) => {
        return of(res);
      }),
      catchError((error) => {
        console.log(error);
        this.router.navigate(['/logout']);
        return of(null);
      })
    );
  }
}
