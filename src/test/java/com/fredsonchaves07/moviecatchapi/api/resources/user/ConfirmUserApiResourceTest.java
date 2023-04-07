package com.fredsonchaves07.moviecatchapi.api.resources.user;

import com.fredsonchaves07.moviecatchapi.api.services.user.CreateUserAPIService;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.userDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ConfirmUserApiResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreateUserAPIService userService;

    @Autowired
    private TokenService tokenService;

    @Test
    public void shouldConfirmUser() throws Exception {
        UserDTO userDTO = userService.execute(createUserDTO());
        TokenDTO tokenDTO = tokenService.encrypt(Optional.of(userDTO));
        mockMvc.perform(post("/api/v1/users/confirm/{token}", tokenDTO.getToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("User Test"))
                .andExpect(jsonPath("$.email").value("usertest@email.com"));
    }

    @Test
    public void notShouldConfirmUserIfUserIsConfirmed() throws Exception {
        UserDTO userDTO = userService.execute(createUserDTO());
        TokenDTO tokenDTO = tokenService.encrypt(Optional.of(userDTO));
        mockMvc.perform(post("/api/v1/users/confirm/{token}", tokenDTO.getToken())
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/api/v1/users/confirm/{token}", tokenDTO.getToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("UserAlreadyConfirmedError"))
                .andExpect(jsonPath("$.title").value("User has already been confirmed"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users/confirm/" + tokenDTO.getToken()))
                .andExpect(jsonPath("$.detail").value("User has already been confirmed. " +
                        "Login with your credentials"));
    }

    @Test
    public void notShoulConfirmUserIfUserIsNotFound() throws Exception {
        TokenDTO tokenDTO = tokenService.encrypt(Optional.of(userDTO("usertest2", "usertest2@email.com")));
        mockMvc.perform(post("/api/v1/users/confirm/{token}", tokenDTO.getToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.type").value("UserNotFoundError"))
                .andExpect(jsonPath("$.title").value("User not found"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users/confirm/" + tokenDTO.getToken()))
                .andExpect(jsonPath("$.detail").value("User not found check registered email"));
    }

    @Test
    public void notShoulConfirmUserWithInvalidToken() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciaiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6eTY2OTQ3MzMyM30." +
                "Bb-HcCS0EYGvczEmZgxjnz-_jo9hVDfecmAMLBuSQzgHefBv-EZCKXkb8F8GEmcgvTw6By0fwiwPz8ooz5mwwg";
        mockMvc.perform(post("/api/v1/users/confirm/{token}", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(498))
                .andExpect(jsonPath("$.status").value(498))
                .andExpect(jsonPath("$.type").value("InvalidTokenError"))
                .andExpect(jsonPath("$.title").value("Token invalid"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users/confirm/" + token))
                .andExpect(jsonPath("$.detail").value("Check token credentials and try again"));
    }

    @Test
    public void notShoulConfirmUserWithExpiredToken() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VydGVzdEBlbWFpbC5jb20iLCJleHAiOjE2Njk5NDM3MTgsImlhdCI6MTY2OTk0MzcxOH0." +
                "1-FfzP6NjRA05V5YSBVAc90nji3de9VVk9H8bAQpta64H2BQgHL2NmBJu1pFeh_2EmuDtKhLL4JKldH79Pt8_w";
        mockMvc.perform(post("/api/v1/users/confirm/{token}", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.type").value("ExpiredTokenError"))
                .andExpect(jsonPath("$.title").value("Token expired"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users/confirm/" + token))
                .andExpect(jsonPath("$.detail").value("Check token credentials and try again"));
    }
}
