package com.messanger.resources;

import com.messanger.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {

    private List<User> users = new ArrayList<>();

    public UserRepository() {
//        this.users = List.of(
//                new User("ivan", "12345", "Сергей", "Петров"));
        this.users.add(new User("admin", "admin", "Админ", "Админ"));
    }

    public User getByLogin(String login) {
        return this.users.stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst()
                .orElse(null);
    }

    public void setUser(User user){
        this.users.add(user);
    }

    public User getUserByLogin(String login){
        return this.users.stream().filter(user -> login.equals(user.getLogin()))
                .findFirst()
                .orElse(null);
    }

    public User getUserByUUID(UUID uuid){
        return this.users.stream().filter(user -> uuid.equals(user.getUuid()))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAll() {
        return this.users;
    }
}
