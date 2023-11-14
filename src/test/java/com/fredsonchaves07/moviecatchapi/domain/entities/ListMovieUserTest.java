package com.fredsonchaves07.moviecatchapi.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUser;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ListMovieUserTest {

    private User existingUser;

    @BeforeEach
    public void setUp() {
        existingUser = createUser();
    }

    @Test
    public void shouldCreateListMovieUser() {
        String name = "List movie user 1";
        String description = "List movie user 1 description";
        List<MovieSeries> moviesList = List.of(
                new MovieSeries("1", "Movie 1", "urlImageMovie1"), new MovieSeries("2", "Serie 1", "urlImageMSerie1")
        );
        ListMovieUser listMovieUser = new ListMovieUser(name, description, existingUser, moviesList);
        assertNotNull(listMovieUser);
        assertEquals(name, listMovieUser.getName());
        assertEquals(description, listMovieUser.getDescription());
        assertEquals(existingUser, listMovieUser.getUser());
        assertEquals(moviesList, listMovieUser.getMovieList());
    }
}
