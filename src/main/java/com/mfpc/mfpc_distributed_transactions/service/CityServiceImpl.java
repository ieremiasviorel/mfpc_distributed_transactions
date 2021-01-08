package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.business_model.City;
import com.mfpc.mfpc_distributed_transactions.data_model.CityDb;
import com.mfpc.mfpc_distributed_transactions.mapper.CityMapper;
import com.mfpc.mfpc_distributed_transactions.repository.CityRepository;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    public Long insert(City city) {
        return cityRepository.insert(cityMapper.cityToCityDb(city));
    }

    @Override
    public City find(Long id, Transaction transaction) {
        transaction = initializeAndRegisterTransactionIfNeeded(transaction);

        CityDb cityDb = cityRepository.find(id, transaction);

        if (cityDb != null) {
            return cityMapper.cityDbToCity(cityDb);
        } else {
            return null;
        }
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll()
                .stream()
                .map(cityMapper::cityDbToCity)
                .collect(Collectors.toList());
    }

    @Override
    public void update(City city) {
        cityRepository.update(cityMapper.cityToCityDb(city));
    }

    @Override
    public void delete(Long id) {
        cityRepository.delete(id);
    }
}
