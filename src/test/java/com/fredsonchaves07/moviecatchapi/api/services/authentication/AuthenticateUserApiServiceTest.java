package com.fredsonchaves07.moviecatchapi.api.services.authentication;

import com.fredsonchaves07.moviecatchapi.api.exception.UnauthorizedException;
import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthenticateUserApiServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private ConfirmUserUseCase confirmUserUseCase;

    @Autowired
    private AuthenticateUserApiService authenticateUserApiService;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        UserDTO userDTO = createUserUseCase.execute(createUserDTO());
        TokenDTO tokenDTO = tokenService.encrypt(Optional.of(userDTO));
        confirmUserUseCase.execute(tokenDTO);
    }

    @Test
    public void shouldAuthenticateUserWithEmailRegisteredValid() {
        String email = "usertest@email.com";
        String password = "user@123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        TokenDTO tokenDTO = authenticateUserApiService.execute(loginDTO);
        String token = tokenService.decrypt(Optional.of(tokenDTO));
        assertNotNull(tokenDTO);
        assertEquals(email, token);
    }

    @Test
    public void notShouldAuthenticateUserWithEmailDoesNotExists() {
        String email = "usertest1@email.com";
        String password = "user@123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        assertThrows(
                UnauthorizedException.class,
                () -> authenticateUserApiService.execute(loginDTO)
        );
    }

    @Test
    public void notShouldAuthenticateUserIfPasswordDoesNotMatch() {
        String email = "usertest@email.com";
        String password = "user@1243";
        LoginDTO loginDTO = new LoginDTO(email, password);
        assertThrows(
                UnauthorizedException.class,
                () -> authenticateUserApiService.execute(loginDTO)
        );
    }

    @Test
    public void notShouldAuthenticateUserIfEmailIsNull() {
        String password = "user@1243";
        LoginDTO loginDTO = new LoginDTO(null, password);
        assertThrows(
                UnauthorizedException.class,
                () -> authenticateUserApiService.execute(loginDTO)
        );
    }

    @Test
    public void notShouldAuthenticateUserIfPasswordIsNull() {
        String email = "usertest@email.com";
        LoginDTO loginDTO = new LoginDTO(email, null);
        assertThrows(
                UnauthorizedException.class,
                () -> authenticateUserApiService.execute(loginDTO)
        );
    }

    @Test
    public void notShouldAuthenticateIfLoginIsNull() {
        assertThrows(
                UnauthorizedException.class,
                () -> authenticateUserApiService.execute(null)
        );
    }

    @Test
    public void notShouldAuthenticateUserIfUserIsNoConfirmed() {
        createUserUseCase.execute(createUserDTO(
                "User not confirmed", "usertnotconfirmed@email.com", "user@123")
        );
        String email = "usertnotconfirmed@email.com";
        String password = "user@123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        assertThrows(
                UnauthorizedException.class,
                () -> authenticateUserApiService.execute(loginDTO)
        );
    }
}
