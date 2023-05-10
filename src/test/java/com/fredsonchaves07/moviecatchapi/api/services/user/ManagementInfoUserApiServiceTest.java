package com.fredsonchaves07.moviecatchapi.api.services.user;

import com.fredsonchaves07.moviecatchapi.api.exception.UnauthorizedException;
import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UpdateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.authentication.AuthenticateUserUseCase;
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
public class ManagementInfoUserApiServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private ConfirmUserUseCase confirmUserUseCase;

    @Autowired
    private AuthenticateUserUseCase authenticateUserUseCase;

    @Autowired
    private ManagementInfoUserApiService managementInfoUserApiService;

    @Autowired
    private TokenService tokenService;

    private UserDTO userDTO;

    private TokenDTO tokenDTO;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        userDTO = createUserUseCase.execute(createUserDTO());
        tokenDTO = tokenService.encrypt(Optional.of(userDTO));
        confirmUserUseCase.execute(tokenDTO);
    }

    @Test
    public void shouldUpdateUser() {
        String name = "User updated";
        authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", "user@123"));
        UserDTO userUpdated = managementInfoUserApiService.execute(
                new UserDTO(null, "usertest@email.com"), new UpdateUserDTO(name, null));
        assertNotNull(userUpdated);
        assertEquals(userUpdated.getEmail(), userDTO.getEmail());
        assertEquals(userUpdated.getName(), name);
    }

    @Test
    public void shouldAuthenticateUserWithNewPasswordUpdated() {
        String password = "User@45631";
        authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", "user@123"));
        UserDTO userUpdated = managementInfoUserApiService.execute(
                new UserDTO(null, "usertest@email.com"), new UpdateUserDTO(null, password)
        );
        TokenDTO token = authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", password));
        String email = tokenService.decrypt(Optional.ofNullable(token));
        assertNotNull(userUpdated);
        assertNotNull(token);
        assertEquals(email, userDTO.getEmail());
    }

    @Test
    public void shouldUpdateNameAndPasswordUser() {
        String name = "User updated";
        String password = "User@45631";
        authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", "user@123"));
        UserDTO userUpdated = managementInfoUserApiService.execute(
                new UserDTO(null, "usertest@email.com"), new UpdateUserDTO(name, password));
        TokenDTO token = authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", password));
        assertNotNull(userUpdated);
        assertNotNull(token);
        assertEquals(name, userUpdated.getName());
    }

    @Test
    public void notShouldUpdateUserIfNameAndPasswordIsNull() {
        authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", "user@123"));
        UserDTO userUpdated = managementInfoUserApiService.execute(
                new UserDTO(null, "usertest@email.com"), new UpdateUserDTO(null, null));
        TokenDTO token = authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", "user@123"));
        assertNotNull(userUpdated);
        assertNotNull(token);
        assertEquals(userDTO.getName(), userUpdated.getName());
    }

    @Test
    public void notShouldUpdateUserIfUserIsNotAuthenticate() {
        String name = "User updated";
        String password = "User@45631";
        assertThrows(
                UnauthorizedException.class,
                () -> managementInfoUserApiService.execute(
                        new UserDTO(null, "usertest@email.com"), new UpdateUserDTO(name, password)
                ));
    }
}
