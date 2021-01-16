import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AirportFormComponent } from './component/airport-form/airport-form.component';
import { AirportListComponent } from './component/airport-list/airport-list.component';
import { AirportComponent } from './component/airport/airport.component';
import { FlightFormComponent } from './component/flight-form/flight-form.component';
import { FlightListComponent } from './component/flight-list/flight-list.component';
import { FlightComponent } from './component/flight/flight.component';
import { ReservationFormComponent } from './component/reservation-form/reservation-form.component';
import { ReservationListComponent } from './component/reservation-list/reservation-list.component';
import { ReservationComponent } from './component/reservation/reservation.component';

@NgModule({
  declarations: [
    AppComponent,
    AirportListComponent,
    AirportFormComponent,
    AirportComponent,
    FlightComponent,
    FlightListComponent,
    FlightFormComponent,
    ReservationComponent,
    ReservationListComponent,
    ReservationFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    MatButtonModule,
    MatInputModule,
    MatTableModule,
    MatTabsModule,
    MatFormFieldModule,
    MatSelectModule,
  ],
  providers: [],
  bootstrap: [ AppComponent ]
})
export class AppModule {
}
