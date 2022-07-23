package com.fredsonchaves07.moviecatchapi.factories;

import com.fredsonchaves07.moviecatchapi.domain.entities.User;

public class UserFactory {

    public static User createUser() {
        return new User("User test", "user@email.com", "user@123");
    }

    public static User createUser(String name, String email, String password) {
        return new User(name, email, password);
    }
}