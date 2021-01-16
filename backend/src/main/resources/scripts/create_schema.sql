create table city (
    id bigint not null auto_increment,
    name varchar(255),
    country varchar(255),
    primary key (id)
) engine=InnoDB;

create table airport (
    id bigint not null auto_increment,
    cityId bigint not null,
    code varchar(255),
    name varchar(255),
    latitude float,
    longitude float,
    primary key (id)
) engine=InnoDB;

create table flight (
    id bigint not null auto_increment,
    flightNumber varchar(255),
    airplaneType varchar(255),
    departureAirportId bigint not null,
    arrivalAirportId bigint not null,
    departureTime timestamp,
    arrivalTime timestamp,
    primary key (id)
) engine=InnoDB;

create table user (
    id bigint not null auto_increment,
    username varchar(50) unique,
    firstName varchar(255),
    lastName varchar(255),
    primary key (id)
) engine=InnoDB;

create table reservation (
    id bigint not null auto_increment,
    flightId bigint not null,
    userId bigint not null,
    primary key (id)
) engine=InnoDB;
