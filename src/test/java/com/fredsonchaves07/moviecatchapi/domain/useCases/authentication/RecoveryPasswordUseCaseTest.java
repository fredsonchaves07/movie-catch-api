package com.fredsonchaves07.moviecatchapi.domain.useCases.authentication;

import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailOrPasswordIncorrectException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailOrPasswordInvalidException;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RecoveryPasswordUseCaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private ConfirmUserUseCase confirmUserUseCase;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RecoveryPasswordUseCase recoveryPasswordUseCase;

    @Autowired
    private AuthenticateUserUseCase authenticateUserUseCase;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        UserDTO userDTO = createUserUseCase.execute(createUserDTO());
        TokenDTO tokenDTO = tokenService.encrypt(Optional.of(userDTO));
        confirmUserUseCase.execute(tokenDTO);
    }

    @Test
    public void shouldRecoveryPasswordWithUserValid() {
        String email = "usertest@email.com";
        String newPassword = "newPassword@123";
        LoginDTO loginDTO = new LoginDTO(email, newPassword);
        UserDTO userDTO = recoveryPasswordUseCase.execute(loginDTO);
        assertNotNull(userDTO);
    }

    @Test
    public void notShouldRecoveryPasswordWithUserNotExists() {
        String email = "usertes@email.com";
        String newPassword = "newPassword@123";
        LoginDTO loginDTO = new LoginDTO(email, newPassword);
        assertThrows(
                UserNotFoundException.class,
                () -> recoveryPasswordUseCase.execute(loginDTO)
        );
    }

    @Test
    public void notShouldRecoveryPasswordIfUserIsNoConfirmed() {
        createUserUseCase.execute(createUserDTO("User test1", "usertest1@email.com", "user@12345"));
        String email = "usertest1@email.com";
        String newPassword = "newPassword@123";
        LoginDTO loginDTO = new LoginDTO(email, newPassword);
        assertThrows(
                UnconfirmedUserException.class,
                () -> recoveryPasswordUseCase.execute(loginDTO)
        );
    }

    @Test
    public void shouldAuthenticateUserWithNewPassword() {
        String email = "usertest@email.com";
        String newPassword = "newPassword@123";
        LoginDTO loginDTO = new LoginDTO(email, newPassword);
        recoveryPasswordUseCase.execute(loginDTO);
        LoginDTO login = new LoginDTO(email, newPassword);
        TokenDTO tokenDTO = authenticateUserUseCase.execute(login);
        assertNotNull(tokenDTO);
    }

    @Test
    public void notShouldAuthenticateUserWithOldPassword() {
        String email = "usertest@email.com";
        String newPassword = "newPassword@123";
        LoginDTO loginDTO = new LoginDTO(email, newPassword);
        recoveryPasswordUseCase.execute(loginDTO);
        LoginDTO login = new LoginDTO(email, "user@123");
        assertThrows(
                EmailOrPasswordIncorrectException.class,
                () -> authenticateUserUseCase.execute(login)
        );
    }

    @Test
    public void notShouldRecoveryPasswordWithInvalidPassword() {
        String email = "usertest@email.com";
        String newPassword = "new";
        LoginDTO loginDTO = new LoginDTO(email, newPassword);
        assertThrows(
                EmailOrPasswordInvalidException.class,
                () -> recoveryPasswordUseCase.execute(loginDTO)
        );
    }

    @Test
    public void notShouldRecoveryPasswordWithEmailIsNull() {
        String newPassword = "new";
        LoginDTO loginDTO = new LoginDTO(null, newPassword);
        assertThrows(
                UserNotFoundException.class,
                () -> recoveryPasswordUseCase.execute(loginDTO)
        );
    }

    @Test
    public void notShouldRecoveryPasswordWithPasswordIsNull() {
        String email = "usertest@email.com";
        LoginDTO loginDTO = new LoginDTO(email, null);
        assertThrows(
                EmailOrPasswordInvalidException.class,
                () -> recoveryPasswordUseCase.execute(loginDTO)
        );
    }

    @Test
    public void notShouldRecoveryPasswordWithRecoveryPasswordDTOIsNull() {
        LoginDTO loginDTO = new LoginDTO(null, null);
        assertThrows(
                UserNotFoundException.class,
                () -> recoveryPasswordUseCase.execute(loginDTO)
        );
    }
}
