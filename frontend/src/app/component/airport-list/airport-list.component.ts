import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Airport } from '../../model';
import { NAVIGATION } from '../../model/constant';
import { AirportService } from '../../service/airport.service';

@Component({
  selector: 'app-airport-list',
  templateUrl: './airport-list.component.html',
  styleUrls: [ './airport-list.component.scss' ],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class AirportListComponent implements OnInit {
  airports$!: Observable<Airport[]>;
  expandedElement: Airport | undefined;

  displayedColumns = [ 'id', 'name', 'code', 'location', 'latitude', 'longitude' ];

  constructor(private router: Router, private airportService: AirportService) {
  }

  ngOnInit(): void {
    this.airports$ = this.airportService.getAll();
  }

  async handleEdit(airport: Airport): Promise<void> {
    await this.router.navigate([ NAVIGATION.AIRPORT, NAVIGATION.FORM, airport.id ]);
  }

  async handleCreate(): Promise<void> {
    await this.router.navigate([ NAVIGATION.AIRPORT, NAVIGATION.FORM ]);
  }

  handleDelete(airport: Airport): void {
    this.airports$ = this.airportService.delete(airport.id).pipe(mergeMap(() => this.airportService.getAll()));
  }
}
