package com.fredsonchaves07.moviecatchapi.domain.dto.listUser;

import com.fredsonchaves07.moviecatchapi.domain.entities.records.MovieSeries;

public record MovieUserDTO(
        String id,
        String name,
        String imageUrl
) {

    public static MovieUserDTO from(MovieSeries movieSeries) {
        return new MovieUserDTO(movieSeries.id(), movieSeries.name(), movieSeries.urlImage());
    }
}
