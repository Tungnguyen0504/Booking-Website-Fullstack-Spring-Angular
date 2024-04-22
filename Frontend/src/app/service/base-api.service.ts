import { Injectable } from '@angular/core';
import { Observable, catchError, finalize, map, of, switchMap } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { AlertService } from './alert.service';

@Injectable({
  providedIn: 'root',
})
export class BaseApiService {
  loading: boolean = false;

  constructor(private $alertService: AlertService, private $httpClient: HttpClient) {}

  isLoading() {
    // setTimeout(() => {
      return this.loading;
    // }, 200);
  }

  public get(url: string, params: HttpParams): Observable<any> {
    this.loading = true;
    return this.$httpClient.get(url, { params }).pipe(
      switchMap((response) => {
        if (response) {
          return of(response);
        }
        return of(null);
      }),
      catchError((error) => {
        this.$alertService.error(error.error.message);
        return of(null);
      }),
      finalize(() => {
        this.loading = false;
      })
    );
  }

  public post(url: string, requestBody: any): Observable<any> {
    this.loading = true;
    return this.$httpClient.post(url, requestBody).pipe(
      switchMap((response) => {
        if (response) {
          return of(response);
        }
        return of(null);
      }),
      catchError((error) => {
        this.$alertService.error(error.error.message);
        return of(null);
      }),
      finalize(() => {
        this.loading = false;
      })
    );
  }
}
