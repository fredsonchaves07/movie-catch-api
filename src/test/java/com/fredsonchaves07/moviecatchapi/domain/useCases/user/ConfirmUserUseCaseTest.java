package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.ExpiredTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.InvalidTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserAlreadyConfirmedException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.userDTO;
import static org.junit.jupiter.api.Assertions.*;

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

    private TokenDTO tokenDTO;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        userDTO = createUserUseCase.execute(createUserDTO());
        tokenDTO = tokenService.encrypt(Optional.of(userDTO));
    }

    @Test
    public void shouldConfirmUser() {
        UserDTO confirmedUser = confirmUserUseCase.execute(tokenDTO);
        assertNotNull(confirmedUser);
        assertEquals(userDTO.getName(), confirmedUser.getName());
        assertEquals(userDTO.getEmail(), confirmedUser.getEmail());
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
        tokenDTO = tokenService.encrypt(Optional.of(userDTO("usertest2", "usertest2@email.com")));
        assertThrows(
                UserNotFoundException.class,
                () -> confirmUserUseCase.execute(tokenDTO)
        );
    }

    @Test
    public void notShouldConfirmUserIfTokenIsNull() {
        assertThrows(
                InvalidTokenException.class,
                () -> confirmUserUseCase.execute(null)
        );
    }

    @Test
    public void notShouldConfirmUserWithExpiredToken() {
        TokenDTO expiredToken = new TokenDTO("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VydGVzdEBlbWFpbC5jb20iLCJleHAiOjE2Njk5NDM3MTgsImlhdCI6MTY2OTk0MzcxOH0." +
                "1-FfzP6NjRA05V5YSBVAc90nji3de9VVk9H8bAQpta64H2BQgHL2NmBJu1pFeh_2EmuDtKhLL4JKldH79Pt8_w");
        assertThrows(
                ExpiredTokenException.class,
                () -> confirmUserUseCase.execute(expiredToken)
        );
    }

    @Test
    public void notShouldConfirmUserWithInvalidToken() {
        TokenDTO invalidToken = new TokenDTO("eyJ0eXAiOiJKV1QiLCJhbGciaiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6eTY2OTQ3MzMyM30." +
                "Bb-HcCS0EYGvczEmZgxjnz-_jo9hVDfecmAMLBuSQzgHefBv-EZCKXkb8F8GEmcgvTw6By0fwiwPz8ooz5mwwg");
        assertThrows(
                InvalidTokenException.class,
                () -> confirmUserUseCase.execute(invalidToken)
        );
    }
}
