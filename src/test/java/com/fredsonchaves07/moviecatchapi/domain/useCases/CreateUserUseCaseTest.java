package com.fredsonchaves07.moviecatchapi.domain.useCases;

import com.fredsonchaves07.moviecatchapi.domain.dto.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.EmailOrPasswordInvalid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CreateUserUseCaseTest {

    @Autowired
    UserRepository userRepository;

    CreateUserUseCase createUserUseCase;

    @BeforeEach
    public void setUp() {
        createUserUseCase = new CreateUserUseCase(userRepository);
    }

    @Test
    public void shouldCreateUserByUseCase() {
        String name = "User Test";
        String email = "user@email.com";
        String password = "user@123";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        UserDTO userDTO = createUserUseCase.execute(createUserDTO);
        assertNotNull(userDTO);
        assertEquals(userDTO.getName(), name);
        assertEquals(userDTO.getEmail(), email);
    }

    @Test
    public void notShouldCreateUserWithInvalidEmail() {
        String name = "User Test";
        String password = "user@123";
        String email = "user$%@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        assertThrows(
                EmailOrPasswordInvalid.class,
                () -> createUserUseCase.execute(createUserDTO),
                "Expected EmailOrPasswordInvalid"
        );
    }

    @Test
    public void notShouldCreateUserIfPasswordContainSpaceBlank() {
        String name = "User Test";
        String password = "user @123";
        String email = "user@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        assertThrows(
                EmailOrPasswordInvalid.class,
                () -> createUserUseCase.execute(createUserDTO),
                "Expected EmailOrPasswordInvalid"
        );
    }

    @Test
    public void notShouldCreateUserIfPasswordCntainLessThan8Characters() {
        String name = "User Test";
        String password = "use@123";
        String email = "user@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        assertThrows(
                EmailOrPasswordInvalid.class,
                () -> createUserUseCase.execute(createUserDTO),
                "Expected EmailOrPasswordInvalid"
        );
    }

}
