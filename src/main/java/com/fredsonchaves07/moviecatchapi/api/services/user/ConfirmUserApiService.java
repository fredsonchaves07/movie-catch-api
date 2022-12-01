package com.fredsonchaves07.moviecatchapi.api.services.user;

import com.fredsonchaves07.moviecatchapi.api.services.exception.ConfirmUserUseCaseException;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.DomainUseCaseException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.ConfirmUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmUserApiService {

    @Autowired
    private ConfirmUserUseCase confirmUserUseCase;

    public UserDTO execute(String token) {
        try {
            return confirmUserUseCase.execute(new TokenDTO(token));
        } catch (DomainUseCaseException exception) {
            throw new ConfirmUserUseCaseException(exception.getType(), exception.getTitle(), exception.getMessage());
        }
    }
}
