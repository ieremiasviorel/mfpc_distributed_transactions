package com.mfpc.mfpc_distributed_transactions.controller;

import com.mfpc.mfpc_distributed_transactions.business_model.Reservation;
import com.mfpc.mfpc_distributed_transactions.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("reservation")
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
    public RedirectView create(@RequestBody Reservation reservation) {
        Long id = reservationService.insert(reservation, null);

        return new RedirectView("/reservation/" + id);
    }

    @PatchMapping()
    public RedirectView update(@RequestBody Reservation reservation) {
        reservationService.update(reservation, null);

        return new RedirectView("/reservation/" + reservation.getId());
    }

    @DeleteMapping("{id}")
    public RedirectView delete(@PathVariable Long id) {
        reservationService.delete(id, null);

        return new RedirectView("/reservation");
    }
}
