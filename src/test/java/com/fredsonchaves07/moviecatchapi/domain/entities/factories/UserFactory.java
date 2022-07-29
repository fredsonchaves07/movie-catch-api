package com.fredsonchaves07.moviecatchapi.domain.entities.factories;

import com.fredsonchaves07.moviecatchapi.domain.entities.User;

public class UserFactory {

    public static User createUser() {
        User user = new User();
        user.setName("User test");
        user.setEmail("user@email.com");
        user.setPassword("user@123");
        return user;
    }

    public static User createUser(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}