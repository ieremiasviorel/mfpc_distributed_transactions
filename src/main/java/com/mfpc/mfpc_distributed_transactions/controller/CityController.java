package com.mfpc.mfpc_distributed_transactions.controller;

import com.mfpc.mfpc_distributed_transactions.business_model.City;
import com.mfpc.mfpc_distributed_transactions.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("api/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("{id}")
    public City getOne(@PathVariable("id") Long id) {
        return cityService.find(id, null);
    }

    @GetMapping
    public List<City> getAll() {
        return cityService.findAll(null);
    }

    @PostMapping()
    public Long create(@RequestBody City city) {
        return cityService.insert(city, null);
    }

    @PatchMapping()
    public void update(@RequestBody City city) {
        cityService.update(city, null);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        cityService.delete(id, null);
    }
}
