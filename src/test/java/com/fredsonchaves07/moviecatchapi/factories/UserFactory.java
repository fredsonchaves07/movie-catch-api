package com.fredsonchaves07.moviecatchapi.factories;

import com.fredsonchaves07.moviecatchapi.domain.dto.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;

public class UserFactory {

    public static CreateUserDTO createUserDTO() {
        return new CreateUserDTO("User Test", "usertest@email.com", "user@123");
    }

    public static CreateUserDTO createUserDTO(String name, String email, String password) {
        return new CreateUserDTO(name, email, password);
    }

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