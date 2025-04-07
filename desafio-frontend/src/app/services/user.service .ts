import { User } from '../models/user.model';
import { PageResponse } from '../models/page.model';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

   getAllUsers(page:number = 0,size:number = 10): Observable<PageResponse<User>>{
    return this.http.get<PageResponse<User>>(`${this.apiUrl}/user?page=${page}&size=${size}`);
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/user/${id}`);
  }

  createtUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/user`, user);
  }

  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/user/${id}`, user);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/user/${id}`);
  }
  getContext(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/auth/context`);
  }
  updatePassowrd(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/user/password`, user);
  }
}