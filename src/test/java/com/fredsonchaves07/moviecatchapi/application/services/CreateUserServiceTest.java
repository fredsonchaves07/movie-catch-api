package com.fredsonchaves07.moviecatchapi.application.services;

import com.fredsonchaves07.moviecatchapi.application.services.user.CreateUserAPIService;
import com.fredsonchaves07.moviecatchapi.domain.dto.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.SendEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class CreateUserServiceTest {

    @InjectMocks
    private CreateUserAPIService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SendEmailService sendEmailService;

    @Test
    public void shouldCreateUser() {
        CreateUserDTO createUserDTO = createUserDTO();
        UserDTO userDTO = userService.createUser(createUserDTO);
        assertNotNull(userDTO);
        assertEquals(userDTO.getName(), createUserDTO.getName());
        assertEquals(userDTO.getEmail(), createUserDTO.getEmail());
    }
}
