package com.mfpc.mfpc_distributed_transactions.controller;

import com.mfpc.mfpc_distributed_transactions.business_model.Airport;
import com.mfpc.mfpc_distributed_transactions.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/airport")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @GetMapping("{id}")
    public Airport getOne(@PathVariable("id") Long id) {
        return airportService.find(id, null);
    }

    @GetMapping
    public List<Airport> getAll() {
        return airportService.findAll(null);
    }

    @PostMapping()
    public Long create(@RequestBody Airport airport) {
        return airportService.insert(airport, null);
    }

    @PatchMapping()
    public void update(@RequestBody Airport airport) {
        airportService.update(airport, null);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        airportService.delete(id, null);
    }
}
