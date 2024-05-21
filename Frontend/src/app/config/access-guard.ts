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
import { UserService } from '../service/user.service';

@Injectable({
  providedIn: 'root',
})
export class AccessGuard {
  constructor(
    private route: Router,
    private $authService: AuthenticationService,
    private $userService: UserService
  ) {}

  isAuthenticated(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.$authService.isAuthenticated().pipe(
      switchMap((res) => {
        if (res) {
          return of(true);
        }
        this.route.navigateByUrl('error');
        return of(false);
      })
    );
  }

  isAdmin(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.$userService.getCurrentUser().pipe(
      switchMap((res) => {
        if (res && res.role == 'ADMIN') {
          return of(true);
        }
        this.route.navigateByUrl('error');
        return of(false);
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

export const AdminGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): Observable<boolean> => {
  return inject(AccessGuard).isAdmin(route, state);
};
