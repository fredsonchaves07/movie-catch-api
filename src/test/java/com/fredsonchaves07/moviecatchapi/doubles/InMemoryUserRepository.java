package com.fredsonchaves07.moviecatchapi.doubles;

import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRepository implements UserRepository {

    private List<User> users = new ArrayList<>();

    @Override
    public List<User> findByName(String name) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public void create(User user) {
        users.add(user);
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void update(User user, String name, String password) {

    }
}
