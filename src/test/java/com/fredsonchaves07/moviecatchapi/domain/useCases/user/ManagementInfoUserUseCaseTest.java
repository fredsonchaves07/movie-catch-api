package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UpdateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.ExpiredTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.InvalidTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UnconfirmedUserException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.authentication.AuthenticateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ManagementInfoUserUseCaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagementInfoUserUseCase managementInfoUserUseCase;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private ConfirmUserUseCase confirmUserUseCase;

    @Autowired
    private AuthenticateUserUseCase authenticateUserUseCase;

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
    public void shouldUpdateNameUser() {
        String name = "User updated";
        TokenDTO token = authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", "user@123"));
        UserDTO userUpdated = managementInfoUserUseCase.execute(token, new UpdateUserDTO(name, null));
        assertNotNull(userUpdated);
        assertEquals(userUpdated.getEmail(), userDTO.getEmail());
        assertEquals(userUpdated.getName(), name);
    }

    @Test
    public void shouldAuthenticateUserWithNewPasswordUpdated() {
        String password = "User@45631";
        TokenDTO token = authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", "user@123"));
        UserDTO userUpdated = managementInfoUserUseCase.execute(token, new UpdateUserDTO(null, password));
        token = authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", password));
        String email = tokenService.decrypt(Optional.ofNullable(token));
        assertNotNull(userUpdated);
        assertNotNull(token);
        assertEquals(email, userDTO.getEmail());
    }

    @Test
    public void shouldUpdateNameAndPasswordUser() {
        String name = "User updated";
        String password = "User@45631";
        TokenDTO token = authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", "user@123"));
        UserDTO userUpdated = managementInfoUserUseCase.execute(token, new UpdateUserDTO(name, password));
        token = authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", password));
        assertNotNull(userUpdated);
        assertNotNull(token);
        assertEquals(name, userUpdated.getName());
    }

    @Test
    public void notShouldUpdateUserIfNameAndPasswordIsNull() {
        TokenDTO token = authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", "user@123"));
        UserDTO userUpdated = managementInfoUserUseCase.execute(token, new UpdateUserDTO(null, null));
        token = authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", "user@123"));
        assertNotNull(userUpdated);
        assertNotNull(token);
        assertEquals(userDTO.getName(), userUpdated.getName());
    }

    @Test
    public void notShouldUpdateUserIfUserIsNotExists() {
        String name = "User updated";
        String password = "User@45631";
        TokenDTO token = tokenService.encrypt(Optional.of(new UserDTO(
                "usernotexist", "usernotexist@email.com")));
        assertThrows(
                UserNotFoundException.class,
                () -> managementInfoUserUseCase.execute(token, new UpdateUserDTO(name, password))
        );
    }

    @Test
    public void notShouldUpdateUserIfUserIsNotConfirmed() {
        String name = "User updated";
        String password = "User@45631";
        UserDTO user = createUserUseCase.execute(
                new CreateUserDTO("usernotconfirmed", "usernotconfirmed@email.com", "user@123")
        );
        TokenDTO token = tokenService.encrypt(Optional.of(user));
        assertThrows(
                UnconfirmedUserException.class,
                () -> managementInfoUserUseCase.execute(token, new UpdateUserDTO(name, password))
        );
    }

    @Test
    public void notShouldUpdateUserIfTokensIsNull() {
        String name = "User updated";
        String password = "User@45631";
        assertThrows(
                InvalidTokenException.class,
                () -> managementInfoUserUseCase.execute(null, new UpdateUserDTO(name, password))
        );
    }

    @Test
    public void notShouldUpdateUserWithExpiredToken() {
        String name = "User updated";
        String password = "User@45631";
        TokenDTO expiredToken = new TokenDTO("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VydGVzdEBlbWFpbC5jb20iLCJleHAiOjE2Njk5NDM3MTgsImlhdCI6MTY2OTk0MzcxOH0." +
                "1-FfzP6NjRA05V5YSBVAc90nji3de9VVk9H8bAQpta64H2BQgHL2NmBJu1pFeh_2EmuDtKhLL4JKldH79Pt8_w");
        assertThrows(
                ExpiredTokenException.class,
                () -> managementInfoUserUseCase.execute(expiredToken, new UpdateUserDTO(name, password))
        );
    }

    @Test
    public void notShouldUpdateUserWithInvalidToken() {
        String name = "User updated";
        String password = "User@45631";
        TokenDTO invalidToken = new TokenDTO("eyJ0eXAiOiJKV1QiLCJhbGciaiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6eTY2OTQ3MzMyM30." +
                "Bb-HcCS0EYGvczEmZgxjnz-_jo9hVDfecmAMLBuSQzgHefBv-EZCKXkb8F8GEmcgvTw6By0fwiwPz8ooz5mwwg");
        assertThrows(
                InvalidTokenException.class,
                () -> managementInfoUserUseCase.execute(invalidToken, new UpdateUserDTO(name, password))
        );
    }
}
