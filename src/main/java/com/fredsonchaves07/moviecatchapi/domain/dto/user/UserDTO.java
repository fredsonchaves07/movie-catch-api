package com.fredsonchaves07.moviecatchapi.domain.dto.user;

import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "User response body")
public class UserDTO {

    private String name;
    private String email;

    public UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
