package com.fredsonchaves07.moviecatchapi.application.repositories;

import com.fredsonchaves07.moviecatchapi.application.database.UserDatabase;
import com.fredsonchaves07.moviecatchapi.application.models.UserModel;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    UserDatabase userDatabase;

    public UserRepositoryImpl(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

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
        UserModel userModel = new UserModel();
        userDatabase.create(userModel.toUser(user));
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void update(User user, String name, String password) {

    }
}
