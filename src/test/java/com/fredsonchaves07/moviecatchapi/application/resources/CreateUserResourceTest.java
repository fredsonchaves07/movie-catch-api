package com.fredsonchaves07.moviecatchapi.application.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredsonchaves07.moviecatchapi.domain.dto.CreateUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
public class CreateUserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateUser() throws Exception {
        CreateUserDTO createUserDTO = createUserDTO();
        String userBodyJson = objectMapper.writeValueAsString(createUserDTO);
        ResultActions response = mockMvc
                .perform(post("/api/v1/users")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
