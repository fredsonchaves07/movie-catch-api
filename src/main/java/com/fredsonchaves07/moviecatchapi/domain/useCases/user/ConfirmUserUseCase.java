package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserAlreadyConfirmedException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ConfirmUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    private User user;

    @Transactional
    public UserDTO execute(TokenDTO token) {
        user = getUserDecriptedByToken(token);
        if (userIsNotFound()) throw new UserNotFoundException();
        if (isConfirmed()) throw new UserAlreadyConfirmedException();
        user.confirmUser();
        userRepository.save(user);
        return new UserDTO(user);
    }

    private User getUserDecriptedByToken(TokenDTO token) {
        String email = tokenService.decrypt(token);
        return userRepository.findByEmail(email);
    }

    private boolean isConfirmed() {
        return user.isConfirm();
    }

    private boolean userIsNotFound() {
        return user == null;
    }
}
