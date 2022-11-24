package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.api.services.email.FakeSendMailService;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.mail.SendEmailService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.UserAlreadyConfirmedException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ConfirmUserUseCaseTest {

    @Autowired
    private UserRepository userRepository;

    private ConfirmUserUseCase confirmUserUseCase;

    private CreateUserUseCase createUserUseCase;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        SendEmailService sendEmailService = new FakeSendMailService();
        confirmUserUseCase = new ConfirmUserUseCase(userRepository);
        createUserUseCase = new CreateUserUseCase(userRepository, sendEmailService);
    }

    @Test
    public void shouldConfirmUser() {
        createUserUseCase.execute(createUserDTO());
        assertTrue(true);
    }

    @Test
    public void notShouldConfirmUserIfUserIsConfirmed() {
        userDTO = createUserUseCase.execute(createUserDTO());
        confirmUserUseCase.execute(userDTO);
        assertThrows(
                UserAlreadyConfirmedException.class,
                () -> confirmUserUseCase.execute(userDTO)
        );
    }

    @Test
    public void notShoulConfirmUserIfUserIsNotFound() {
        assertThrows(
                UserNotFoundException.class,
                () -> confirmUserUseCase.execute(new UserDTO("User Test", "user@email.com"))
        );
    }
}
