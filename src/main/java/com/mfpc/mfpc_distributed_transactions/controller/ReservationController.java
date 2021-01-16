package com.mfpc.mfpc_distributed_transactions.controller;

import com.mfpc.mfpc_distributed_transactions.business_model.Reservation;
import com.mfpc.mfpc_distributed_transactions.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("{id}")
    public Reservation getOne(@PathVariable("id") Long id) {
        return reservationService.find(id, null);
    }

    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.findAll(null);
    }

    @PostMapping()
    public Long create(@RequestBody Reservation reservation) {
        return reservationService.insert(reservation, null);
    }

    @PatchMapping()
    public void update(@RequestBody Reservation reservation) {
        reservationService.update(reservation, null);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        reservationService.delete(id, null);
    }
}
