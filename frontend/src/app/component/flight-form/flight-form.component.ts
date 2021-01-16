import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { mapTo } from 'rxjs/operators';

import { Airport, Flight } from '../../model';
import { NAVIGATION } from '../../model/constant';
import { createEmptyFlight } from '../../model/util';
import { AirportService } from '../../service/airport.service';
import { FlightService } from '../../service/flight.service';

@Component({
  selector: 'app-flight-form',
  templateUrl: './flight-form.component.html',
  styleUrls: [ './flight-form.component.scss' ]
})
export class FlightFormComponent implements OnInit {
  airports$!: Observable<Airport[]>;
  flight$!: Observable<Flight>;
  isNew!: boolean;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private airportService: AirportService,
    private flightService: FlightService) {
  }

  ngOnInit(): void {
    this.airports$ = this.airportService.getAll();

    const flightId = this.route.snapshot.paramMap.get('id');
    if (flightId) {
      this.isNew = false;
      this.flight$ = this.flightService.get(parseInt(flightId));
    } else {
      this.isNew = true;
      this.flight$ = of(createEmptyFlight());
    }
  }

  airportsEqual(airportA: Airport, airportB: Airport): boolean {
    return airportA.id === airportB.id;
  }

  async handleSubmit(flight: Flight): Promise<void> {
    await (this.isNew ? this.flightService.create(flight).pipe(mapTo(undefined)) : this.flightService.update(flight))
      .toPromise()
      .then(() => this.router.navigate([ NAVIGATION.FLIGHT, NAVIGATION.LIST ]));
  }

  async handleCancel(): Promise<void> {
    await this.router.navigate([ NAVIGATION.FLIGHT, NAVIGATION.LIST ]);
  }
}
