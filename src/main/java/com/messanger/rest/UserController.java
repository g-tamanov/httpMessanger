package com.messanger.rest;

import com.messanger.services.UserService;
import com.messanger.model.User;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(path = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UUID setUser(@RequestBody User user) {
        service.setUser(user);
        return service.getByLogin(user.getLogin()).getUuid();
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    UUID getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        org.springframework.security.core.userdetails.User user = (principal instanceof org.springframework.security.core.userdetails.User) ? (org.springframework.security.core.userdetails.User) principal : null;
        if (Objects.nonNull(user)) this.service.getByLogin(user.getUsername()).setUuid(UUID.randomUUID());
        return Objects.nonNull(user) ? this.service.getByLogin(user.getUsername()).getUuid() : null;
    }

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> getAll() {
        return this.service.getAll();
    }
}
