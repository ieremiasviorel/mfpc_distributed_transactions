package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.business_model.Reservation;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;

import java.util.List;

public interface ReservationService extends BaseService<Reservation> {
    List<Reservation> findByFlightId(Long id, Transaction transaction);
}
