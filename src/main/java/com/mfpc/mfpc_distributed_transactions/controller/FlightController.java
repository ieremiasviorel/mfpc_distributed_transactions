package com.mfpc.mfpc_distributed_transactions.controller;

import com.mfpc.mfpc_distributed_transactions.business_model.Flight;
import com.mfpc.mfpc_distributed_transactions.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("api/flight")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping("{id}")
    public Flight getOne(@PathVariable("id") Long id) {
        return flightService.find(id, null);
    }

    @GetMapping
    public List<Flight> getAll() {
        return flightService.findAll(null);
    }

    @PostMapping()
    public Long create(@RequestBody Flight flight) {
        return flightService.insert(flight, null);
    }

    @PatchMapping()
    public void update(@RequestBody Flight flight) {
        flightService.update(flight, null);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        flightService.delete(id, null);
    }
}
