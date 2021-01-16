import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Flight } from '../../model';
import { NAVIGATION } from '../../model/constant';
import { FlightService } from '../../service/flight.service';

@Component({
  selector: 'app-flight-list',
  templateUrl: './flight-list.component.html',
  styleUrls: [ './flight-list.component.scss' ],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class FlightListComponent implements OnInit {
  flights$!: Observable<Flight[]>;
  expandedElement: Flight | undefined;
  displayedColumns = [ 'id', 'flightNumber', 'airplaneType', 'departureAirport', 'departureTime', 'arrivalAirport', 'arrivalTime' ];

  constructor(private router: Router, private flightService: FlightService) {
  }

  ngOnInit(): void {
    this.flights$ = this.flightService.getAll();
  }

  async handleEdit(flight: Flight): Promise<void> {
    await this.router.navigate([ NAVIGATION.FLIGHT, NAVIGATION.FORM, flight.id ]);
  }

  async handleCreate(): Promise<void> {
    await this.router.navigate([ NAVIGATION.FLIGHT, NAVIGATION.FORM ]);
  }

  handleDelete(flight: Flight): void {
    this.flights$ = this.flightService.delete(flight.id).pipe(mergeMap(() => this.flightService.getAll()));
  }
}
