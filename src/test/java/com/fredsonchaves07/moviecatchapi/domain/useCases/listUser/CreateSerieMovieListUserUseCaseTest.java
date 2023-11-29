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

    @Test
    public void shouldCreateMultipleListMovieUser() {
        CreateListMovieUserDTO list1 = new CreateListMovieUserDTO(
                "List movie user 1", "List movie user 1 description", List.of(
                new MovieUserDTO("1", "Movie 1", "urlImageMovie1"),
                new MovieUserDTO("2", "Serie 1", "urlImageSerie1")
        )
        );
        CreateListMovieUserDTO list2 = new CreateListMovieUserDTO(
                "List movie user 2", "List movie user 2 description", List.of(
                new MovieUserDTO("6", "Serie 6", "urlImageSerie6"),
                new MovieUserDTO("10", "Movie 10", "urlImageMovie10")
        )
        );
        CreateListMovieUserDTO list3 = new CreateListMovieUserDTO(
                "List movie user 3", "List movie user 3 description", List.of(
                new MovieUserDTO("2", "Serie 1", "urlImageSerie1"),
                new MovieUserDTO("1", "Movie 1", "urlImageMovie1")
        )
        );
        ListMovieUserDTO listMovieUser1 = createSerieMovieListUserUseCase.execute(list1);
        ListMovieUserDTO listMovieUser2 = createSerieMovieListUserUseCase.execute(list2);
        ListMovieUserDTO listMovieUser3 = createSerieMovieListUserUseCase.execute(list3);
        assertNotNull(listMovieUser1);
        assertNotNull(listMovieUser2);
        assertNotNull(listMovieUser3);
        assertEquals("List movie user 1", listMovieUser1.name());
        assertEquals("List movie user 1 description", listMovieUser1.description());
        assertEquals(userDTO, listMovieUser1.user());
        assertEquals(List.of(
                new MovieUserDTO("1", "Movie 1", "urlImageMovie1"),
                new MovieUserDTO("2", "Serie 1", "urlImageSerie1")
        ), listMovieUser1.movieUserDTOList());
        assertFalse(listMovieUser1.movieUserDTOList().isEmpty());
        assertEquals(2, listMovieUser1.movieUserDTOList().size());
        assertEquals("List movie user 2", listMovieUser2.name());
        assertEquals("List movie user 2 description", listMovieUser2.description());
        assertEquals(userDTO, listMovieUser2.user());
        assertEquals(List.of(
                new MovieUserDTO("6", "Serie 6", "urlImageSerie6"),
                new MovieUserDTO("10", "Movie 10", "urlImageMovie10")
        ), listMovieUser2.movieUserDTOList());
        assertFalse(listMovieUser2.movieUserDTOList().isEmpty());
        assertEquals(2, listMovieUser2.movieUserDTOList().size());
        assertEquals("List movie user 3", listMovieUser3.name());
        assertEquals("List movie user 3 description", listMovieUser3.description());
        assertEquals(userDTO, listMovieUser3.user());
        assertEquals(List.of(
                new MovieUserDTO("2", "Serie 1", "urlImageSerie1"),
                new MovieUserDTO("1", "Movie 1", "urlImageMovie1")
        ), listMovieUser3.movieUserDTOList());
        assertFalse(listMovieUser3.movieUserDTOList().isEmpty());
        assertEquals(2, listMovieUser3.movieUserDTOList().size());
    }
}
