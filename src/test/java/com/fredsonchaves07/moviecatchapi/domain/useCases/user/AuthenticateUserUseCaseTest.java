package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.useCases.authentication.AuthenticateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        String name = "User Test";
        String email = "usertest@email.com";
        String password = "user@123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        UserDTO userDTO = authenticateUserUseCase.execute(loginDTO);
        assertNotNull(userDTO);
        assertEquals(userDTO.getName(), name);
        assertEquals(userDTO.getEmail(), email);
    }
}
