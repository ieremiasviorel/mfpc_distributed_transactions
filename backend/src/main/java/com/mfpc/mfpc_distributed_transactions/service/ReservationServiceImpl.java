package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.business_model.Reservation;
import com.mfpc.mfpc_distributed_transactions.data_model.ReservationDb;
import com.mfpc.mfpc_distributed_transactions.mapper.ReservationMapper;
import com.mfpc.mfpc_distributed_transactions.repository.ReservationRepository;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl extends AbstractService<Reservation, ReservationDb> implements ReservationService {
    private final ReservationMapper reservationMapper;
    private final FlightService flightService;
    private final UserService userService;

    private final Environment environment;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper, FlightService flightService, UserService userService, Environment environment) {
        super(reservationRepository);
        this.reservationMapper = reservationMapper;
        this.flightService = flightService;
        this.userService = userService;
        this.environment = environment;
    }

    @Override
    public List<Reservation> findByFlightId(Long id, Transaction transaction) {
        transaction = initializeAndRegisterTransactionIfNeeded(transaction);

        List<Reservation> reservations = repository.findAll(transaction)
                .stream()
                .map(this.reservationMapper::reservationDbToReservation)
                .collect(Collectors.toList());

        commitTransactionIfNeeded(transaction);

        return reservations;
    }

    @Override
    protected ReservationDb tToTDb(Reservation reservation) {
        return reservationMapper.reservationToReservationDb(reservation);
    }

    @Override
    protected Reservation tDbToT(ReservationDb reservationDb, Transaction transaction) {
        Reservation reservation = reservationMapper.reservationDbToReservation(reservationDb);

        // simulate a long-running transaction
        try {
            if (environment.acceptsProfiles(Profiles.of("simulate"))) {
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        reservation.setFlight(flightService.find(reservationDb.getFlightId(), transaction));
        reservation.setUser(userService.find(reservationDb.getUserId(), transaction));

        return reservation;
    }
}
