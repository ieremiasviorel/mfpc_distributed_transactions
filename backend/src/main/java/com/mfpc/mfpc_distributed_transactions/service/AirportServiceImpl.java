package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.business_model.Airport;
import com.mfpc.mfpc_distributed_transactions.data_model.AirportDb;
import com.mfpc.mfpc_distributed_transactions.mapper.AirportMapper;
import com.mfpc.mfpc_distributed_transactions.repository.AirportRepository;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class AirportServiceImpl extends AbstractService<Airport, AirportDb> implements AirportService {
    private final AirportMapper airportMapper;
    private final CityService cityService;

    public AirportServiceImpl(AirportRepository airportRepository, AirportMapper airportMapper, CityService cityService) {
        super(airportRepository);
        this.airportMapper = airportMapper;
        this.cityService = cityService;
    }

    @Override
    protected AirportDb tToTDb(Airport airport) {
        return airportMapper.airportToAirportDb(airport);
    }

    @Override
    protected Airport tDbToT(AirportDb airportDb, Transaction transaction) {
        Airport airport = airportMapper.airportDbToAirport(airportDb);

        airport.setCity(cityService.find(airportDb.getCityId(), transaction));

        return airport;
    }
}
