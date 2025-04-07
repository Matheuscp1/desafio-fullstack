import { LocalService } from './../../services/authenticantion/local-service.service';
import { inject, Injectable } from '@angular/core';
import { CanActivateFn, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
class AuthService {

  constructor(private router: Router, private localService:LocalService) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
      if(this.localService.asSession()){
        return true
      }
      console.log(this.localService.asSession())
      this.router.navigateByUrl("/login")
      return false
  }
}

export const AuthGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
  return inject(AuthService).canActivate(next, state);
}