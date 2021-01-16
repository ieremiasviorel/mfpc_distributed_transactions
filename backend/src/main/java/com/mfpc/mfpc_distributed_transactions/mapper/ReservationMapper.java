package com.mfpc.mfpc_distributed_transactions.mapper;

import com.mfpc.mfpc_distributed_transactions.business_model.Flight;
import com.mfpc.mfpc_distributed_transactions.business_model.Reservation;
import com.mfpc.mfpc_distributed_transactions.business_model.User;
import com.mfpc.mfpc_distributed_transactions.data_model.ReservationDb;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface ReservationMapper {
    Reservation reservationDbToReservation(ReservationDb reservationDb);


    @Mapping(source = "flight", target = "flightId", qualifiedByName = "flightToFlightId")
    @Mapping(source = "user", target = "userId", qualifiedByName = "userToUserId")
    ReservationDb reservationToReservationDb(Reservation reservation);

    @Named("flightToFlightId")
    static Long flightToFlightId(Flight flight) {
        return flight.getId();
    }

    @Named("userToUserId")
    static Long userToUserId(User user) {
        return user.getId();
    }
}
