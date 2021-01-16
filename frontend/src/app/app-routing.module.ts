import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AirportFormComponent } from './component/airport-form/airport-form.component';
import { AirportListComponent } from './component/airport-list/airport-list.component';
import { AirportComponent } from './component/airport/airport.component';
import { FlightFormComponent } from './component/flight-form/flight-form.component';
import { FlightListComponent } from './component/flight-list/flight-list.component';
import { FlightComponent } from './component/flight/flight.component';
import { ReservationFormComponent } from './component/reservation-form/reservation-form.component';
import { ReservationListComponent } from './component/reservation-list/reservation-list.component';
import { ReservationComponent } from './component/reservation/reservation.component';
import { NAVIGATION } from './model/constant';

const routes: Routes = [
  {
    path: NAVIGATION.AIRPORT,
    component: AirportComponent,
    children: [
      {
        path: NAVIGATION.LIST,
        component: AirportListComponent
      },
      {
        path: NAVIGATION.FORM,
        component: AirportFormComponent
      },
      {
        path: NAVIGATION.FORM + '/:id',
        component: AirportFormComponent
      }
    ]
  },
  {
    path: NAVIGATION.FLIGHT,
    component: FlightComponent,
    children: [
      {
        path: NAVIGATION.LIST,
        component: FlightListComponent
      },
      {
        path: NAVIGATION.FORM,
        component: FlightFormComponent
      },
      {
        path: NAVIGATION.FORM + '/:id',
        component: FlightFormComponent
      }
    ]
  },
  {
    path: NAVIGATION.RESERVATION,
    component: ReservationComponent,
    children: [
      {
        path: NAVIGATION.LIST,
        component: ReservationListComponent
      },
      {
        path: NAVIGATION.FORM,
        component: ReservationFormComponent
      },
      {
        path: NAVIGATION.FORM + '/:id',
        component: ReservationFormComponent
      }
    ]
  }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
