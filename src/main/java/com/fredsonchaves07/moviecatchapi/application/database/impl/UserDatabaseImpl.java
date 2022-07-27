package com.fredsonchaves07.moviecatchapi.application.database.impl;

import com.fredsonchaves07.moviecatchapi.application.database.UserDatabase;
import com.fredsonchaves07.moviecatchapi.application.database.jpa.JpaUserRepository;
import com.fredsonchaves07.moviecatchapi.application.models.UserModel;

public class UserDatabaseImpl implements UserDatabase {

    JpaUserRepository jpaUserRepository;

    public UserDatabaseImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public UserModel create(UserModel userModel) {
        return jpaUserRepository.save(userModel);
    }
}
