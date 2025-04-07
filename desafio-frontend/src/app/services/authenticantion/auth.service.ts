import { LocalService } from './local-service.service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable, of,BehaviorSubject } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private loggedInSubject = new BehaviorSubject<boolean>(false);
  private user = new BehaviorSubject<any>(null);


  constructor( private httpClient: HttpClient, private router:Router,
     private localStore: LocalService) { 
    const loggedIn = !!localStorage.getItem('session');
    this.user.next(localStore.decodeToken())
    this.loggedInSubject.next(loggedIn);
  }
  urlApi = environment.apiUrl;
  login(login: FormGroup): Observable<token> {
    this.loggedInSubject.next(true);
    return this.httpClient.post<token>(`${this.urlApi}/auth/login`, login.value);
  }

  createUser(login: FormGroup): Observable<any> {
    return this.httpClient.post<Response>(`${this.urlApi}/register`, login.value);
  }

  get isLoggedIn$() {
    return this.loggedInSubject.asObservable();
  }

  get user$() {
    return this.user.asObservable();
  }

  logout(){
    this.localStore.clearData()
    this.loggedInSubject.next(false);
    this.router.navigateByUrl("/login")
  }

}
interface token {
  token:string
}