package com.mfpc.mfpc_distributed_transactions.controller;

import com.mfpc.mfpc_distributed_transactions.business_model.User;
import com.mfpc.mfpc_distributed_transactions.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("user")
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
    public RedirectView create(@RequestBody User user) {
        Long id = userService.insert(user, null);

        return new RedirectView("/user/" + id);
    }

    @PatchMapping()
    public RedirectView update(@RequestBody User user) {
        userService.update(user, null);

        return new RedirectView("/user/" + user.getId());
    }

    @DeleteMapping("{id}")
    public RedirectView delete(@PathVariable Long id) {
        userService.delete(id, null);

        return new RedirectView("/user");
    }
}
