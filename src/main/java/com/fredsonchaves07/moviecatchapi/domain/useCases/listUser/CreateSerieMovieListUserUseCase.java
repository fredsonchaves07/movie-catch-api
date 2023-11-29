package com.fredsonchaves07.moviecatchapi.domain.useCases.listUser;

import com.fredsonchaves07.moviecatchapi.domain.dto.listUser.CreateListMovieUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.listUser.ListMovieUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.listUser.MovieUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.ListMovieUser;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.entities.records.MovieSeries;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateSerieMovieListUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public ListMovieUserDTO execute(CreateListMovieUserDTO createListMovieUserDTO) {
        String name = createListMovieUserDTO.name();
        String description = createListMovieUserDTO.description();
        boolean isPrivate = createListMovieUserDTO.isPrivate();
        List<MovieSeries> movieSeries = createListMovieUserDTO.movieUserDTOList().stream().map(MovieSeries::from).toList();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = (String) authentication.getPrincipal();
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        ListMovieUser listMovieUser = new ListMovieUser(name, description, user, movieSeries, isPrivate);
        return new ListMovieUserDTO(
                listMovieUser.getName(),
                listMovieUser.getDescription(),
                new UserDTO(listMovieUser.getUser().getName(), listMovieUser.getUser().getEmail()),
                listMovieUser.isPrivate(),
                listMovieUser.getMoviesList().stream().map(MovieUserDTO::from).toList()
        );
    }
}
