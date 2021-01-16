import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Airport } from '../model';

@Injectable({
  providedIn: 'root'
})
export class AirportService {
  constructor(private httpClient: HttpClient) {
  }

  get(id: number): Observable<Airport> {
    return this.httpClient.get<Airport>('/api/airport/' + id);
  }

  getAll(): Observable<Airport[]> {
    return this.httpClient.get<Airport[]>('/api/airport/');
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>('/api/airport/' + id);
  }

  create(airport: Airport): Observable<number> {
    return this.httpClient.post<number>('/api/airport', airport);
  }

  update(airport: Airport): Observable<void> {
    return this.httpClient.patch<void>('/api/airport', airport);
  }
}
