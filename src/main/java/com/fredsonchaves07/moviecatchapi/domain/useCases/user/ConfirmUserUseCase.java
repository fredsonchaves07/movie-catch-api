package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.exception.TokenException;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.TokenExpiredOrInvalidException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.UserAlreadyConfirmedException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.UserNotFoundException;
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
    public void execute(TokenDTO token) {
        try {
            user = getUserDecriptedByToken(token);
            if (userIsNotFound()) throw new UserNotFoundException();
            if (isConfirmed()) throw new UserAlreadyConfirmedException();
            user.confirmUser();
            userRepository.save(user);
        } catch (TokenException tokenException) {
            throw new TokenExpiredOrInvalidException();
        }
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
