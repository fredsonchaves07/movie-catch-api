package com.fredsonchaves07.moviecatchapi.api.resources;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MethodNotAllowedResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnMethodNotAllowedIfRequestIsInvalid() throws Exception {
        mockMvc.perform(delete("/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.status").value(405))
                .andExpect(jsonPath("$.type").value("MethodNotAllowedError"))
                .andExpect(jsonPath("$.title").value("Method not allowed"))
                .andExpect(jsonPath("$.instance").value("/"))
                .andExpect(jsonPath("$.detail").value(
                        "The resource you are trying to access does " +
                                "not allow requests of this type. See available feature documentation."
                ));
    }

    @Test
    public void notShouldReturnMethodNotAllowedIfRequestIsValid() throws Exception {
        mockMvc.perform(get("/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
