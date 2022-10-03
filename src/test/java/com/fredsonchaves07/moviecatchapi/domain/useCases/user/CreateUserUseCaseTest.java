package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.api.services.email.FakeSendMailService;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.mail.SendEmailService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.CreateUserUseCase;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.EmailAlreadyExistException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.EmailOrPasswordInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CreateUserUseCaseTest {

    @Autowired
    private UserRepository userRepository;

    private SendEmailService sendEmailService;

    private CreateUserUseCase createUserUseCase;

    @BeforeEach
    public void setUp() {
        sendEmailService = new FakeSendMailService();
        createUserUseCase = new CreateUserUseCase(userRepository, sendEmailService);
    }

    @Test
    public void shouldCreateUser() {
        String name = "User Test";
        String email = "user@email.com";
        String password = "user@123";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        UserDTO userDTO = createUserUseCase.execute(createUserDTO);
        assertNotNull(userDTO);
        assertEquals(userDTO.getName(), name);
        assertEquals(userDTO.getEmail(), email);
    }

    @Test
    public void notShouldCreateUserWithInvalidEmail() {
        String name = "User Test";
        String password = "user@123";
        String email = "user$%@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        assertThrows(
                EmailOrPasswordInvalidException.class,
                () -> createUserUseCase.execute(createUserDTO),
                "Expected EmailOrPasswordInvalid"
        );
    }

    @Test
    public void notShouldCreateUserIfPasswordContainSpaceBlank() {
        String name = "User Test";
        String password = "user @123";
        String email = "user@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        assertThrows(
                EmailOrPasswordInvalidException.class,
                () -> createUserUseCase.execute(createUserDTO),
                "Expected EmailOrPasswordInvalid"
        );
    }

    @Test
    public void notShouldCreateUserIfPasswordContainLessThan8Characters() {
        String name = "User Test";
        String password = "use@123";
        String email = "user@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        assertThrows(
                EmailOrPasswordInvalidException.class,
                () -> createUserUseCase.execute(createUserDTO),
                "Expected EmailOrPasswordInvalid"
        );
    }

    @Test
    public void notShouldCreateUserIfEmailAlreadyExist() {
        CreateUserDTO firstUser = createUserDTO();
        createUserUseCase.execute(firstUser);
        String name = "User Test";
        String password = "user@123";
        String email = "usertest@email.com";
        CreateUserDTO secondUser = createUserDTO(name, email, password);
        assertThrows(
                EmailAlreadyExistException.class,
                () -> createUserUseCase.execute(secondUser),
                "Expected EmailAlreadyExist"
        );
    }

}
