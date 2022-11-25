package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.api.services.email.FakeSendMailService;
import com.fredsonchaves07.moviecatchapi.api.services.token.JwtTokenService;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.mail.SendEmailService;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.UserAlreadyConfirmedException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.userDTO;
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
        TokenService tokenService = new JwtTokenService();
        SendEmailService sendEmailService = new FakeSendMailService();
        confirmUserUseCase = new ConfirmUserUseCase(userRepository, tokenService);
        createUserUseCase = new CreateUserUseCase(userRepository, sendEmailService);
    }

    @Test
    public void shouldConfirmUser() {
        userDTO = createUserUseCase.execute(createUserDTO());
        confirmUserUseCase.execute(userDTO);
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
                () -> confirmUserUseCase.execute(userDTO())
        );
    }
}
