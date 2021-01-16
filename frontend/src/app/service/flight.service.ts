import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Flight } from '../model';

@Injectable({
  providedIn: 'root'
})
export class FlightService {
  constructor(private httpClient: HttpClient) {
  }

  get(id: number): Observable<Flight> {
    return this.httpClient.get<Flight>('/api/flight/' + id);
  }

  getAll(): Observable<Flight[]> {
    return this.httpClient.get<Flight[]>('/api/flight/');
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>('/api/flight/' + id);
  }

  create(flight: Flight): Observable<number> {
    return this.httpClient.post<number>('/api/flight', flight);
  }

  update(flight: Flight): Observable<void> {
    return this.httpClient.patch<void>('/api/flight', flight);
  }
}
