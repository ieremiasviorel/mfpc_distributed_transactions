package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.business_model.Reservation;
import com.mfpc.mfpc_distributed_transactions.data_model.ReservationDb;
import com.mfpc.mfpc_distributed_transactions.mapper.ReservationMapper;
import com.mfpc.mfpc_distributed_transactions.repository.ReservationRepository;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl extends AbstractService<Reservation, ReservationDb> implements ReservationService {
    private final ReservationMapper reservationMapper;
    private final FlightService flightService;
    private final UserService userService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper, FlightService flightService, UserService userService) {
        super(reservationRepository);
        this.reservationMapper = reservationMapper;
        this.flightService = flightService;
        this.userService = userService;
    }

    @Override
    protected ReservationDb tToTDb(Reservation reservation) {
        return reservationMapper.reservationToReservationDb(reservation);
    }

    @Override
    protected Reservation tDbToT(ReservationDb reservationDb, Transaction transaction) {
        Reservation reservation = reservationMapper.reservationDbToReservation(reservationDb);

        reservation.setFlight(flightService.find(reservationDb.getFlightId(), transaction));
        reservation.setUser(userService.find(reservationDb.getUserId(), transaction));

        return reservation;
    }
}
