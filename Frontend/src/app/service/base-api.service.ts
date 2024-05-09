import { Injectable } from '@angular/core';
import { Observable, catchError, finalize, map, of, switchMap } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { AlertService } from './alert.service';

@Injectable({
  providedIn: 'root',
})
export class BaseApiService {
  constructor(private $alertService: AlertService, private $httpClient: HttpClient) {}

  public getWithParams(url: string, params: HttpParams): Observable<any> {
    return this.$httpClient.get(url, { params }).pipe(
      switchMap((response) => this.handleResponse(response)),
      catchError((error) => this.handleError(error))
    );
  }

  public getWithUrl(url: string): Observable<any> {
    return this.$httpClient.get(url).pipe(
      switchMap((response) => this.handleResponse(response)),
      catchError((error) => this.handleError(error))
    );
  }

  public postWithRequestBody(url: string, requestBody: any): Observable<any> {
    return this.$httpClient.post(url, requestBody).pipe(
      switchMap((response) => this.handleResponse(response)),
      catchError((error) => this.handleError(error))
    );
  }

  private handleResponse(response: any): Observable<any> {
    if (response) {
      return of(response);
    }
    return of(null);
  }

  private handleError(error: any): Observable<any> {
    console.log(error);
    this.$alertService.error(error.error.message);
    return of(null);
  }
}
