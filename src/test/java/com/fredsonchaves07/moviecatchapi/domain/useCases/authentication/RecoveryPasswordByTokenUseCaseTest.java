package com.fredsonchaves07.moviecatchapi.domain.useCases.authentication;

import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.ExpiredTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.InvalidTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UnconfirmedUserException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.ConfirmUserUseCase;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.CreateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.userDTO;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RecoveryPasswordByTokenUseCaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private ConfirmUserUseCase confirmUserUseCase;

    @Autowired
    private RecoveryPasswordByTokenUseCase recoveryPasswordByTokenUseCase;

    private TokenDTO tokenDTO;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        UserDTO userDTO = createUserUseCase.execute(createUserDTO());
        tokenDTO = tokenService.encrypt(Optional.of(userDTO));
        confirmUserUseCase.execute(tokenDTO);
    }

    @Test
    public void shouldRecoveryPasswordByToken() {
        UserDTO userDTO = recoveryPasswordByTokenUseCase.execute(tokenDTO);
        assertNotNull(userDTO);
    }

    @Test
    public void notShouldRecoveryPasswordByTokenIfUserIsNotExist() {
        tokenDTO = tokenService.encrypt(Optional.of(userDTO()));
        assertThrows(
                UserNotFoundException.class,
                () -> recoveryPasswordByTokenUseCase.execute(tokenDTO)
        );
    }

    @Test
    public void notShouldRecoveryPasswordByTokenIfUserNotConfirmed() {
        UserDTO userDTO = createUserUseCase.execute(
                new CreateUserDTO("User not confirmed", "notconfirmed@email.com", "user@123")
        );
        tokenDTO = tokenService.encrypt(Optional.of(userDTO));
        assertThrows(
                UnconfirmedUserException.class,
                () -> recoveryPasswordByTokenUseCase.execute(tokenDTO)
        );
    }

    @Test
    public void notShouldRecoveryPassowordIfTokenIsInvalid() {
        tokenDTO = new TokenDTO("eyJhbGciOiJIUzI1iJ9." +
                "eyJzdWIiOiJ1c2VydGVzdEBlbWFpbC5jb20iLCJpYXQiOjE2ODE5MDk3MzcsImV4cCI6MTY4MTkxNjkzN30." +
                "slb-J8bRZsV_3nu09DkobH0MBTBdF08EWjCYMPK-6jQ");
        assertThrows(
                InvalidTokenException.class,
                () -> recoveryPasswordByTokenUseCase.execute(tokenDTO)
        );
    }

    @Test
    public void notShouldRecoveryPassowordIfTokenIsExpired() {
        tokenDTO = new TokenDTO("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VydGVzdEBlbWFpbC5jb20iLCJleHAiOjE2Njk5NDM3MTgsImlhdCI6MTY2OTk0MzcxOH0." +
                "1-FfzP6NjRA05V5YSBVAc90nji3de9VVk9H8bAQpta64H2BQgHL2NmBJu1pFeh_2EmuDtKhLL4JKldH79Pt8_w");
        assertThrows(
                ExpiredTokenException.class,
                () -> recoveryPasswordByTokenUseCase.execute(tokenDTO)
        );
    }

    @Test
    public void notShouldRecoveryPasswordIfTokenIsNull() {
        assertThrows(
                InvalidTokenException.class,
                () -> recoveryPasswordByTokenUseCase.execute(null)
        );
    }
}
