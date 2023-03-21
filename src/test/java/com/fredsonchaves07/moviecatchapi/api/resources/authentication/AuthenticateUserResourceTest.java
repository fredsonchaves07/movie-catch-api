package com.fredsonchaves07.moviecatchapi.api.resources.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
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

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthenticateUserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private ConfirmUserUseCase confirmUserUseCase;

    @Autowired
    private TokenService tokenService;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        UserDTO userDTO = createUserUseCase.execute(createUserDTO());
        TokenDTO tokenDTO = tokenService.encrypt(userDTO);
        confirmUserUseCase.execute(tokenDTO);
    }

    @Test
    public void shouldAuthenticateUserWithEmailRegisteredValid() throws Exception {
        String email = "usertest@email.com";
        String password = "user@123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        String userBodyJson = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(post("/api/v1/login")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void notShouldAuthenticateUserWithEmailDoesNotExists() throws Exception {
        String email = "usertest1@email.com";
        String password = "user@123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        String userBodyJson = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(post("/api/v1/login")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("EmailOrPasswordIncorrect"))
                .andExpect(jsonPath("$.title").value("Email or password incorrect"))
                .andExpect(jsonPath("$.instance").value("/api/v1/login"))
                .andExpect(jsonPath("$.detail").value(
                        "Invalid email or password. Please try again"
                ));
    }

    @Test
    public void notShouldAuthenticateUserIfPasswordDoesNotMatch() throws Exception {
        String email = "usertest@email.com";
        String password = "user@1243";
        LoginDTO loginDTO = new LoginDTO(email, password);
        String userBodyJson = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(post("/api/v1/login")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("EmailOrPasswordIncorrect"))
                .andExpect(jsonPath("$.title").value("Email or password incorrect"))
                .andExpect(jsonPath("$.instance").value("/api/v1/login"))
                .andExpect(jsonPath("$.detail").value(
                        "Invalid email or password. Please try again"
                ));
    }

    @Test
    public void notShouldAuthenticateUserIfEmailIsNull() throws Exception {
        String password = "user@1243";
        LoginDTO loginDTO = new LoginDTO(null, password);
        String userBodyJson = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(post("/api/v1/login")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("EmailOrPasswordIncorrect"))
                .andExpect(jsonPath("$.title").value("Email or password incorrect"))
                .andExpect(jsonPath("$.instance").value("/api/v1/login"))
                .andExpect(jsonPath("$.detail").value(
                        "Invalid email or password. Please try again"
                ));
    }

    @Test
    public void notShouldAuthenticateUserIfPasswordIsNull() throws Exception {
        String email = "usertest@email.com";
        LoginDTO loginDTO = new LoginDTO(email, null);
        String userBodyJson = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(post("/api/v1/login")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("EmailOrPasswordIncorrect"))
                .andExpect(jsonPath("$.title").value("Email or password incorrect"))
                .andExpect(jsonPath("$.instance").value("/api/v1/login"))
                .andExpect(jsonPath("$.detail").value(
                        "Invalid email or password. Please try again"
                ));
    }

    @Test
    public void notShouldAuthenticateIfLoginIsNull() throws Exception {
        String userBodyJson = objectMapper.writeValueAsString(null);
        mockMvc.perform(post("/api/v1/login")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("UnknownPropertyError"))
                .andExpect(jsonPath("$.title").value("Invalid reported request property"))
                .andExpect(jsonPath("$.instance").value("/api/v1/login"))
                .andExpect(jsonPath("$.detail").value(
                        "One or more invalid properties were reported. " +
                                "Check the request documentation for mandatory and optional parameters"
                ));
    }

    @Test
    public void notShouldAuthenticateUserIfUserIsNoConfirmed() throws Exception {
        createUserUseCase.execute(createUserDTO(
                "User not confirmed", "usertnotconfirmed@email.com", "user@123")
        );
        String email = "usertnotconfirmed@email.com";
        String password = "user@123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        String userBodyJson = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(post("/api/v1/login")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("UncofirmedUserError"))
                .andExpect(jsonPath("$.title").value("User has not been confirmed"))
                .andExpect(jsonPath("$.instance").value("/api/v1/login"))
                .andExpect(jsonPath("$.detail").value(
                        "User has not been confirmed. Check your confirmation email and try again"
                ));
    }
}
