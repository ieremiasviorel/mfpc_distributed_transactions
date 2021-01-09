package com.mfpc.mfpc_distributed_transactions.controller;

import com.mfpc.mfpc_distributed_transactions.business_model.Airport;
import com.mfpc.mfpc_distributed_transactions.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
        return airportService.findAll(null);
    }

    @PostMapping()
    public RedirectView create(@RequestBody Airport airport) {
        Long id = airportService.insert(airport, null);

        return new RedirectView("/airport/" + id);
    }

    @PatchMapping()
    public RedirectView update(@RequestBody Airport airport) {
        airportService.update(airport, null);

        return new RedirectView("/airport/" + airport.getId());
    }

    @DeleteMapping("{id}")
    public RedirectView delete(@PathVariable Long id) {
        airportService.delete(id, null);

        return new RedirectView("/airport");
    }
}
