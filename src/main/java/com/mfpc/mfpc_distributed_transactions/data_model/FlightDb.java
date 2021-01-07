package com.mfpc.mfpc_distributed_transactions.data_model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FlightDb extends DbRecord {
    private String flightNumber;
    private String airplaneType;
    private Long departureAirportId;
    private Long arrivalAirportId;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
}
