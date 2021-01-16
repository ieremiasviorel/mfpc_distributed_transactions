import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Reservation } from '../model';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  constructor(private httpClient: HttpClient) {
  }

  get(id: number): Observable<Reservation> {
    return this.httpClient.get<Reservation>('/api/reservation/' + id);
  }

  getAll(): Observable<Reservation[]> {
    return this.httpClient.get<Reservation[]>('/api/reservation/');
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>('/api/reservation/' + id);
  }

  create(reservation: Reservation): Observable<number> {
    return this.httpClient.post<number>('/api/reservation', reservation);
  }

  update(reservation: Reservation): Observable<void> {
    return this.httpClient.patch<void>('/api/reservation', reservation);
  }
}
