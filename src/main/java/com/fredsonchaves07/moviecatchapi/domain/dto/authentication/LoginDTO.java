package com.fredsonchaves07.moviecatchapi.domain.dto.authentication;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Login")
public final class LoginDTO {

    @ApiModelProperty(required = true)
    private final String email;

    @ApiModelProperty(required = true)
    private final String password;

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
