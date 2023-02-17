package com.fredsonchaves07.moviecatchapi.api.services.authentication;

import com.fredsonchaves07.moviecatchapi.api.exception.BadRequestException;
import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailOrPasswordIncorrectException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.authentication.AuthenticateUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserApiService {

    @Autowired
    private AuthenticateUserUseCase authenticateUserUseCase;

    public TokenDTO execute(LoginDTO loginDTO) {
        try {
            return authenticateUserUseCase.execute(loginDTO);
        } catch (EmailOrPasswordIncorrectException exception) {
            throw new BadRequestException(exception);
        }
    }
}
