package com.fredsonchaves07.moviecatchapi.domain.dto.token;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Token")
public final class TokenDTO {

    private final String token;

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return getToken();
    }
}
