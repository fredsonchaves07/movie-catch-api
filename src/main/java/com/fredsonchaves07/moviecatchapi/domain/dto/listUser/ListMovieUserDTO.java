package com.fredsonchaves07.moviecatchapi.domain.dto.listUser;

import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;

import java.util.List;

public record ListMovieUserDTO(
        String name,
        String description,
        UserDTO user,
        boolean isPrivate,
        List<MovieUserDTO> movieUserDTOList
) {

    public ListMovieUserDTO(String name, String description, UserDTO user, List<MovieUserDTO> movieUserDTOList) {
        this(name, description, user, false, movieUserDTOList);
    }
}
