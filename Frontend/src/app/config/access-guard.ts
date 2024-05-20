import { Injectable, inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable, of, switchMap } from 'rxjs';
import { AuthenticationService } from '../service/authentication.service';

@Injectable({
  providedIn: 'root',
})
export class AccessGuard {
  constructor(private route: Router, private $authService: AuthenticationService) {}

  isAuthenticated(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.$authService.isAuthenticated().pipe(
      switchMap((res) => {
        if (!res) {
          this.route.navigateByUrl('error');
          return of(false);
        }
        return of(true);
      })
    );
  }
}

export const AuthenGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): Observable<boolean> => {
  return inject(AccessGuard).isAuthenticated(route, state);
};
