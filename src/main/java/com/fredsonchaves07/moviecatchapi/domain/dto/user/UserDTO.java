package com.fredsonchaves07.moviecatchapi.domain.dto.user;

import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import io.swagger.annotations.ApiModel;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return name.equals(userDTO.name) && email.equals(userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}
