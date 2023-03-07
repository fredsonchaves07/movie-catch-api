package com.fredsonchaves07.moviecatchapi.factories;

import com.fredsonchaves07.moviecatchapi.domain.entities.Role;

public class RoleFactory {

    public static Role createRole() {
        return new Role("Test");
    }

    public static Role createRole(String name) {
        return new Role(name);
    }
}
