package com.fredsonchaves07.moviecatchapi.api.services.authentication;

import com.fredsonchaves07.moviecatchapi.api.exception.BadRequestException;
import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.CreateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    private AuthenticateUserApiService authenticateUserApiService;

    private String token;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        userDTO = createUserUseCase.execute(createUserDTO());
        token = tokenService.encrypt(userDTO).token();
    }

    @Test
    public void shouldAuthenticateUserWithEmailRegisteredValid() {
        String email = "usertest@email.com";
        String password = "user@123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        TokenDTO tokenDTO = authenticateUserApiService.execute(loginDTO);
        String token = tokenService.decrypt(tokenDTO);
        assertNotNull(tokenDTO);
        assertEquals(email, token);
    }

    @Test
    public void notShouldAuthenticateUserWithEmailDoesNotExists() {
        String email = "usertest1@email.com";
        String password = "user@123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        assertThrows(
                BadRequestException.class,
                () -> authenticateUserApiService.execute(loginDTO)
        );
    }

    @Test
    public void notShouldAuthenticateUserIfPasswordDoesNotMatch() {
        String email = "usertest@email.com";
        String password = "user@1243";
        LoginDTO loginDTO = new LoginDTO(email, password);
        assertThrows(
                BadRequestException.class,
                () -> authenticateUserApiService.execute(loginDTO)
        );
    }

    @Test
    public void notShouldAuthenticateUserIfEmailIsNull() {
        String password = "user@1243";
        LoginDTO loginDTO = new LoginDTO(null, password);
        assertThrows(
                BadRequestException.class,
                () -> authenticateUserApiService.execute(loginDTO)
        );
    }

    @Test
    public void notShouldAuthenticateUserIfPasswordIsNull() {
        String email = "usertest@email.com";
        LoginDTO loginDTO = new LoginDTO(email, null);
        assertThrows(
                BadRequestException.class,
                () -> authenticateUserApiService.execute(loginDTO)
        );
    }

    @Test
    public void notShouldAuthenticateIfLoginIsNull() {
        assertThrows(
                BadRequestException.class,
                () -> authenticateUserApiService.execute(null)
        );
    }
}
