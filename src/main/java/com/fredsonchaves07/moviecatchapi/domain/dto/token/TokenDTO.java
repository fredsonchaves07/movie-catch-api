package com.fredsonchaves07.moviecatchapi.domain.dto.token;

public class TokenDTO {

    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
