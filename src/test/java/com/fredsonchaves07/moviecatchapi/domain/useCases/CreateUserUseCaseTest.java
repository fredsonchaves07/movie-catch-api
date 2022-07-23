package com.fredsonchaves07.moviecatchapi.domain.useCases;

import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.fredsonchaves07.moviecatchapi.factories.DoublesFactory.getInMemoryUserRepository;

public class CreateUserUseCaseTest {

    UserRepository userRepository;
    CreateUserUseCase createUserUseCase;

    @BeforeEach
    public void setUp() {
        userRepository = getInMemoryUserRepository();
        createUserUseCase = new CreateUserUseCase(userRepository);
    }

    @Test
    public void shouldCreateUserByUseCase() {
//        UserDTO userDTO = createUserDTO();
        createUserUseCase.execute();
    }
}
