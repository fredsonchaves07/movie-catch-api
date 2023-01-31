package com.fredsonchaves07.moviecatchapi.factories;

import com.fredsonchaves07.moviecatchapi.domain.dto.user.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;

public class UserFactory {

    public static CreateUserDTO createUserDTO() {
        return new CreateUserDTO("User Test", "usertest@email.com", "user@123");
    }

    public static CreateUserDTO createUserDTO(String name, String email, String password) {
        return new CreateUserDTO(name, email, password);
    }

    public static UserDTO userDTO() {
        return new UserDTO(createUser());
    }

    public static UserDTO userDTO(String name, String email) {
        return new UserDTO(name, email);
    }

    public static User createUser() {
        User user = new User("User test", "user@email.com", "user@123");
        return user;
    }

    public static User createUser(String name, String email, String password) {
        User user = new User(name, email, password);
        return user;
    }
}
