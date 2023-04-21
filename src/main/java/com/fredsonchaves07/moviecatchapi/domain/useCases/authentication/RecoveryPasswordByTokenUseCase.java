package com.fredsonchaves07.moviecatchapi.domain.useCases.authentication;

import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UnconfirmedUserException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RecoveryPasswordByTokenUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public UserDTO execute(TokenDTO tokenDTO) {
        User user = decryptedUserByToken(tokenDTO);
        if (!user.isConfirm())
            throw new UnconfirmedUserException();
        return new UserDTO(user);
    }

    private User decryptedUserByToken(TokenDTO tokenDTO) {
        String email = tokenService.decrypt(Optional.ofNullable(tokenDTO));
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
