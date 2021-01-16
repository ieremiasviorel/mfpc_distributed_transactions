type DbRecord = {
  id: number;
}

export type City = DbRecord & {
  name: string;
  country: string;
}

export type Airport = DbRecord & {
  city: City;
  name: string;
  code: string;
  latitude: number;
  longitude: number;
}

export type Flight = DbRecord & {
  flightNumber: string;
  airplaneType: string;
  departureAirport: Airport;
  arrivalAirport: Airport;
  departureTime: string;
  arrivalTime: string;
}

export type User = DbRecord & {
  username: string;
  firstName: string;
  lastName: string;
}

export type Reservation = DbRecord & {
  user: User;
  flight: Flight;
}
