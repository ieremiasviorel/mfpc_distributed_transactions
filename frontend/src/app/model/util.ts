import { Airport, City, Flight, Reservation, User } from './index';

export function createEmptyReservation(): Reservation {
  return {
    id: 0,
    user: createEmptyUser(),
    flight: createEmptyFlight()
  }
}

export function createEmptyFlight(): Flight {
  return {
    id: 0,
    flightNumber: '',
    airplaneType: '',
    departureAirport: createEmptyAirport(),
    arrivalAirport: createEmptyAirport(),
    departureTime: '',
    arrivalTime: ''
  }
}

export function createEmptyAirport(): Airport {
  return {
    id: 0,
    name: '',
    code: '',
    city: createEmptyCity(),
    latitude: 0,
    longitude: 0
  }
}

export function createEmptyCity(): City {
  return {
    id: 0,
    name: '',
    country: ''
  }
}

export function createEmptyUser(): User {
  return {
    id: 0,
    username: '',
    firstName: '',
    lastName: ''
  }
}
