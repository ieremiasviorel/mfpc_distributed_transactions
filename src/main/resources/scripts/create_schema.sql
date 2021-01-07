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
