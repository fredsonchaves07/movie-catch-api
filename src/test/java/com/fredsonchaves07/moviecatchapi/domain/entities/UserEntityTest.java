package com.fredsonchaves07.moviecatchapi.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.fredsonchaves07.moviecatchapi.factories.RoleFactory.createRole;
import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUser;
import static org.junit.jupiter.api.Assertions.*;

public class UserEntityTest {

    private User existingUser;

    private Role existingRole;

    @BeforeEach
    public void setUp() {
        existingUser = createUser();
        existingRole = createRole();
    }

    @Test
    public void shouldCreateUser() {
        String name = "User test";
        String email = "user@email.com";
        String password = "user@123";
        User user = new User(name, email, password);
        assertNotNull(user);
        assertEquals(user.getName(), name);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getPassword(), password);
        assertFalse(user.isConfirm());
        assertNull(user.getRoles());
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

    @Test
    public void shouldUpdateEmailUser() {
        String email = "newEmail@email.com";
        existingUser.setEmail(email);
        assertEquals(existingUser.getEmail(), email);
    }

    @Test
    public void shouldConfirmUser() {
        existingUser.confirmUser();
        assertTrue(existingUser.isConfirm());
    }

    @Test
    public void shouldValidateName() {
        String name = "User test";
        String email = "user@email.com";
        String password = "user@123";
        User user = new User(name, email, password);
        assertTrue(user.isNameValid());
    }

    @Test
    public void notShouldValidateNameIfNameIsNull() {
        String email = "user@email.com";
        String password = "user@123";
        User user = new User(null, email, password);
        assertFalse(user.isNameValid());
    }

    @Test
    public void shouldValidateEmailAndPassword() {
        String name = "User test";
        String email = "user@email.com";
        String password = "user@123";
        User user = new User(name, email, password);
        assertTrue(user.isEmailAndPasswordValid());
    }

    @Test
    public void notShouldValidateEmailIfEmailIsNull() {
        String name = "User test";
        String password = "user@123";
        User user = new User(name, null, password);
        assertFalse(user.isEmailAndPasswordValid());
    }

    @Test
    public void notShouldValidateEmailIfEmailsIsInvalid() {
        String name = "User test";
        String email = "user$%@email.com";
        String password = "user@123";
        User user = new User(name, email, password);
        assertFalse(user.isEmailAndPasswordValid());
    }

    @Test
    public void notShouldValidatePasswordIfPasswordIsNull() {
        String name = "User test";
        String email = "user@email.com";
        User user = new User(name, email, null);
        assertFalse(user.isEmailAndPasswordValid());
    }

    @Test
    public void notShouldValidatePasswordIfPasswordContainSpaceBlank() {
        String name = "User Test";
        String password = "user @123";
        String email = "user@email.com";
        User user = new User(name, email, password);
        assertFalse(user.isEmailAndPasswordValid());
    }

    @Test
    public void notShouldValidateIfPasswordContainLessThan8Characters() {
        String name = "User Test";
        String password = "use@123";
        String email = "user@email.com";
        User user = new User(name, password, email);
        assertFalse(user.isEmailAndPasswordValid());
    }

    @Test
    public void shouldAddRoleUser() {
        existingUser.addRole(existingRole);
        assertTrue(existingUser.containRole(existingRole));
    }

    @Test
    public void shouldGetRolesUser() {
        existingUser.addRole(existingRole);
        assertNotNull(existingUser.getRoles());
        assertEquals(1, existingUser.getRoles().size());
    }
}
