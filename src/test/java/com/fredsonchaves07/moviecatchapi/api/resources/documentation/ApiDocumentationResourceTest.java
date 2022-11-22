package com.fredsonchaves07.moviecatchapi.api.resources.documentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ApiDocumentationResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetApiDocumentation() throws Exception {
        mockMvc.perform(get("/docs")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isFound());
    }
}
