package com.fredsonchaves07.moviecatchapi.domain.useCases.listUser;

import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.listUser.CreateListMovieUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.listUser.ListMovieUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.listUser.MovieUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.authentication.AuthenticateUserUseCase;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.ConfirmUserUseCase;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.CreateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUserDTO;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreateSerieMovieListUserUseCaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private ConfirmUserUseCase confirmUserUseCase;

    @Autowired
    private AuthenticateUserUseCase authenticateUserUseCase;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CreateSerieMovieListUserUseCase createSerieMovieListUserUseCase;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        userDTO = createUserUseCase.execute(createUserDTO());
        TokenDTO tokenDTO = tokenService.encrypt(Optional.of(userDTO));
        confirmUserUseCase.execute(tokenDTO);
        String email = "usertest@email.com";
        String password = "user@123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        authenticateUserUseCase.execute(loginDTO);
    }

    @Test
    public void shouldCreateListMovieUser() {
        String name = "List movie user 1";
        String description = "List movie user 1 description";
        List<MovieUserDTO> moviesList = List.of(
                new MovieUserDTO("1", "Movie 1", "urlImageMovie1"),
                new MovieUserDTO("2", "Serie 1", "urlImageMSerie1")
        );
        CreateListMovieUserDTO createListMovieUserDTO = new CreateListMovieUserDTO(name, description, moviesList);
        ListMovieUserDTO listMovieUser = createSerieMovieListUserUseCase.execute(createListMovieUserDTO);
        assertNotNull(listMovieUser);
        assertFalse(listMovieUser.isPrivate());
        assertEquals(name, listMovieUser.name());
        assertEquals(description, listMovieUser.description());
        assertEquals(userDTO, listMovieUser.user());
        assertEquals(moviesList, listMovieUser.movieUserDTOList());
        assertFalse(listMovieUser.movieUserDTOList().isEmpty());
        assertEquals(2, listMovieUser.movieUserDTOList().size());
    }
}
