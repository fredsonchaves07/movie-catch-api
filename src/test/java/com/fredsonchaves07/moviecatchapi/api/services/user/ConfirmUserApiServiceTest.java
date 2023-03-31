package com.fredsonchaves07.moviecatchapi.api.services.user;

import com.fredsonchaves07.moviecatchapi.api.exception.ApiExpiredTokenException;
import com.fredsonchaves07.moviecatchapi.api.exception.ApiInvalidTokenException;
import com.fredsonchaves07.moviecatchapi.api.exception.BadRequestException;
import com.fredsonchaves07.moviecatchapi.api.exception.ResourceNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.CreateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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
        token = tokenService.encrypt(Optional.of(userDTO)).getToken();
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
                BadRequestException.class,
                () -> confirmUserApiService.execute(token)
        );
    }

    @Test
    public void notShoulConfirmUserIfUserIsNotFound() {
        token = tokenService.encrypt(Optional.of(userDTO("usertest2", "usertest2@email.com"))).getToken();
        assertThrows(
                ResourceNotFoundException.class,
                () -> confirmUserApiService.execute(token)
        );
    }

    @Test
    public void notShouldConfirmUserIfTokenIsNull() {
        assertThrows(
                ApiInvalidTokenException.class,
                () -> confirmUserApiService.execute(null)
        );
    }

    @Test
    public void notShouldConfirmUserWithExpiredToken() {
        String expiredToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6MTY2OTk0MjY5NiwiaWF0IjoxNjY5OTQyNjk2fQ." +
                "oC3X-neXhHqxbc1hN09ctA0P2cLG5eMVDdugg50xpjBZg6Fp2oQJdjKc08WYvDjCJzA-1k6XdlqNMx1Vq3rrVw";
        assertThrows(
                ApiExpiredTokenException.class,
                () -> confirmUserApiService.execute(expiredToken)
        );
    }

    @Test
    public void notShouldConfirmUserWithInvalidToken() {
        String invalidToken = "eyJ0eXAiOiJKV1QiLCJhbGciaiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6eTY2OTQ3MzMyM30." +
                "Bb-HcCS0EYGvczEmZgxjnz-_jo9hVDfecmAMLBuSQzgHefBv-EZCKXkb8F8GEmcgvTw6By0fwiwPz8ooz5mwwg";
        assertThrows(
                ApiInvalidTokenException.class,
                () -> confirmUserApiService.execute(invalidToken)
        );
    }
}
