package com.fredsonchaves07.moviecatchapi.domain.dto.user;

public class CreateUserDTO {

    private String name;
    private String email;
    private String password;

    public CreateUserDTO() {
    }

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
