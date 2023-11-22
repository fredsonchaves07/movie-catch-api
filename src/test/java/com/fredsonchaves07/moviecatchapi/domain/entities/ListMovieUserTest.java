package com.fredsonchaves07.moviecatchapi.domain.entities;

import com.fredsonchaves07.moviecatchapi.domain.entities.records.MovieSeries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.createUser;
import static org.junit.jupiter.api.Assertions.*;

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
                new MovieSeries("1", "Movie 1", "urlImageMovie1"),
                new MovieSeries("2", "Serie 1", "urlImageMSerie1")
        );
        ListMovieUser listMovieUser = new ListMovieUser(name, description, existingUser, moviesList);
        assertNotNull(listMovieUser);
        assertFalse(listMovieUser.isPrivate());
        assertEquals(name, listMovieUser.getName());
        assertEquals(description, listMovieUser.getDescription());
        assertEquals(existingUser, listMovieUser.getUser());
        assertEquals(moviesList, listMovieUser.getMoviesList());
        assertFalse(listMovieUser.getMoviesList().isEmpty());
        assertEquals(2, listMovieUser.getMoviesList().size());
    }

    @Test
    public void shouldCreateMultipleListMovieUser() {
        ListMovieUser listMovieUser1 = new ListMovieUser(
                "List movie user 1", "List movie user 1 description", existingUser, List.of(
                new MovieSeries("1", "Movie 1", "urlImageMovie1"),
                new MovieSeries("2", "Serie 1", "urlImageSerie1")
        ));
        ListMovieUser listMovieUser2 = new ListMovieUser(
                "List movie user 2", "List movie user 2 description", existingUser, List.of(
                new MovieSeries("6", "Serie 6", "urlImageSerie6"),
                new MovieSeries("10", "Movie 10", "urlImageMovie10")
        ));
        ListMovieUser listMovieUser3 = new ListMovieUser(
                "List movie user 3", "List movie user 3 description", existingUser, List.of(
                new MovieSeries("2", "Serie 1", "urlImageSerie1"),
                new MovieSeries("1", "Movie 1", "urlImageMovie1")
        ));
        assertNotNull(existingUser.getListMovieUsers());
        assertFalse(existingUser.getListMovieUsers().isEmpty());
        assertEquals(3, existingUser.getListMovieUsers().size());
        assertFalse(listMovieUser1.isPrivate());
        assertFalse(listMovieUser2.isPrivate());
        assertFalse(listMovieUser3.isPrivate());
        assertEquals(listMovieUser1, existingUser.getListMovieUsers().get(0));
        assertEquals(listMovieUser2, existingUser.getListMovieUsers().get(1));
        assertEquals(listMovieUser3, existingUser.getListMovieUsers().get(2));
    }

    @Test
    public void shouldAddMovieInListMovieUser() {
        String name = "List movie user 1";
        String description = "List movie user 1 description";
        List<MovieSeries> moviesList = List.of(
                new MovieSeries("1", "Movie 1", "urlImageMovie1"),
                new MovieSeries("2", "Serie 1", "urlImageMSerie1")
        );
        ListMovieUser listMovieUser = new ListMovieUser(name, description, existingUser, moviesList);
        listMovieUser.addMovieSerie(new MovieSeries("3", "Movie 2", "urlImageMovie2"));
        assertNotNull(listMovieUser);
        assertFalse(listMovieUser.isPrivate());
        assertEquals(name, listMovieUser.getName());
        assertEquals(description, listMovieUser.getDescription());
        assertEquals(existingUser, listMovieUser.getUser());
        assertFalse(listMovieUser.getMoviesList().isEmpty());
        assertEquals(3, listMovieUser.getMoviesList().size());
        assertEquals("3", listMovieUser.getMoviesList().get(2).id());
        assertEquals("Movie 2", listMovieUser.getMoviesList().get(2).name());
        assertEquals("urlImageMovie2", listMovieUser.getMoviesList().get(2).urlImage());
    }

    @Test
    public void notShouldAddMovieInListMovieUserIfMovieSeriesIsNull() {
        String name = "List movie user 1";
        String description = "List movie user 1 description";
        List<MovieSeries> moviesList = List.of(
                new MovieSeries("1", "Movie 1", "urlImageMovie1"),
                new MovieSeries("2", "Serie 1", "urlImageMSerie1")
        );
        ListMovieUser listMovieUser = new ListMovieUser(name, description, existingUser, moviesList);
        listMovieUser.addMovieSerie(null);
        assertFalse(listMovieUser.isPrivate());
        assertNotNull(listMovieUser);
        assertEquals(name, listMovieUser.getName());
        assertEquals(description, listMovieUser.getDescription());
        assertEquals(existingUser, listMovieUser.getUser());
        assertFalse(listMovieUser.getMoviesList().isEmpty());
        assertEquals(2, listMovieUser.getMoviesList().size());
    }

    @Test
    public void shouldRemoveMovieInListMovieUser() {
        String name = "List movie user 1";
        String description = "List movie user 1 description";
        List<MovieSeries> moviesList = List.of(
                new MovieSeries("1", "Movie 1", "urlImageMovie1"),
                new MovieSeries("2", "Serie 1", "urlImageMSerie1")
        );
        ListMovieUser listMovieUser = new ListMovieUser(name, description, existingUser, moviesList);
        listMovieUser.removeMovieSerie(new MovieSeries("1", "Movie 1", "urlImageMovie1"));
        assertFalse(listMovieUser.isPrivate());
        assertNotNull(listMovieUser);
        assertEquals(name, listMovieUser.getName());
        assertEquals(description, listMovieUser.getDescription());
        assertEquals(existingUser, listMovieUser.getUser());
        assertFalse(listMovieUser.getMoviesList().isEmpty());
        assertEquals(1, listMovieUser.getMoviesList().size());
    }

    @Test
    public void notShouldRemoveInListMovieUserIfMovieSeriesIsNotInTheList() {
        String name = "List movie user 1";
        String description = "List movie user 1 description";
        List<MovieSeries> moviesList = List.of(
                new MovieSeries("1", "Movie 1", "urlImageMovie1"),
                new MovieSeries("2", "Serie 1", "urlImageMSerie1")
        );
        ListMovieUser listMovieUser = new ListMovieUser(name, description, existingUser, moviesList);
        listMovieUser.removeMovieSerie(new MovieSeries("6", "Movie 100", "urlImageMovie100"));
        assertNotNull(listMovieUser);
        assertFalse(listMovieUser.isPrivate());
        assertEquals(name, listMovieUser.getName());
        assertEquals(description, listMovieUser.getDescription());
        assertEquals(existingUser, listMovieUser.getUser());
        assertFalse(listMovieUser.getMoviesList().isEmpty());
        assertEquals(2, listMovieUser.getMoviesList().size());
    }

    @Test
    public void shouldSearchMovieInListUserByName() {
        String name = "List movie user 1";
        String description = "List movie user 1 description";
        List<MovieSeries> moviesList = List.of(
                new MovieSeries("1", "Movie 1", "urlImageMovie1"),
                new MovieSeries("2", "Serie 1", "urlImageMSerie1")
        );
        ListMovieUser listMovieUser = new ListMovieUser(name, description, existingUser, moviesList);
        listMovieUser.addMovieSerie(new MovieSeries("3", "Movie 2", "urlImageMovie2"));
        MovieSeries movieSeries = listMovieUser.searchMovieSerieByName("Serie 1").orElseThrow();
        assertEquals("2", movieSeries.id());
        assertEquals("Serie 1", movieSeries.name());
        assertEquals("urlImageMSerie1", movieSeries.urlImage());
    }

    @Test
    public void notShouldSearchMovieInListUserIfNameIsInvalid() {
        String name = "List movie user 1";
        String description = "List movie user 1 description";
        List<MovieSeries> moviesList = List.of(
                new MovieSeries("1", "Movie 1", "urlImageMovie1"),
                new MovieSeries("2", "Serie 1", "urlImageMSerie1")
        );
        ListMovieUser listMovieUser = new ListMovieUser(name, description, existingUser, moviesList);
        listMovieUser.addMovieSerie(new MovieSeries("3", "Movie 2", "urlImageMovie2"));
        assertFalse(listMovieUser.searchMovieSerieByName("Serie 4").isPresent());
    }

    @Test
    public void shouldCreateListMovieSeriePrivate() {
        String name = "List movie user 1";
        String description = "List movie user 1 description";
        List<MovieSeries> moviesList = List.of(
                new MovieSeries("1", "Movie 1", "urlImageMovie1"),
                new MovieSeries("2", "Serie 1", "urlImageMSerie1")
        );
        ListMovieUser listMovieUser = new ListMovieUser(name, description, existingUser, moviesList, true);
        assertNotNull(listMovieUser);
        assertTrue(listMovieUser.isPrivate());
        assertEquals(name, listMovieUser.getName());
        assertEquals(description, listMovieUser.getDescription());
        assertEquals(existingUser, listMovieUser.getUser());
        assertEquals(moviesList, listMovieUser.getMoviesList());
        assertFalse(listMovieUser.getMoviesList().isEmpty());
        assertEquals(2, listMovieUser.getMoviesList().size());
    }

    @Test
    public void shouldPrivateListSerieMovie() {
        String name = "List movie user 1";
        String description = "List movie user 1 description";
        List<MovieSeries> moviesList = List.of(
                new MovieSeries("1", "Movie 1", "urlImageMovie1"),
                new MovieSeries("2", "Serie 1", "urlImageMSerie1")
        );
        ListMovieUser listMovieUser = new ListMovieUser(name, description, existingUser, moviesList);
        listMovieUser.setPrivate();
        assertNotNull(listMovieUser);
        assertTrue(listMovieUser.isPrivate());
        assertEquals(name, listMovieUser.getName());
        assertEquals(description, listMovieUser.getDescription());
        assertEquals(existingUser, listMovieUser.getUser());
        assertEquals(moviesList, listMovieUser.getMoviesList());
        assertFalse(listMovieUser.getMoviesList().isEmpty());
        assertEquals(2, listMovieUser.getMoviesList().size());
    }
}
