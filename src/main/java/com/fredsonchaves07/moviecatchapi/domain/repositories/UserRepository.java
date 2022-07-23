package com.fredsonchaves07.moviecatchapi.domain.repositories;

import com.fredsonchaves07.moviecatchapi.domain.entities.User;

import java.util.List;

public interface UserRepository {

    List<User> getByName(String name);

    User getByEmail(String email);

    void create(User user);

    void delete(User user);

    void update(User user, String name, String password);
}
