package com.fredsonchaves07.moviecatchapi.domain.repository;

import com.fredsonchaves07.moviecatchapi.domain.entities.ListMovieUser;
import com.fredsonchaves07.moviecatchapi.domain.entities.Role;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.entities.records.MovieSeries;
import com.fredsonchaves07.moviecatchapi.domain.repositories.RoleRepository;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        User user = userRepository.findByEmail(email).orElseThrow();
        assertNotNull(user);
        assertEquals(user.getName(), newUser.getName());
        assertEquals(user.getEmail(), newUser.getEmail());
        assertEquals(user.getPassword(), newUser.getPassword());
        assertNotNull(user.getRoles());
        assertTrue(user.containRole(existingRole));
        assertFalse(user.isConfirm());
        assertEquals(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy:hh:mm")),
                user.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy:hh:mm"))
        );
        assertEquals(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy:hh:mm")),
                user.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy:hh:mm"))
        );
    }

    @Test
    public void shouldUpdateNameUser() {
        String name = "User update name";
        User user = userRepository.findByEmail(persitUser.getEmail()).orElseThrow();
        user.setName(name);
        userRepository.save(user);
        assertEquals(user.getName(), name);
    }

    @Test
    public void shouldUpdatePasswordUser() {
        String password = "newPassword@123";
        User user = userRepository.findByEmail(persitUser.getEmail()).orElseThrow();
        user.setPassword(password);
        userRepository.save(user);
        assertEquals(user.getPassword(), password);
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
        User user = userRepository.findByEmail(persitUser.getEmail()).orElseThrow();
        user.confirmUser();
        userRepository.save(user);
        user = userRepository.findByEmail(persitUser.getEmail()).orElseThrow();
        assertTrue(user.isConfirm());
    }

    @Test
    public void shouldAddRoleUser() {
        persitUser.addRole(existingRole);
        User user = userRepository.save(persitUser);
        assertNotNull(user.getRoles());
        assertTrue(user.containRole(existingRole));
    }

    @Test
    public void addUserListMovieSeries() {
        String name = "List movie user 1";
        String description = "List movie user 1 description";
        List<MovieSeries> moviesList = List.of(
                new MovieSeries("1", "Movie 1", "urlImageMovie1"),
                new MovieSeries("2", "Serie 1", "urlImageMSerie1")
        );
        ListMovieUser listMovieUser = new ListMovieUser(name, description, existingUser, moviesList);
        assertFalse(persitUser.getListMovieUser().isEmpty());
        assertNotNull(persitUser.getListMovieUser());
        assertEquals(listMovieUser.getName(), persitUser.getListMovieUser().get(0).getName());
        assertEquals(listMovieUser.getDescription(), persitUser.getListMovieUser().get(0).getDescription());
        assertEquals("1", persitUser.getListMovieUser().get(0).getMoviesList().get(0).id());
        assertEquals("Movie 1", persitUser.getListMovieUser().get(0).getMoviesList().get(0).name());
        assertEquals("urlImageMovie1", persitUser.getListMovieUser().get(0).getMoviesList().get(0).urlImage());
        assertEquals("2", persitUser.getListMovieUser().get(0).getMoviesList().get(1).id());
        assertEquals("Serie 1", persitUser.getListMovieUser().get(0).getMoviesList().get(1).name());
        assertEquals("urlImageMSerie1", persitUser.getListMovieUser().get(0).getMoviesList().get(1).urlImage());
    }

    @Test
    public void addMultipleListInMovieSeries() {
        ListMovieUser listMovieUser1 = new ListMovieUser(
                "List movie user 1", "List movie user 1 description", existingUser, List.of(
                new MovieSeries("1", "Movie 1", "urlImageMovie1"),
                new MovieSeries("2", "Serie 1", "urlImageSerie1")
        ));
        ListMovieUser listMovieUser2 = new ListMovieUser(
                "List movie user 2", "List movie user 2 description", existingUser, List.of(
                new MovieSeries("6", "Serie 6", "urlImageSerie6"),
                new MovieSeries("10", "Movie 10", "urlImageMovie10")
        ));
        ListMovieUser listMovieUser3 = new ListMovieUser(
                "List movie user 3", "List movie user 3 description", existingUser, List.of(
                new MovieSeries("2", "Serie 1", "urlImageSerie1"),
                new MovieSeries("1", "Movie 1", "urlImageMovie1")
        ));
        assertNotNull(persitUser.getListMovieUser());
        assertFalse(persitUser.getListMovieUser().isEmpty());
        assertEquals(3, persitUser.getListMovieUser().size());
        assertEquals(listMovieUser1, persitUser.getListMovieUser().get(0));
        assertEquals(listMovieUser2, persitUser.getListMovieUser().get(1));
        assertEquals(listMovieUser3, persitUser.getListMovieUser().get(2));
    }
}