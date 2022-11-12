package com.messenger.rest;

import com.messenger.services.UserService;
import com.messenger.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(path = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<User> setUser(@RequestBody User user) {
        User u = service.getByLogin(user.getLogin());
        if (u != null)
            return new ResponseEntity<>(new User("", "", null, "", ""), HttpStatus.CONFLICT);
        service.setUser(user);
        return new ResponseEntity<>(service.getByLogin(user.getLogin()), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<User> getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        org.springframework.security.core.userdetails.User user = (principal instanceof org.springframework.security.core.userdetails.User) ? (org.springframework.security.core.userdetails.User) principal : null;
        if (Objects.nonNull(user)) this.service.getByLogin(user.getUsername()).setSession(UUID.randomUUID());
        HttpHeaders responseHeaders = new HttpHeaders();
        if (user == null)
            return new ResponseEntity<>(new User("", "", null, "", ""), HttpStatus.NOT_FOUND);
        responseHeaders.set("user-uuid", this.service.getByLogin(user.getUsername()).getSession().toString());
        return new ResponseEntity<>(this.service.getByLogin(user.getUsername()), responseHeaders, HttpStatus.OK);
    }

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<User> getAll() {
        return this.service.getAll();
    }

    @GetMapping(path = "/valid", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<User> isValid(@RequestHeader("user-uuid") UUID uuid) {
        User user= this.service.getBySession(uuid);
        if(user!=null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(new User("", "", null, "", ""), HttpStatus.UNAUTHORIZED);
    }
}
