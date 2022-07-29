package com.fredsonchaves07.moviecatchapi.domain.useCases;

import com.fredsonchaves07.moviecatchapi.domain.dto.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.EmailOrPasswordInvalid;
import org.junit.jupiter.api.Test;

import static com.fredsonchaves07.moviecatchapi.domain.dto.factories.CreateUserDTOFactory.createUserDTO;
import static org.junit.jupiter.api.Assertions.*;

public class CreateUserUseCaseTest {

    UserRepository userRepository;
    CreateUserUseCase createUserUseCase;

//    @BeforeEach
//    public void setUp() {
//        userRepository = getInMemoryUserRepository();
//        createUserUseCase = new CreateUserUseCase(userRepository);
//    }

    @Test
    public void shouldCreateUserByUseCase() {
        String name = "User Test";
        String password = "user@123";
        String email = "user@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        UserDTO userDTO = createUserUseCase.execute(createUserDTO);
        assertNotNull(userDTO);
        assertEquals(userDTO.getName(), name);
        assertEquals(userDTO.getEmail(), email);
    }

    @Test
    public void notSouldCreateUserWithInvalidEmail() {
        String name = "User Test";
        String password = "user@123";
        String email = "user @email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, password, email);
        assertThrows(EmailOrPasswordInvalid.class, () -> createUserUseCase.execute(createUserDTO), "Expected EmailOrPasswordInvalid");
    }
}
