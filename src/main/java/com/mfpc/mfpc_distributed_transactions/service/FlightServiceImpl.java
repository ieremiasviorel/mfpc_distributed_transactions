package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.business_model.Flight;
import com.mfpc.mfpc_distributed_transactions.data_model.FlightDb;
import com.mfpc.mfpc_distributed_transactions.mapper.FlightMapper;
import com.mfpc.mfpc_distributed_transactions.repository.FlightRepository;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl extends AbstractService implements FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private final AirportService airportService;

    @Override
    public Long insert(Flight flight) {
        return null;
    }

    @Override
    public Flight find(Long id, Transaction transaction) {
        transaction = initializeAndRegisterTransactionIfNeeded(transaction);

        FlightDb flightDb = flightRepository.find(id, transaction);

        Flight flight = flightDbToFlight(flightDb, transaction);

        commitTransactionIfNeeded(transaction);

        return flight;
    }

    @Override
    public List<Flight> findAll() {
        return null;
    }

    @Override
    public void update(Flight flight) {

    }

    @Override
    public void delete(Long id) {

    }

    private Flight flightDbToFlight(FlightDb flightDb, Transaction transaction) {
        Flight flight = flightMapper.flightDbToFlight(flightDb);

        flight.setDepartureAirport(airportService.find(flightDb.getDepartureAirportId(), transaction));
        flight.setArrivalAirport(airportService.find(flightDb.getArrivalAirportId(), transaction));

        return flight;
    }
}
