import { Router } from '@angular/router';
import { LocalService } from './../../services/authenticantion/local-service.service';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: LocalService, private router: Router) {}
  readonly SESSION = 'session';
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.authService.getData(this.SESSION);
    if (token) {
      const cloned = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log('Requisição clonada com token:', cloned)
      return next.handle(cloned);
    } else {
      console.log('Token não encontrado. Requisição sem token.');
    }

    return next.handle(req);
  }
}
