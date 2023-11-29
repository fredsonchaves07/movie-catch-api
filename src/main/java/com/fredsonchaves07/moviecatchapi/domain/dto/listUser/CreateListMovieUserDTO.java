package com.fredsonchaves07.moviecatchapi.domain.dto.listUser;

import java.util.List;

public record CreateListMovieUserDTO(
        String name,
        String description,
        boolean isPrivate,
        List<MovieUserDTO> movieUserDTOList
) {

    public CreateListMovieUserDTO(String name, String description, List<MovieUserDTO> movieUserDTOList) {
        this(name, description, false, movieUserDTOList);
    }
}
