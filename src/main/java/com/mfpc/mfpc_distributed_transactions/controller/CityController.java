package com.mfpc.mfpc_distributed_transactions.controller;

import com.mfpc.mfpc_distributed_transactions.data_model.CityDb;
import com.mfpc.mfpc_distributed_transactions.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("city")
@RequiredArgsConstructor
public class CityController {

    private final CityRepository cityRepository;

    @GetMapping("{id}")
    public CityDb getOne(@PathVariable("id") Long id) {
        return cityRepository.find(id);
    }

    @GetMapping
    public List<CityDb> getAll() {
        return cityRepository.findAll();
    }

    @GetMapping("insert")
    public RedirectView create(@RequestParam("name") String name, @RequestParam("country") String country) {
        CityDb cityDb = new CityDb();
        cityDb.setName(name);
        cityDb.setCountry(country);

        Long id = cityRepository.insert(cityDb);

        return new RedirectView("/city/" + id);
    }

    @GetMapping("delete/{id}")
    public RedirectView create(@PathVariable Long id) {
        cityRepository.delete(id);
        return new RedirectView("/city");
    }
}
