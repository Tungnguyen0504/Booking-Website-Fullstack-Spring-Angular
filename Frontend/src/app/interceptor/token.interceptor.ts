import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../service/authentication.service';
import { JWT_TOKEN_STORAGE } from '../constant/Abstract.constant';
import { Util } from '../util/util';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const data = Util.getLocal(JWT_TOKEN_STORAGE);
    if (data) {
      req = this._addToken(req, data!);
    }

    return next.handle(req);
  }

  private _addToken(request: HttpRequest<any>, token: string) {
    return request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
}
