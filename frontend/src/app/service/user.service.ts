import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { User } from '../model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private httpClient: HttpClient) {
  }

  get(id: number): Observable<User> {
    return this.httpClient.get<User>('/api/user/' + id);
  }

  getAll(): Observable<User[]> {
    return this.httpClient.get<User[]>('/api/user/');
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>('/api/user/' + id);
  }

  create(user: User): Observable<number> {
    return this.httpClient.post<number>('/api/user', user);
  }

  update(user: User): Observable<void> {
    return this.httpClient.patch<void>('/api/user', user);
  }
}
