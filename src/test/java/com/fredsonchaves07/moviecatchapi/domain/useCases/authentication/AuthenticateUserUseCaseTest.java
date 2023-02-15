package com.fredsonchaves07.moviecatchapi.domain.useCases.authentication;

import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailOrPasswordInvalidException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.CreateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AuthenticateUserUseCaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private AuthenticateUserUseCase authenticateUserUseCase;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        createUserUseCase.execute(createUserDTO());
    }

    @Test
    public void shouldAuthenticateUserWithEmailRegisteredValid() {
        String email = "usertest@email.com";
        String password = "user@123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        TokenDTO tokenDTO = authenticateUserUseCase.execute(loginDTO);
        assertNotNull(tokenDTO);
    }

    @Test
    public void notShouldAuthenticateUserWithEmailDoesNotExists() {
        String email = "usertest1@email.com";
        String password = "user@123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        assertThrows(
                EmailOrPasswordInvalidException.class,
                () -> authenticateUserUseCase.execute(loginDTO)
        );
    }

    @Test
    public void notShouldAuthenticateUserIfPasswordDoesNotMatch() {
        String email = "usertest@email.com";
        String password = "user@1243";
        LoginDTO loginDTO = new LoginDTO(email, password);
        assertThrows(
                EmailOrPasswordInvalidException.class,
                () -> authenticateUserUseCase.execute(loginDTO)
        );
    }
}
