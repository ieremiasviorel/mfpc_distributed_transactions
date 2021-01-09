package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.business_model.Flight;
import com.mfpc.mfpc_distributed_transactions.data_model.FlightDb;
import com.mfpc.mfpc_distributed_transactions.mapper.FlightMapper;
import com.mfpc.mfpc_distributed_transactions.repository.FlightRepository;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl extends AbstractService<Flight, FlightDb> implements FlightService {
    private final FlightMapper flightMapper;
    private final FlightRepository flightRepository;
    private final AirportService airportService;

    public FlightServiceImpl(FlightRepository flightRepository, FlightMapper flightMapper, AirportService airportService) {
        super(flightRepository);
        this.flightMapper = flightMapper;
        this.flightRepository =flightRepository;
        this.airportService = airportService;
    }

    @Override
    public Long insert(Flight flight, Transaction transaction) {
        transaction = super.initializeAndRegisterTransactionIfNeeded(transaction);

        Long id = flightRepository.insert(tToTDb(flight), transaction);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        commitTransactionIfNeeded(transaction);

        return id;
    }

    @Override
    protected FlightDb tToTDb(Flight flight) {
        return flightMapper.flightToFlightDb(flight);
    }

    @Override
    protected Flight tDbToT(FlightDb flightDb, Transaction transaction) {
        Flight flight = flightMapper.flightDbToFlight(flightDb);

        flight.setDepartureAirport(airportService.find(flightDb.getDepartureAirportId(), transaction));
        flight.setArrivalAirport(airportService.find(flightDb.getArrivalAirportId(), transaction));

        return flight;
    }
}
