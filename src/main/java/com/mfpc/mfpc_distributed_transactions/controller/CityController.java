package com.mfpc.mfpc_distributed_transactions.controller;

import com.mfpc.mfpc_distributed_transactions.model.CityDb;
import com.mfpc.mfpc_distributed_transactions.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public String create(@RequestParam("name") String name, @RequestParam("country") String country) {
        CityDb cityDb = new CityDb();
        cityDb.setName(name);
        cityDb.setCountry(country);

        Long id = cityRepository.insert(cityDb);

        return "redirect:/city/" + id;
    }
}
