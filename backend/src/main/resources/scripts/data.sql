INSERT INTO city (id, name, country)
VALUES (1, 'New York', 'USA');
INSERT INTO city (id, name, country)
VALUES (2, 'Los Angeles', 'USA');
INSERT INTO city (id, name, country)
VALUES (3, 'Paris', 'France');
INSERT INTO city (id, name, country)
VALUES (4, 'Berlin', 'Germany');

INSERT INTO airport (id, cityId, name, code, latitude, longitude)
VALUES (1, 1, 'John F. Kennedy International Airport', 'JFK', 40.6413, -73.7781);
INSERT INTO airport (id, cityId, name, code, latitude, longitude)
VALUES (2, 2, 'Los Angeles International Airport', 'LAX', 33.9416, -118.4085);
INSERT INTO airport (id, cityId, name, code, latitude, longitude)
VALUES (3, 3, 'Paris Charles de Gaulle Airport', 'CDG', 49.0097, 2.5480);
INSERT INTO airport (id, cityId, name, code, latitude, longitude)
VALUES (4, 4, 'Berlin Tempelhof Airport', 'THF', 52.4825, 13.3891);

INSERT INTO flight (id, flightNumber, airplaneType, departureAirportId, arrivalAirportId, departureTime, arrivalTime)
VALUES (1, 'JAU-3683', 'Airbus A350', 1, 2, '2019-07-16 16:30:00', '2019-07-16 21:46:00');
INSERT INTO flight (id, flightNumber, airplaneType, departureAirportId, arrivalAirportId, departureTime, arrivalTime)
VALUES (2, 'JAU-3684', 'Airbus A350', 1, 3, '2019-07-16 12:00:00', '2019-07-16 18:15:00');
INSERT INTO flight (id, flightNumber, airplaneType, departureAirportId, arrivalAirportId, departureTime, arrivalTime)
VALUES (3, 'JAU-3685', 'Airbus A350', 1, 4, '2019-07-16 10:30:00', '2019-07-16 21:30:00');
INSERT INTO flight (id, flightNumber, airplaneType, departureAirportId, arrivalAirportId, departureTime, arrivalTime)
VALUES (4, 'JAU-3686', 'Airbus A350', 3, 4, '2019-07-16 14:30:00', '2019-07-16 16:00:00');

INSERT INTO user (id, username, firstName, lastName)
VALUES (1, 'jdoe1', 'John', 'Doe');
INSERT INTO user (id, username, firstName, lastName)
VALUES (2, 'jdoe2', 'Jane', 'Doe');
INSERT INTO user (id, username, firstName, lastName)
VALUES (3, 'tbuck', 'Ted', 'Buck');

INSERT INTO reservation (id, flightId, userId)
VALUES (1, 1, 1);
INSERT INTO reservation (id, flightId, userId)
VALUES (2, 1, 2);
INSERT INTO reservation (id, flightId, userId)
VALUES (3, 2, 3);