package com.fredsonchaves07.moviecatchapi.api.resources.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
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
public class CreateUserApiResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldCreateUser() throws Exception {
        CreateUserDTO createUserDTO = createUserDTO();
        String userBodyJson = objectMapper.writeValueAsString(createUserDTO);
        mockMvc.perform(post("/api/v1/users")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("User Test"))
                .andExpect(jsonPath("$.email").value("usertest@email.com"));
    }

    @Test
    public void notShouldCreateUserIfInvalidEmail() throws Exception {
        String name = "User Test";
        String password = "user@123";
        String email = "user$%@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        String userBodyJson = objectMapper.writeValueAsString(createUserDTO);
        mockMvc.perform(post("/api/v1/users")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("EmailOrPasswordInvalidError"))
                .andExpect(jsonPath("$.title").value("Email or password invalid"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users"))
                .andExpect(jsonPath("$.detail").value("Invalid email or password. The password and email must contain mandatory criteria"));
    }

    @Test
    public void notShouldCreateUserIfPasswordContainSpaceBlank() throws Exception {
        String name = "User Test";
        String password = "user @123";
        String email = "user@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        String userBodyJson = objectMapper.writeValueAsString(createUserDTO);
        mockMvc.perform(post("/api/v1/users")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("EmailOrPasswordInvalidError"))
                .andExpect(jsonPath("$.title").value("Email or password invalid"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users"))
                .andExpect(jsonPath("$.detail").value("Invalid email or password. The password and email must contain mandatory criteria"));
    }

    @Test
    public void notShouldCreateUserIfPasswordContainLessThan8Characters() throws Exception {
        String name = "User Test";
        String password = "use@123";
        String email = "user@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        String userBodyJson = objectMapper.writeValueAsString(createUserDTO);
        mockMvc.perform(post("/api/v1/users")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("EmailOrPasswordInvalidError"))
                .andExpect(jsonPath("$.title").value("Email or password invalid"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users"))
                .andExpect(jsonPath("$.detail").value("Invalid email or password. The password and email must contain mandatory criteria"));
    }

    @Test
    public void notShouldCreateUserIfEmailAlreadyExist() throws Exception {
        CreateUserDTO firstUser = createUserDTO();
        String userBodyJson = objectMapper.writeValueAsString(firstUser);
        mockMvc.perform(post("/api/v1/users")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("User Test"))
                .andExpect(jsonPath("$.email").value("usertest@email.com"));
        String name = "User Test";
        String password = "user@123";
        String email = "usertest@email.com";
        CreateUserDTO secondUser = createUserDTO(name, email, password);
        userBodyJson = objectMapper.writeValueAsString(secondUser);
        mockMvc.perform(post("/api/v1/users")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("EmailAlreadyExistError"))
                .andExpect(jsonPath("$.title").value("Email already exist"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users"))
                .andExpect(jsonPath("$.detail").value("It is not possible to register a user with email already registered in the system. Try again with another email"));
    }


    @Test
    public void notShouldCreateUserIfNamesNotProvided() throws Exception {
        String password = "user@123";
        String email = "user@email.com";
        CreateUserDTO createUserDTO = createUserDTO(null, email, password);
        String userBodyJson = objectMapper.writeValueAsString(createUserDTO);
        mockMvc.perform(post("/api/v1/users")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("NameValidError"))
                .andExpect(jsonPath("$.title").value("Name not provided or invalid"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users"))
                .andExpect(jsonPath("$.detail").value(
                        "The name field is required. " +
                                "It was not informed in the request or it is invalid. " +
                                "Consult the resource documentation to verify request details"));
    }

    @Test
    public void notShouldCreateUserIfPasswordNotProvided() throws Exception {
        String name = "User Test";
        String email = "usertest@email.com";
        CreateUserDTO createUserDTO = createUserDTO(name, email, null);
        String userBodyJson = objectMapper.writeValueAsString(createUserDTO);
        mockMvc.perform(post("/api/v1/users")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("EmailOrPasswordInvalidError"))
                .andExpect(jsonPath("$.title").value("Email or password invalid"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users"))
                .andExpect(jsonPath("$.detail").value("Invalid email or password. " +
                        "The password and email must contain mandatory criteria"));
    }

    @Test
    public void notShouldCreateUserIfEmailNotProvided() throws Exception {
        String name = "User Test";
        String password = "user@123";
        CreateUserDTO createUserDTO = createUserDTO(name, null, password);
        String userBodyJson = objectMapper.writeValueAsString(createUserDTO);
        mockMvc.perform(post("/api/v1/users")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("EmailOrPasswordInvalidError"))
                .andExpect(jsonPath("$.title").value("Email or password invalid"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users"))
                .andExpect(jsonPath("$.detail").value("Invalid email or password. " +
                        "The password and email must contain mandatory criteria"));
    }

    @Test
    public void notShouldCreateUserIfRequestHasUnknownProperties() throws Exception {
        String name = "User Test";
        String password = "use@123";
        String email = "user@email.com";
        String propertyUnknown = "";
        CreateUserDTO createUserDTO = createUserDTO(name, email, password);
        StringBuilder requestString = new StringBuilder().append(createUserDTO).append(propertyUnknown);
        String userBodyJson = objectMapper.writeValueAsString(requestString);
        mockMvc.perform(post("/api/v1/users")
                        .content(userBodyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("UnknownPropertyError"))
                .andExpect(jsonPath("$.title").value("Invalid reported request property"))
                .andExpect(jsonPath("$.instance").value("/api/v1/users"))
                .andExpect(jsonPath("$.detail").value("One or more invalid properties were reported. " +
                        "Check the request documentation for mandatory and optional parameters"));
    }
}
