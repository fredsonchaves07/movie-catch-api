package com.fredsonchaves07.moviecatchapi.api.resources.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UpdateUserDTO;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ManagementInfoUserApiResourceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private ConfirmUserUseCase confirmUserUseCase;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void shouldUpdateNameUser() throws Exception {
        String email = "usertest@email.com";
        String password = "user@123";
        String nameUpdated = "User updated";
        LoginDTO loginDTO = new LoginDTO(email, password);
        UpdateUserDTO user = new UpdateUserDTO(nameUpdated, null);
        String userBodyJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(put("/api/v1/users")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(
                                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()))
                        )
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(nameUpdated))
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    public void shouldAuthenticateUserWithNewPasswordUpdated() throws Exception {
        String email = "usertest@email.com";
        String password = "user@123";
        String passwordUpdated = "User@45631";
        LoginDTO loginDTO = new LoginDTO(email, password);
        UpdateUserDTO user = new UpdateUserDTO(null, passwordUpdated);
        String userBodyJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(put("/api/v1/users")
                .with(SecurityMockMvcRequestPostProcessors.authentication(
                        new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()))
                )
                .content(userBodyJson)
                .contentType(MediaType.APPLICATION_JSON));
        loginDTO = new LoginDTO(email, passwordUpdated);
        userBodyJson = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(post("/api/v1/login")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void shouldUpdateNameAndPasswordUser() throws Exception {
        String email = "usertest@email.com";
        String password = "user@123";
        String nameUpdated = "User updated";
        String passwordUpdated = "User@45631";
        LoginDTO loginDTO = new LoginDTO(email, password);
        UpdateUserDTO user = new UpdateUserDTO(nameUpdated, passwordUpdated);
        String userBodyJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(put("/api/v1/users")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(
                                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()))
                        )
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(nameUpdated))
                .andExpect(jsonPath("$.email").value(email));
        loginDTO = new LoginDTO(email, passwordUpdated);
        userBodyJson = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(post("/api/v1/login")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void notShouldUpdateUserIfNameAndPasswordIsNull() throws Exception {
        String email = "usertest@email.com";
        String password = "user@123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        UpdateUserDTO user = new UpdateUserDTO(null, null);
        String userBodyJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(put("/api/v1/users")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(
                                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()))
                        )
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("User Test"))
                .andExpect(jsonPath("$.email").value(email));
        userBodyJson = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(post("/api/v1/login")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void notShouldUpdateUserIfUserIsNotAuthenticate() throws Exception {
        String name = "User updated";
        String password = "User@45631";
        UpdateUserDTO user = new UpdateUserDTO(name, password);
        String userBodyJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(put("/api/v1/users")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.type").value("EmailOrPasswordIncorrect"))
                .andExpect(jsonPath("$.title").value("Email or password incorrect"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users"))
                .andExpect(jsonPath("$.detail").value(
                        "Invalid email or password. Please try again"
                ));
    }

    @Test
    public void notShouldUpdateUserIfUserNotAlreadyExist() throws Exception {
        String email = "usertestnotexist@email.com";
        String password = "user@123";
        String nameUpdated = "User updated";
        String passwordUpdated = "User@45631";
        LoginDTO loginDTO = new LoginDTO(email, password);
        UpdateUserDTO user = new UpdateUserDTO(nameUpdated, passwordUpdated);
        String userBodyJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(put("/api/v1/users")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(
                                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()))
                        )
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.type").value("UserNotFoundError"))
                .andExpect(jsonPath("$.title").value("User not found"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users"))
                .andExpect(jsonPath("$.detail").value(
                        "User not found check registered email"
                ));
    }

    @Test
    public void notShouldUpdateUserIfUserIsNotConfirmed() throws Exception {
        String nameUpdated = "User updated";
        String passwordUpdated = "User@45631";
        userDTO = createUserUseCase.execute(createUserDTO(
                "userNotConfirmed", "usernotconfirmed@email.com", "user@123")
        );
        LoginDTO loginDTO = new LoginDTO("usernotconfirmed@email.com", "user@123");
        UpdateUserDTO user = new UpdateUserDTO(nameUpdated, passwordUpdated);
        String userBodyJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(put("/api/v1/users")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(
                                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()))
                        )
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("UncofirmedUserError"))
                .andExpect(jsonPath("$.title").value("User has not been confirmed"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users"))
                .andExpect(jsonPath("$.detail").value(
                        "User has not been confirmed. Check your confirmation email and try again"
                ));
    }
}
