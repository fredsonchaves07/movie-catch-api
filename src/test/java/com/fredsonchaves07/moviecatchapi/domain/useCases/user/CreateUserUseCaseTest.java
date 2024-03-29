package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.user.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailAlreadyExistException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailOrPasswordInvalidException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.NameInvalidException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreateUserUseCaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldCreateUser() {
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
                EmailOrPasswordInvalidException.class,
                () -> createUserUseCase.execute(createUserDTO)
        );
    }

    @Test
    public void notShouldCreateUserIfPasswordContainSpaceBlank() {
        String name = "User Test";
        String password = "user @123";
        String email = "user@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        assertThrows(
                EmailOrPasswordInvalidException.class,
                () -> createUserUseCase.execute(createUserDTO)
        );
    }

    @Test
    public void notShouldCreateUserIfPasswordContainLessThan8Characters() {
        String name = "User Test";
        String password = "use@123";
        String email = "user@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        assertThrows(
                EmailOrPasswordInvalidException.class,
                () -> createUserUseCase.execute(createUserDTO)
        );
    }

    @Test
    public void notShouldCreateUserIfEmailAlreadyExist() {
        CreateUserDTO firstUser = createUserDTO();
        createUserUseCase.execute(firstUser);
        String name = "User Test";
        String password = "user@123";
        String email = "usertest@email.com";
        CreateUserDTO secondUser = createUserDTO(name, email, password);
        assertThrows(
                EmailAlreadyExistException.class,
                () -> createUserUseCase.execute(secondUser)
        );
    }

    @Test
    public void notShouldCreateUserIfNameIsNotValid() {
        String email = "user@email.com";
        String password = "user@123";
        CreateUserDTO createUserDTO = createUserDTO(null, email, password);
        assertThrows(
                NameInvalidException.class,
                () -> createUserUseCase.execute(createUserDTO)
        );
    }

    @Test
    public void notShouldCreateUserIfEmailNotValid() {
        String name = "User test";
        String password = "user@123";
        CreateUserDTO createUserDTO = createUserDTO(name, null, password);
        assertThrows(
                EmailOrPasswordInvalidException.class,
                () -> createUserUseCase.execute(createUserDTO)
        );
    }

    @Test
    public void notShouldCreateUserIfPasswordNotInformed() {
        String name = "User test";
        String email = "user@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, null);
        assertThrows(
                EmailOrPasswordInvalidException.class,
                () -> createUserUseCase.execute(createUserDTO)
        );
    }
}
