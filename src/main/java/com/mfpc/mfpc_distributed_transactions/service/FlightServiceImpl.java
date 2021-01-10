package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.business_model.Flight;
import com.mfpc.mfpc_distributed_transactions.data_model.FlightDb;
import com.mfpc.mfpc_distributed_transactions.data_model.ReservationDb;
import com.mfpc.mfpc_distributed_transactions.mapper.FlightMapper;
import com.mfpc.mfpc_distributed_transactions.repository.FlightRepository;
import com.mfpc.mfpc_distributed_transactions.repository.ReservationRepository;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl extends AbstractService<Flight, FlightDb> implements FlightService {
    private final FlightMapper flightMapper;
    private final AirportService airportService;
    private final ReservationRepository reservationRepository;

    public FlightServiceImpl(
            FlightRepository flightRepository,
            FlightMapper flightMapper,
            AirportService airportService,
            ReservationRepository reservationRepository) {
        super(flightRepository);
        this.flightMapper = flightMapper;
        this.airportService = airportService;
        this.reservationRepository = reservationRepository;
    }

    /**
     * Simulate a situation where a read is blocked by a write and needs to wait
     * 1. Start updating a flight
     * 2. Start a find for the same flight
     * => the find request gets locked waiting for the lock
     * => the update request finishes and releases the lock
     * => the find request is resumed and finishes
     */
    @Override
    public void update(Flight flight, Transaction transaction) {
        transaction = super.initializeAndRegisterTransactionIfNeeded(transaction);

        repository.update(tToTDb(flight), transaction);

        // simulate a long-running transaction
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        commitTransactionIfNeeded(transaction);
    }

    /**
     * Simulate a deadlock between
     * - a read on a reservation (steps: 1. read Reservation, 2. read Flight)
     * - a write on a flight (steps: 1. delete Flight, 2. delete all flight Reservations)
     * <p>
     * 1. transaction 1 executes step 1 => Reservation is locked
     * 2. transaction 2 executes step 1 => Flight is locked
     * => transaction 1 waits on Flight lock => transaction 1 waits on transaction 2
     * => transaction 2 waits on Reservation lock => transaction 2 waits on transaction 1
     * =>=> deadlock
     */
    @Override
    public void delete(Long id, Transaction transaction) {
        transaction = initializeAndRegisterTransactionIfNeeded(transaction);

        List<ReservationDb> flightReservations = reservationRepository.findByFlightId(id, transaction);

        repository.delete(id, transaction);

        // simulate a long-running transaction
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (ReservationDb reservation : flightReservations) {
            reservationRepository.delete(reservation.getId(), transaction);
        }

        commitTransactionIfNeeded(transaction);
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
