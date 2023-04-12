package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.EmailDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ChangeRequestPasswordUserUseCaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private ConfirmUserUseCase confirmUserUseCase;

    @Autowired
    private ChangeRequestPasswordUserUseCase changeRequestPasswordUserUseCase;

    @Autowired
    private TokenService tokenService;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        UserDTO userDTO = createUserUseCase.execute(createUserDTO());
        TokenDTO tokenDTO = tokenService.encrypt(Optional.of(userDTO));
        confirmUserUseCase.execute(tokenDTO);
    }

    @Test
    public void shouldRequestChangeUser() {
        String email = "usertest@email.com";
        changeRequestPasswordUserUseCase.execute(new EmailDTO(email));
        assertTrue(true);
    }

    @Test
    public void shouldReconfirmUserIfUserIsNotConfirmed() {
        createUserUseCase.execute(createUserDTO(
                "User not confirmed", "usertnotconfirmed@email.com", "user@123")
        );
        String email = "usertnotconfirmed@email.com";
        changeRequestPasswordUserUseCase.execute(new EmailDTO(email));
        assertTrue(true);
    }

    @Test
    public void notShouldRequestChangeUserIfUseDoesNotExist() {
        String email = "usertnotexist@email.com";
        assertThrows(
                UserNotFoundException.class,
                () -> changeRequestPasswordUserUseCase.execute(new EmailDTO(email))
        );
    }

    @Test
    public void notShouldRequestChangeUserIfEmailIsNull() {
        assertThrows(
                UserNotFoundException.class,
                () -> changeRequestPasswordUserUseCase.execute(null)
        );
    }
}
