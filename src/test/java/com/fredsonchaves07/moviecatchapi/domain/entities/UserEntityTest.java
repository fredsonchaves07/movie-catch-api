package com.fredsonchaves07.moviecatchapi.domain.entities;

import com.fredsonchaves07.moviecatchapi.domain.entities.factories.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserEntityTest {

    private User existingUser;

    @BeforeEach
    public void setUp() {
        existingUser = UserFactory.createUser();
    }

    @Test
    public void shouldCreateANewUser() {
        String name = "User test";
        String email = "user@email.com";
        String password = "user@123";
        User user = new User(name, email, password);
        assertNotNull(user);
        assertEquals(user.getName(), name);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getPassword(), password);
    }

    @Test
    public void shouldUpdateNameUser() {
        String name = "User update name";
        existingUser.setName(name);
        assertEquals(existingUser.getName(), name);
    }

    @Test
    public void shouldUpdatePasswordUser() {
        String password = "newPassword@123";
        existingUser.setPassword(password);
        assertEquals(existingUser.getPassword(), password);
    }
}
