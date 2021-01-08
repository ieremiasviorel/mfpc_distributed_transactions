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
        return cityService.findAll();
    }

    @GetMapping("insert")
    public RedirectView create(@RequestParam("name") String name, @RequestParam("country") String country) {
        City city = new City();
        city.setName(name);
        city.setCountry(country);

        Long id = cityService.insert(city);

        return new RedirectView("/city/" + id);
    }

    @GetMapping("delete/{id}")
    public RedirectView create(@PathVariable Long id) {
        cityService.delete(id);
        return new RedirectView("/city");
    }
}
