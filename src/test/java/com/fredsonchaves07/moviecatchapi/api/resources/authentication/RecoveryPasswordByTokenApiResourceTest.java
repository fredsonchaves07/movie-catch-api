package com.fredsonchaves07.moviecatchapi.api.resources.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Optional;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.userDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RecoveryPasswordByTokenApiResourceTest {

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

    TokenDTO tokenDTO;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        UserDTO userDTO = createUserUseCase.execute(createUserDTO());
        tokenDTO = tokenService.encrypt(Optional.of(userDTO));
        confirmUserUseCase.execute(tokenDTO);
    }

    @Test
    public void shouldRecoveryPasswordByToken() throws Exception {
        mockMvc.perform(get("/api/v1/recovery/{token}", tokenDTO.getToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("User Test"))
                .andExpect(jsonPath("$.email").value("usertest@email.com"));
    }

    @Test
    public void notShouldRecoveryPasswordByTokenIfUserIsNotExist() throws Exception {
        TokenDTO tokenDTO = tokenService.encrypt(Optional.of(userDTO("usertest2", "usertest2@email.com")));
        mockMvc.perform(get("/api/v1/recovery/{token}", tokenDTO.getToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.type").value("UserNotFoundError"))
                .andExpect(jsonPath("$.title").value("User not found"))
                .andExpect(jsonPath("$.instance").value("/api/v1/recovery/" + tokenDTO.getToken()))
                .andExpect(jsonPath("$.detail").value("User not found check registered email"));
    }

    @Test
    public void notShouldRecoveryPasswordByTokenIfUserNotConfirmed() throws Exception {
        UserDTO userDTO = createUserUseCase.execute(
                createUserDTO("usernotexist", "usernotexist@email.com", "user@1234")
        );
        TokenDTO tokenDTO = tokenService.encrypt(Optional.of(userDTO));
        mockMvc.perform(get("/api/v1/recovery/{token}", tokenDTO.getToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("UncofirmedUserError"))
                .andExpect(jsonPath("$.title").value("User has not been confirmed"))
                .andExpect(jsonPath("$.instance").value("/api/v1/recovery/" + tokenDTO.getToken()))
                .andExpect(jsonPath("$.detail").value("User has not been confirmed. " +
                        "Check your confirmation email and try again")
                );
    }

    @Test
    public void notShouldRecoveryPassowordIfTokenIsInvalid() throws Exception {
        tokenDTO = new TokenDTO("eyJhbGciOiJIUzI1iJ9." +
                "eyJzdWIiOiJ1c2VydGVzdEBlbWFpbC5jb20iLCJpYXQiOjE2ODE5MDk3MzcsImV4cCI6MTY4MTkxNjkzN30." +
                "slb-J8bRZsV_3nu09DkobH0MBTBdF08EWjCYMPK-6jQ");
        mockMvc.perform(get("/api/v1/recovery/{token}", tokenDTO.getToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(498))
                .andExpect(jsonPath("$.status").value(498))
                .andExpect(jsonPath("$.type").value("InvalidTokenError"))
                .andExpect(jsonPath("$.title").value("Token invalid"))
                .andExpect(jsonPath("$.instance").value("/api/v1/recovery/" + tokenDTO.getToken()))
                .andExpect(jsonPath("$.detail").value("Check token credentials and try again"));
    }

    @Test
    public void notShoulRecoveryPasswordWithExpiredToken() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VydGVzdEBlbWFpbC5jb20iLCJleHAiOjE2Njk5NDM3MTgsImlhdCI6MTY2OTk0MzcxOH0." +
                "1-FfzP6NjRA05V5YSBVAc90nji3de9VVk9H8bAQpta64H2BQgHL2NmBJu1pFeh_2EmuDtKhLL4JKldH79Pt8_w";
        mockMvc.perform(get("/api/v1/recovery/{token}", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.type").value("ExpiredTokenError"))
                .andExpect(jsonPath("$.title").value("Token expired"))
                .andExpect(jsonPath("$.instance").value("/api/v1/recovery/" + token))
                .andExpect(jsonPath("$.detail").value("Check token credentials and try again"));
    }
}
