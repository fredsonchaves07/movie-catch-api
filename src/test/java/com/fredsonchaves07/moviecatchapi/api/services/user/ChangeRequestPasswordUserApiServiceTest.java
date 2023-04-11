package com.fredsonchaves07.moviecatchapi.api.services.user;

import com.fredsonchaves07.moviecatchapi.api.exception.BadRequestException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ChangeRequestPasswordUserApiServiceTest {

    @Autowired
    private ChangeRequestPasswordUserApiService changeRequestPasswordUserApiService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private ConfirmUserUseCase confirmUserUseCase;

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
        changeRequestPasswordUserApiService.execute(email);
        assertTrue(true);
    }

    @Test
    public void shouldReconfirmUserIfUserIsNotConfirmed() {
        createUserUseCase.execute(createUserDTO(
                "User not confirmed", "usertnotconfirmed@email.com", "user@123")
        );
        String email = "usertnotconfirmed@email.com";
        changeRequestPasswordUserApiService.execute(email);
        assertTrue(true);
    }

    @Test
    public void notShouldRequestChangeUserIfUseDoesNotExist() {
        String email = "usertnotexist@email.com";
        assertThrows(
                BadRequestException.class,
                () -> changeRequestPasswordUserApiService.execute(email)
        );
    }

    @Test
    public void notShouldRequestChangeUserIfEmailIsNull() {
        assertThrows(
                BadRequestException.class,
                () -> changeRequestPasswordUserApiService.execute(null)
        );
    }
}
