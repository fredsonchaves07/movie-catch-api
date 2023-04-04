package com.fredsonchaves07.moviecatchapi.domain.dto.authentication;

public final class RecoveryPasswordDTO {

    private final String email;

    private final String password;

    public RecoveryPasswordDTO(String email, String password) {
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
