package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.TokenExpiredOrInvalidException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.UserAlreadyConfirmedException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.userDTO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ConfirmUserUseCaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmUserUseCase confirmUserUseCase;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private TokenService tokenService;

    private UserDTO userDTO;

    private TokenDTO tokenDTO;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        userDTO = createUserUseCase.execute(createUserDTO());
        tokenDTO = tokenService.encrypt(userDTO);
    }

    @Test
    public void shouldConfirmUser() {
        confirmUserUseCase.execute(tokenDTO);
        assertTrue(true);
    }

    @Test
    public void notShouldConfirmUserIfUserIsConfirmed() {
        confirmUserUseCase.execute(tokenDTO);
        assertThrows(
                UserAlreadyConfirmedException.class,
                () -> confirmUserUseCase.execute(tokenDTO)
        );
    }

    @Test
    public void notShoulConfirmUserIfUserIsNotFound() {
        tokenDTO = tokenService.encrypt(userDTO("usertest2", "usertest2@email.com"));
        assertThrows(
                UserNotFoundException.class,
                () -> confirmUserUseCase.execute(tokenDTO)
        );
    }

    @Test
    public void notShouldConfirmUserIfTokenIsNull() {
        assertThrows(
                TokenExpiredOrInvalidException.class,
                () -> confirmUserUseCase.execute(null)
        );
    }

    @Test
    public void notShouldConfirmUserWithExpiredToken() {
        TokenDTO expiredToken = new TokenDTO("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6MTY2OTQ3NDkwOSwiaWF0IjoxNjY5NDc0OTA5fQ." +
                "bNd5hND3oUbUHLrrBtXZ7w-m6reqyRVyT-TlS92_yoi4zNHH6FdsXppJtQtGhm06LgihSt9PbGjZG4SydpA4mg");
        assertThrows(
                TokenExpiredOrInvalidException.class,
                () -> confirmUserUseCase.execute(expiredToken)
        );
    }

    @Test
    public void notShouldConfirmUserWithInvalidToken() {
        TokenDTO invalidToken = new TokenDTO("eyJ0eXAiOiJKV1QiLCJhbGciaiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6eTY2OTQ3MzMyM30." +
                "Bb-HcCS0EYGvczEmZgxjnz-_jo9hVDfecmAMLBuSQzgHefBv-EZCKXkb8F8GEmcgvTw6By0fwiwPz8ooz5mwwg");
        assertThrows(
                TokenExpiredOrInvalidException.class,
                () -> confirmUserUseCase.execute(invalidToken)
        );
    }
}
