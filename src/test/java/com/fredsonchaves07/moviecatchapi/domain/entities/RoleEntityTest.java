package com.fredsonchaves07.moviecatchapi.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.fredsonchaves07.moviecatchapi.factories.RoleFactory.createRole;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoleEntityTest {

    private Role existingRole;

    @BeforeEach
    public void setUp() {
        existingRole = createRole();
    }

    @Test
    public void shouldCreateRole() {
        String name = "Test";
        Role role = new Role(name);
        assertNotNull(role);
        assertEquals(name, role.getName());
    }

    @Test
    public void shouldUpdateNameRole() {
        String name = "Update test";
        existingRole.setName(name);
        assertEquals(name, existingRole.getName());
    }
}
