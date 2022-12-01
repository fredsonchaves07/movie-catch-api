package com.fredsonchaves07.moviecatchapi.api.services.user;

import com.fredsonchaves07.moviecatchapi.api.services.exception.ConfirmUserUseCaseException;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.CreateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.userDTO;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ConfirmUserApiServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmUserApiService confirmUserApiService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    private String token;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        userDTO = createUserUseCase.execute(createUserDTO());
        token = tokenService.encrypt(userDTO).getToken();
    }

    @Test
    public void shouldConfirmUserTest() {
        UserDTO confirmedUser = confirmUserApiService.execute(token);
        assertNotNull(confirmedUser);
        assertEquals(userDTO.getName(), confirmedUser.getName());
        assertEquals(userDTO.getEmail(), confirmedUser.getEmail());
    }

    @Test
    public void notShouldConfirmUserIfUserIsConfirmed() {
        confirmUserApiService.execute(token);
        assertThrows(
                ConfirmUserUseCaseException.class,
                () -> confirmUserApiService.execute(token)
        );
    }

    @Test
    public void notShoulConfirmUserIfUserIsNotFound() {
        token = tokenService.encrypt(userDTO("usertest2", "usertest2@email.com")).getToken();
        assertThrows(
                ConfirmUserUseCaseException.class,
                () -> confirmUserApiService.execute(token)
        );
    }

    @Test
    public void notShouldConfirmUserIfTokenIsNull() {
        assertThrows(
                ConfirmUserUseCaseException.class,
                () -> confirmUserApiService.execute(null)
        );
    }

    @Test
    public void notShouldConfirmUserWithExpiredToken() {
        String expiredToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6MTY2OTQ3NDkwOSwiaWF0IjoxNjY5NDc0OTA5fQ." +
                "bNd5hND3oUbUHLrrBtXZ7w-m6reqyRVyT-TlS92_yoi4zNHH6FdsXppJtQtGhm06LgihSt9PbGjZG4SydpA4mg";
        assertThrows(
                ConfirmUserUseCaseException.class,
                () -> confirmUserApiService.execute(expiredToken)
        );
    }

    @Test
    public void notShouldConfirmUserWithInvalidToken() {
        String invalidToken = "eyJ0eXAiOiJKV1QiLCJhbGciaiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6eTY2OTQ3MzMyM30." +
                "Bb-HcCS0EYGvczEmZgxjnz-_jo9hVDfecmAMLBuSQzgHefBv-EZCKXkb8F8GEmcgvTw6By0fwiwPz8ooz5mwwg";
        assertThrows(
                ConfirmUserUseCaseException.class,
                () -> confirmUserApiService.execute(invalidToken)
        );
    }
}
