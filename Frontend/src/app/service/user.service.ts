import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { JWT_TOKEN_STORAGE, PATH_USER, PATH_V1 } from '../constant/Abstract.constant';
import { Observable, catchError, of, switchMap } from 'rxjs';
import { User } from '../model/User.model';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { Util } from '../util/util';

const URL = environment.apiUrl + PATH_V1 + PATH_USER;

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(
    private httpClient: HttpClient,
    private router: Router,
  ) {}

  getCurrentUser(): Observable<User | null> {
    const data = Util.getLocal(JWT_TOKEN_STORAGE);
    if (data) {
      const params = new HttpParams().set('jwt', data);
      return this.httpClient.get<User>(`${URL}/get-current-user`, { params }).pipe(
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
    return of(null);
  }
}
