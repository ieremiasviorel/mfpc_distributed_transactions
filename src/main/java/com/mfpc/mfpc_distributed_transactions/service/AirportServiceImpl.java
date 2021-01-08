package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.business_model.Airport;
import com.mfpc.mfpc_distributed_transactions.data_model.AirportDb;
import com.mfpc.mfpc_distributed_transactions.mapper.AirportMapper;
import com.mfpc.mfpc_distributed_transactions.repository.AirportRepository;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import com.mfpc.mfpc_distributed_transactions.transaction.service.TransactionScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;
    private final CityService cityService;

    @Override
    public Long insert(Airport airport) {
        return null;
    }

    @Override
    public Airport find(Long id, Transaction transaction) {
        transaction = Transaction.defaultTransaction(transaction, currentThread());

        TransactionScheduler.addTransaction(transaction);

        AirportDb airportDb = airportRepository.find(id, transaction);

        return airportDbToAirport(airportDb, transaction);
    }

    @Override
    public List<Airport> findAll() {
        return airportRepository.findAll()
                .stream()
                .map(airportDb -> airportDbToAirport(airportDb, null))
                .collect(Collectors.toList());
    }

    @Override
    public void update(Airport airport) {

    }

    @Override
    public void delete(Long id) {

    }

    private Airport airportDbToAirport(AirportDb airportDb, Transaction transaction) {
        Airport airport = airportMapper.airportDbToAirport(airportDb);

        airport.setCity(cityService.find(airportDb.getCityId(), transaction));

        return airport;
    }
}
