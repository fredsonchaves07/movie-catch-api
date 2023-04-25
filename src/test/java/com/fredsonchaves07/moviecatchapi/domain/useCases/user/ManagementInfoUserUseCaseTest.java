package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UpdateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.authentication.AuthenticateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        UserDTO userUpdated = managementInfoUserUseCase.execute(
                token, new UpdateUserDTO(name, null)
        );
        assertNotNull(userUpdated);
        assertEquals(userUpdated.getEmail(), userDTO.getEmail());
        assertEquals(userUpdated.getName(), name);
    }

    @Test
    public void shouldAuthenticateUserWithNewPasswordUpdated() {
        String password = "User@45631";
        TokenDTO token = authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", "user@123"));
        UserDTO userUpdated = managementInfoUserUseCase.execute(
                token, new UpdateUserDTO(null, password)
        );
        token = authenticateUserUseCase.execute(new LoginDTO("usertest@email.com", password));
        String email = tokenService.decrypt(Optional.ofNullable(token));
        assertNotNull(userUpdated);
        assertNotNull(token);
        assertEquals(userDTO.getEmail(), email);
    }
}
