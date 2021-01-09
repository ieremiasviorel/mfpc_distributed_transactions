package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.business_model.City;
import com.mfpc.mfpc_distributed_transactions.data_model.CityDb;
import com.mfpc.mfpc_distributed_transactions.mapper.CityMapper;
import com.mfpc.mfpc_distributed_transactions.repository.CityRepository;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends AbstractService<City, CityDb> implements CityService {
    private final CityMapper cityMapper;

    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        super(cityRepository);
        this.cityMapper = cityMapper;
    }

    @Override
    protected CityDb tToTDb(City city) {
        return cityMapper.cityToCityDb(city);
    }

    @Override
    protected City tDbToT(CityDb cityDb, Transaction transaction) {
        return cityMapper.cityDbToCity(cityDb);
    }
}
