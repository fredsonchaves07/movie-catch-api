package com.fredsonchaves07.moviecatchapi.domain.entities;

import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "list_movie_user")
public class ListMovieUser {

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

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<MovieSeries> moviesList;

    private String description;

    @ManyToOne
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
        this.moviesList = moviesList;
    }
}
