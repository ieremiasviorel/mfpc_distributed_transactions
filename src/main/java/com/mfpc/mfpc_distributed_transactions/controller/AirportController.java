package com.mfpc.mfpc_distributed_transactions.controller;

import com.mfpc.mfpc_distributed_transactions.business_model.Airport;
import com.mfpc.mfpc_distributed_transactions.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("airport")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @GetMapping("{id}")
    public Airport getOne(@PathVariable("id") Long id) {
        return airportService.find(id, null);
    }

    @GetMapping
    public List<Airport> getAll() {
        return airportService.findAll();
    }
}
