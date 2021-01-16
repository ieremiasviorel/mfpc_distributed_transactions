import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Reservation } from '../../model';
import { NAVIGATION } from '../../model/constant';
import { ReservationService } from '../../service/reservation.service';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: [ './reservation-list.component.scss' ],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class ReservationListComponent implements OnInit {
  reservations$!: Observable<Reservation[]>;
  expandedElement: Reservation | undefined;

  displayedColumns = [ 'id', 'flight', 'user' ];

  constructor(private router: Router, private reservationService: ReservationService) {
  }

  ngOnInit(): void {
    this.reservations$ = this.reservationService.getAll();
  }

  async handleEdit(reservation: Reservation): Promise<void> {
    await this.router.navigate([ NAVIGATION.RESERVATION, NAVIGATION.FORM, reservation.id ]);
  }

  async handleCreate(): Promise<void> {
    await this.router.navigate([ NAVIGATION.RESERVATION, NAVIGATION.FORM ]);
  }

  handleDelete(reservation: Reservation): void {
    this.reservations$ = this.reservationService.delete(reservation.id).pipe(mergeMap(() => this.reservationService.getAll()));
  }
}
