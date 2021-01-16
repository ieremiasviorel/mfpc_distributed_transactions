package com.mfpc.mfpc_distributed_transactions.controller;

import com.mfpc.mfpc_distributed_transactions.business_model.User;
import com.mfpc.mfpc_distributed_transactions.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{id}")
    public User getOne(@PathVariable("id") Long id) {
        return userService.find(id, null);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.findAll(null);
    }

    @PostMapping()
    public Long create(@RequestBody User user) {
        return userService.insert(user, null);
    }

    @PatchMapping()
    public void update(@RequestBody User user) {
        userService.update(user, null);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id, null);
    }
}
