package com.fredsonchaves07.moviecatchapi.application.database;

import com.fredsonchaves07.moviecatchapi.application.models.UserModel;

public interface UserDatabase {

    public UserModel create(UserModel userModel);
}
