import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { mapTo } from 'rxjs/operators';

import { Flight, Reservation, User } from '../../model';
import { NAVIGATION } from '../../model/constant';
import { createEmptyReservation } from '../../model/util';
import { FlightService } from '../../service/flight.service';
import { ReservationService } from '../../service/reservation.service';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-reservation-form',
  templateUrl: './reservation-form.component.html',
  styleUrls: [ './reservation-form.component.scss' ]
})
export class ReservationFormComponent implements OnInit {
  flights$!: Observable<Flight[]>;
  users$!: Observable<User[]>;
  reservation$!: Observable<Reservation>;
  isNew!: boolean;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private flightService: FlightService,
    private userService: UserService,
    private reservationService: ReservationService) {
  }

  ngOnInit(): void {
    this.flights$ = this.flightService.getAll();
    this.users$ = this.userService.getAll();
    const reservationId = this.route.snapshot.paramMap.get('id');
    if (reservationId) {
      this.isNew = false;
      this.reservation$ = this.reservationService.get(parseInt(reservationId));
    } else {
      this.isNew = true;
      this.reservation$ = of(createEmptyReservation());
    }
  }

  flightsEqual(flightA: Flight, flightB: Flight): boolean {
    return flightA.id === flightB.id;
  }

  usersEqual(userA: User, userB: User): boolean {
    return userA.id === userB.id;
  }

  async handleSubmit(reservation: Reservation): Promise<void> {
    await (this.isNew ? this.reservationService.create(reservation).pipe(mapTo(undefined)) : this.reservationService.update(reservation))
      .toPromise()
      .then(() => this.router.navigate([ NAVIGATION.RESERVATION, NAVIGATION.LIST ]));
  }

  async handleCancel(): Promise<void> {
    await this.router.navigate([ NAVIGATION.RESERVATION, NAVIGATION.LIST ]);
  }
}
