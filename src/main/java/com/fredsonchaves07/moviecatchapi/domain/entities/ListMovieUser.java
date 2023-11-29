
package com.fredsonchaves07.moviecatchapi.domain.entities;

import com.fredsonchaves07.moviecatchapi.domain.entities.records.MovieSeries;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Table(name = "list_movie_user")
@TypeDef(
        name = "json", typeClass = JsonType.class
)
public class ListMovieUser implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @ColumnDefault("random_uuid()")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isPrivate = false;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private List<MovieSeries> moviesList;

    private String description;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updatedAt;

    @Deprecated
    public ListMovieUser() {
    }

    public ListMovieUser(String name, String description, User user, List<MovieSeries> moviesList) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.moviesList = new ArrayList<>(moviesList);
    }

    public ListMovieUser(String name, String description, User user, List<MovieSeries> moviesList, boolean isPrivate) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.moviesList = new ArrayList<>(moviesList);
        this.isPrivate = isPrivate;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public List<MovieSeries> getMoviesList() {
        return moviesList;
    }

    public void addMovieSerie(MovieSeries movieSeries) {
        if (Objects.nonNull(movieSeries)) {
            moviesList.add(movieSeries);
        }
    }

    public void removeMovieSerie(MovieSeries movieSeries) {
        moviesList.remove(movieSeries);
    }

    public Optional<MovieSeries> searchMovieSerieByName(String name) {
        return moviesList.stream().filter(movieSeries -> movieSeries.name().equals(name)).findFirst();
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate() {
        isPrivate = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListMovieUser that = (ListMovieUser) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
