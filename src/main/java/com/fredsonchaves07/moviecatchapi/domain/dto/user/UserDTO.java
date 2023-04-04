package com.fredsonchaves07.moviecatchapi.domain.dto.user;

import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "User response body")
public final class UserDTO {

    private final String name;

    private final String email;

    public UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public UserDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
