package com.mfpc.mfpc_distributed_transactions.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Flight extends DbRecord {
    private String flightNumber;
    private String airplaneType;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
}
