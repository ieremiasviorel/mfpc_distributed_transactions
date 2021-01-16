import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { mapTo } from 'rxjs/operators';

import { Airport, City } from '../../model';
import { NAVIGATION } from '../../model/constant';
import { createEmptyAirport } from '../../model/util';
import { AirportService } from '../../service/airport.service';
import { CityService } from '../../service/city.service';

@Component({
  selector: 'app-airport-form',
  templateUrl: './airport-form.component.html',
  styleUrls: [ './airport-form.component.scss' ]
})
export class AirportFormComponent implements OnInit {
  cities$!: Observable<City[]>;
  airport$!: Observable<Airport>;
  isNew!: boolean;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private cityService: CityService,
    private airportService: AirportService) {
  }

  ngOnInit(): void {
    this.cities$ = this.cityService.getAll();

    const airportId = this.route.snapshot.paramMap.get('id');
    if (airportId) {
      this.isNew = false;
      this.airport$ = this.airportService.get(parseInt(airportId));
    } else {
      this.isNew = true;
      this.airport$ = of(createEmptyAirport());
    }
  }

  citiesEqual(cityA: City, cityB: City): boolean {
    return cityA.id === cityA.id;
  }

  async handleSubmit(airport: Airport): Promise<void> {
    await (this.isNew ? this.airportService.create(airport).pipe(mapTo(undefined)) : this.airportService.update(airport))
      .toPromise()
      .then(() => this.router.navigate([ NAVIGATION.AIRPORT, NAVIGATION.LIST ]));
  }

  async handleCancel(): Promise<void> {
    await this.router.navigate([ NAVIGATION.AIRPORT, NAVIGATION.LIST ]);
  }
}
