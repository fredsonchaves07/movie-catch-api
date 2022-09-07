package com.fredsonchaves07.moviecatchapi.api.services;

import com.fredsonchaves07.moviecatchapi.api.services.email.FakeSendMailService;
import com.fredsonchaves07.moviecatchapi.api.services.exception.CreateUserUseCaseException;
import com.fredsonchaves07.moviecatchapi.api.services.user.CreateUserAPIService;
import com.fredsonchaves07.moviecatchapi.domain.dto.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.SendEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CreateUserServiceTest {

    @Autowired
    private UserRepository userRepository;

    private CreateUserAPIService userService;

    private SendEmailService sendEmailService;

    @BeforeEach
    public void setUp() {
        sendEmailService = new FakeSendMailService();
        userService = new CreateUserAPIService(userRepository, sendEmailService);
    }

    @Test
    public void shouldCreateUser() {
        CreateUserDTO createUserDTO = createUserDTO();
        UserDTO userDTO = userService.execute(createUserDTO);
        assertNotNull(userDTO);
        assertEquals(userDTO.getName(), createUserDTO.getName());
        assertEquals(userDTO.getEmail(), createUserDTO.getEmail());
    }

    @Test
    public void notShouldCreateUserWithInvalidEmail() {
        String name = "User Test";
        String password = "user@123";
        String email = "user$%@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        assertThrows(
                CreateUserUseCaseException.class,
                () -> userService.execute(createUserDTO),
                "Expected CreateUserUseCaseException"
        );
    }

    @Test
    public void notShouldCreateUserIfPasswordContainSpaceBlank() {
        String name = "User Test";
        String password = "user @123";
        String email = "user@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        assertThrows(
                CreateUserUseCaseException.class,
                () -> userService.execute(createUserDTO),
                "Expected CreateUserUseCaseException"
        );
    }

    @Test
    public void notShouldCreateUserIfPasswordContainLessThan8Characters() {
        String name = "User Test";
        String password = "use@123";
        String email = "user@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        assertThrows(
                CreateUserUseCaseException.class,
                () -> userService.execute(createUserDTO),
                "Expected CreateUserUseCaseException"
        );
    }

    @Test
    public void notShouldCreateUserIfEmailAlreadyExist() {
        CreateUserDTO firstUser = createUserDTO();
        userService.execute(firstUser);
        String name = "User Test";
        String password = "user@123";
        String email = "usertest@email.com";
        CreateUserDTO secondUser = createUserDTO(name, email, password);
        assertThrows(
                CreateUserUseCaseException.class,
                () -> userService.execute(secondUser),
                "Expected CreateUserUseCaseException"
        );
    }
}
