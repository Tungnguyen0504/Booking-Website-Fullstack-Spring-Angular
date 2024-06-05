import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, finalize } from 'rxjs';
import { LoaderService } from '../../service/loader.service';

@Injectable()
export class LoadingInterceptor implements HttpInterceptor {
  private totalRequests = 0;

  constructor(private $loaderService: LoaderService) {}

  private skip(request: HttpRequest<unknown>): boolean {
    return !!(request.url.match('api/v1/user/verify-email') && request.method === 'POST');
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (!this.skip(request)) {
      this.totalRequests++;
      this.$loaderService.setLoading(true);
      return next.handle(request).pipe(
        finalize(() => {
          this.totalRequests--;
          if (this.totalRequests == 0) {
            setTimeout(() => {
              this.$loaderService.setLoading(false);
            }, 200);
          }
        })
      );
    } else {
      return next.handle(request);
    }
  }
}
