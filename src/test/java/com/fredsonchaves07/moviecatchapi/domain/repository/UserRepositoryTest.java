package com.fredsonchaves07.moviecatchapi.domain.repository;

import com.fredsonchaves07.moviecatchapi.domain.entities.Role;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.repositories.RoleRepository;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static com.fredsonchaves07.moviecatchapi.factories.RoleFactory.createRole;
import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUser;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User existingUser;

    private User persitUser;

    private Role existingRole;

    private Role persitRole;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        existingUser = createUser();
        existingRole = createRole();
        persitRole = roleRepository.save(existingRole);
        existingUser.addRole(persitRole);
        persitUser = userRepository.save(existingUser);
    }

    @Test
    public void shouldCreateUser() {
        String name = "User test";
        String email = "user1@email.com";
        String password = "user@123";
        User newUser = new User(name, email, password, persitRole);
        userRepository.save(newUser);
        User user = userRepository.findByEmail(email);
        assertNotNull(user);
        assertEquals(user.getName(), newUser.getName());
        assertEquals(user.getEmail(), newUser.getEmail());
        assertEquals(user.getPassword(), newUser.getPassword());
        assertNotNull(user.getRoles());
        assertTrue(user.containRole(existingRole));
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
        assertFalse(user.isConfirm());
    }

    @Test
    public void shouldUpdateNameUser() {
        String name = "User update name";
        User user = userRepository.findByEmail(persitUser.getEmail());
        user.setName(name);
        userRepository.save(user);
        assertEquals(user.getName(), name);
    }

    @Test
    public void shouldUpdatePasswordUser() {
        String password = "newPassword@123";
        User user = userRepository.findByEmail(persitUser.getEmail());
        user.setPassword(password);
        userRepository.save(user);
        assertEquals(user.getPassword(), password);
    }

    @Test
    public void shouldUpdateEmailUser() {
        String email = "newEmail@email.com";
        User user = userRepository.findByEmail(persitUser.getEmail());
        user.setEmail(email);
        userRepository.save(user);
        assertEquals(user.getEmail(), email);
    }

    @Test
    public void notShouldCreateUserIfNameIsNull() {
        String email = "user@email.com";
        String password = "user@123";
        User newUser = new User(null, email, password, existingRole);
        assertThrows(
                DataIntegrityViolationException.class,
                () -> userRepository.save(newUser)
        );
    }

    @Test
    public void notShouldCreateUserIfPasswordIsNull() {
        String name = "User test";
        String email = "user@email.com";
        User newUser = new User(name, email, null, existingRole);
        assertThrows(
                DataIntegrityViolationException.class,
                () -> userRepository.save(newUser)
        );
    }

    @Test
    public void notShouldCreateUserIfEmailIsNull() {
        String name = "User test";
        String password = "user@123";
        User newUser = new User(name, null, password, existingRole);
        assertThrows(
                DataIntegrityViolationException.class,
                () -> userRepository.save(newUser)
        );
    }

    @Test
    public void notShouldCreateUserIfEmailAlreadyExist() {
        String name = "User test";
        String email = "user@email.com";
        String password = "user@123";
        User newUser = new User(name, email, password, existingRole);
        assertThrows(
                DataIntegrityViolationException.class,
                () -> userRepository.saveAndFlush(newUser)
        );
    }

    @Test
    public void shouldConfirmUser() {
        User user = userRepository.findByEmail(persitUser.getEmail());
        user.confirmUser();
        userRepository.save(user);
        user = userRepository.findByEmail(persitUser.getEmail());
        assertTrue(user.isConfirm());
    }

    @Test
    public void shouldAddRoleUser() {
        persitUser.addRole(existingRole);
        User user = userRepository.save(persitUser);
        assertNotNull(user.getRoles());
        assertTrue(user.containRole(existingRole));
    }
}