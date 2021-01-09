package com.mfpc.mfpc_distributed_transactions.controller;

import com.mfpc.mfpc_distributed_transactions.business_model.City;
import com.mfpc.mfpc_distributed_transactions.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("city")
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
    public RedirectView create(@RequestBody City city) {
        Long id = cityService.insert(city, null);

        return new RedirectView("/city/" + id);
    }

    @PatchMapping()
    public RedirectView update(@RequestBody City city) {
        cityService.update(city, null);

        return new RedirectView("/city/" + city.getId());
    }

    @DeleteMapping("{id}")
    public RedirectView delete(@PathVariable Long id) {
        cityService.delete(id, null);

        return new RedirectView("/city");
    }
}
