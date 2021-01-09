INSERT INTO city (id, name, country)
VALUES (1, 'New York', 'USA');
INSERT INTO city (id, name, country)
VALUES (2, 'Los Angeles', 'USA');

INSERT INTO airport (id, cityId, name, code, latitude, longitude)
VALUES (1, 1, 'John F. Kennedy International Airport', 'JFK', 40.6413, -73.7781);

INSERT INTO airport (id, cityId, name, code, latitude, longitude)
VALUES (2, 2, 'Los Angeles International Airport', 'LAX', 33.9416, -118.4085);

INSERT INTO flight (id, flightNumber, airplaneType, departureAirportId, arrivalAirportId, departureTime, arrivalTime)
VALUES (1, 'JAU-3683', 'Airbus A350', 1, 2, '2019-07-16 16:30:00', '2019-07-16 21:46:00');
