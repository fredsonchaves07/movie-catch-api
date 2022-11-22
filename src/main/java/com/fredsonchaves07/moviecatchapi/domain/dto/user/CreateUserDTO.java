package com.fredsonchaves07.moviecatchapi.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "User request body")
public class CreateUserDTO {

    @ApiModelProperty(required = true)
    private final String name;

    @ApiModelProperty(required = true)
    private final String email;

    @ApiModelProperty(required = true)
    private final String password;

    public CreateUserDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
