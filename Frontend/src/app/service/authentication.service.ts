import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BASE_URL, PATH_AUTH, PATH_V1 } from '../constant/Abstract.constant';
import { Observable, catchError, switchMap, throwError } from 'rxjs';
import { AuthenticationResponse } from '../model/response/AuthenticationResponse.model';
import { RegisterRequest } from '../model/request/RegisterRequest.model';
import { LoginRequest } from '../model/request/LoginRequest.model';
import { User } from '../model/User.model';

const URL = BASE_URL + PATH_V1 + PATH_AUTH;

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient) {}

  register(registerRequest: RegisterRequest) {
    return this.httpClient.post(URL + '/register', registerRequest);
  }

  login(loginRequest: LoginRequest) {
    return this.httpClient.post(URL + '/login', loginRequest);
  }

  sendVerificationCode(email: string, verificationCode: string) {
    const requestSendCode = {
      email: email,
      verificationCode: verificationCode,
    };

    return this.httpClient
      .post(URL + '/send-verification-code', requestSendCode)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          return error.error;
        })
      );
  }

  getCurrentUser(): Observable<User> {
    return this.httpClient.get<User>(URL + '/get-current-user');
  }
}
