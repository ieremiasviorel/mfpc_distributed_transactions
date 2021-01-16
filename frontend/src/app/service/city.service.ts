import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { City } from '../model';

@Injectable({
  providedIn: 'root'
})
export class CityService {
  constructor(private httpClient: HttpClient) {
  }

  getAll(): Observable<City[]> {
    return this.httpClient.get<City[]>('/api/city/');
  }
}
