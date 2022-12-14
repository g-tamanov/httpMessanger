package com.messenger.services;

import com.messenger.resources.UserRepository;
import com.messenger.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void setUser(User user) {
        User u = getByLogin(user.getLogin());
        if (Objects.nonNull(u)) {
            throw new RuntimeException(String.format("User %s is exist", user.getLogin()));
        }else {
            this.repository.setUser(user);
        }
    }

    public List<User> getAll() {
        return this.repository.getAll();
    }

    public User getByLogin(String login) {
        return this.repository.getByLogin(login);
    }

    public User getBySession(UUID session) {
        return this.repository.getUserBySession(session);
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User u = getByLogin(login);
        if (Objects.isNull(u)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        return new org.springframework.security.core.userdetails.User(u.getLogin(), u.getPassword(), true, true, true, true, new HashSet<>());
    }
}
