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

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        TokenDTO tokenDTO = tokenService.encrypt(userDTO);
        mockMvc.perform(put("/api/v1/users/confirm/{token}", tokenDTO.getToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("User Test"))
                .andExpect(jsonPath("$.email").value("usertest@email.com"));
    }

    @Test
    public void notShouldConfirmUserIfUserIsConfirmed() throws Exception {
        UserDTO userDTO = userService.execute(createUserDTO());
        TokenDTO tokenDTO = tokenService.encrypt(userDTO);
        mockMvc.perform(put("/api/v1/users/confirm/{token}", tokenDTO.getToken())
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(put("/api/v1/users/confirm/{token}", tokenDTO.getToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("UserAlreadyConfirmedError"))
                .andExpect(jsonPath("$.title").value("User has already been confirmed"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users/confirm/" + tokenDTO.getToken()))
                .andExpect(jsonPath("$.detail").value("User has already been confirmed. " +
                        "Login with your credentials. Click recover password if you forgot it"));
    }

//    @Test
//    public void notShoulConfirmUserIfUserIsNotFound() throws Exception {
//        TokenDTO tokenDTO = tokenService.encrypt(userDTO("usertest2", "usertest2@email.com"));
//        mockMvc.perform(put("/api/v1/users/confirm/{token}", tokenDTO.getToken())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.status").value(400))
//                .andExpect(jsonPath("$.type").value("UserNotFoundError"))
//                .andExpect(jsonPath("$.title").value("User not found"))
//                .andExpect(jsonPath("$.instance").value("/api/v1/users/confirm/" + tokenDTO.getToken()))
//                .andExpect(jsonPath("$.detail").value("User not found check registered email"));
//    }
}
