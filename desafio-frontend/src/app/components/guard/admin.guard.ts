import { LocalService } from './../../services/authenticantion/local-service.service';
import { Injectable, inject } from '@angular/core';
import { CanActivateFn, Router, RouterStateSnapshot, ActivatedRouteSnapshot } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
class AdminService {

  constructor(private router: Router, private localService:LocalService) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
      let token = this.localService.decodeToken()
      if(this.localService.asSession() && token.role != "USER"){
        return true
      }
      this.router.navigateByUrl("/")
      return false
  }
}

export const AdminGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
  return inject(AdminService).canActivate(next, state);
}