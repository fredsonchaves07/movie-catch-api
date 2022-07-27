package com.fredsonchaves07.moviecatchapi.domain.dto.factories;

import com.fredsonchaves07.moviecatchapi.domain.dto.CreateUserDTO;

public class CreateUserDTOFactory {

    public static CreateUserDTO createUserDTO() {
        return new CreateUserDTO("User Test", "usertest@email.com", "user@123");
    }

    public static CreateUserDTO createUserDTO(String name, String email, String password) {
        return new CreateUserDTO(name, email, password);
    }
}
