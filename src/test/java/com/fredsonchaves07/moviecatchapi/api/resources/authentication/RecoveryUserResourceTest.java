package com.fredsonchaves07.moviecatchapi.api.resources.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.RecoveryPasswordDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.ConfirmUserUseCase;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.CreateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RecoveryUserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ConfirmUserUseCase confirmUserUseCase;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        UserDTO userDTO = createUserUseCase.execute(createUserDTO());
        TokenDTO tokenDTO = tokenService.encrypt(Optional.of(userDTO));
        confirmUserUseCase.execute(tokenDTO);
    }

    @Test
    public void shouldRecoveryPasswordWithUserValid() throws Exception {
        String email = "usertest@email.com";
        String newPassword = "newPassword@123";
        RecoveryPasswordDTO recoveryPasswordDTO = new RecoveryPasswordDTO(email, newPassword);
        String userBodyJson = objectMapper.writeValueAsString(recoveryPasswordDTO);
        mockMvc.perform(post("/api/v1/recovery")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.email").isNotEmpty());
    }

    @Test
    public void notShouldRecoveryPasswordIfUserIsNoConfirmed() throws Exception {
        createUserUseCase.execute(createUserDTO("User test1", "usertest1@email.com", "user@12345"));
        String email = "usertest1@email.com";
        String newPassword = "newPassword@123";
        RecoveryPasswordDTO recoveryPasswordDTO = new RecoveryPasswordDTO(email, newPassword);
        String userBodyJson = objectMapper.writeValueAsString(recoveryPasswordDTO);
        mockMvc.perform(post("/api/v1/recovery")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("UncofirmedUserError"))
                .andExpect(jsonPath("$.title").value("User has not been confirmed"))
                .andExpect(jsonPath("$.instance").value("/api/v1/recovery"))
                .andExpect(jsonPath("$.detail").value(
                        "User has not been confirmed. Check your confirmation email and try again"
                ));
    }

    @Test
    public void shouldAuthenticateUserWithNewPassword() throws Exception {
        String email = "usertest@email.com";
        String newPassword = "newPassword@123";
        RecoveryPasswordDTO recoveryPasswordDTO = new RecoveryPasswordDTO(email, newPassword);
        String userBodyJson = objectMapper.writeValueAsString(recoveryPasswordDTO);
        mockMvc.perform(post("/api/v1/recovery")
                .content(userBodyJson)
                .contentType(MediaType.APPLICATION_JSON));
        LoginDTO loginDTO = new LoginDTO(email, newPassword);
        userBodyJson = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(post("/api/v1/login")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void notShouldAuthenticateUserWithOldPassword() throws Exception {
        String email = "usertest@email.com";
        String newPassword = "newPassword@123";
        RecoveryPasswordDTO recoveryPasswordDTO = new RecoveryPasswordDTO(email, newPassword);
        String userBodyJson = objectMapper.writeValueAsString(recoveryPasswordDTO);
        mockMvc.perform(post("/api/v1/recovery")
                .content(userBodyJson)
                .contentType(MediaType.APPLICATION_JSON));
        LoginDTO loginDTO = new LoginDTO(email, "user@123");
        userBodyJson = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(post("/api/v1/login")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.type").value("EmailOrPasswordIncorrect"))
                .andExpect(jsonPath("$.title").value("Email or password incorrect"))
                .andExpect(jsonPath("$.instance").value("/api/v1/login"))
                .andExpect(jsonPath("$.detail").value(
                        "Invalid email or password. Please try again"
                ));
    }

    @Test
    public void notShouldRecoveryPasswordWithEmailIsNull() throws Exception {
        String newPassword = "new";
        RecoveryPasswordDTO recoveryPasswordDTO = new RecoveryPasswordDTO(null, newPassword);
        String userBodyJson = objectMapper.writeValueAsString(recoveryPasswordDTO);
        mockMvc.perform(post("/api/v1/recovery")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("UserNotFoundError"))
                .andExpect(jsonPath("$.title").value("User not found"))
                .andExpect(jsonPath("$.instance").value("/api/v1/recovery"))
                .andExpect(jsonPath("$.detail").value("User not found check registered email"));
    }

    @Test
    public void notShouldRecoveryPasswordWithPasswordIsNull() throws Exception {
        String email = "usertest@email.com";
        RecoveryPasswordDTO recoveryPasswordDTO = new RecoveryPasswordDTO(email, null);
        String userBodyJson = objectMapper.writeValueAsString(recoveryPasswordDTO);
        mockMvc.perform(post("/api/v1/recovery")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("EmailOrPasswordInvalidError"))
                .andExpect(jsonPath("$.title").value("Email or password invalid"))
                .andExpect(jsonPath("$.instance").value("/api/v1/recovery"))
                .andExpect(jsonPath("$.detail").value(
                        "Invalid email or password. The password and email must contain mandatory criteria"
                ));
    }

    @Test
    public void notShouldRecoveryPasswordWithRecoveryPasswordDTOIsNull() throws Exception {
        String userBodyJson = objectMapper.writeValueAsString(null);
        mockMvc.perform(post("/api/v1/recovery")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("UnknownPropertyError"))
                .andExpect(jsonPath("$.title").value("Invalid reported request property"))
                .andExpect(jsonPath("$.instance").value("/api/v1/recovery"))
                .andExpect(jsonPath("$.detail").value(
                        "One or more invalid properties were reported. " +
                                "Check the request documentation for mandatory and optional parameters"
                ));
    }
}
