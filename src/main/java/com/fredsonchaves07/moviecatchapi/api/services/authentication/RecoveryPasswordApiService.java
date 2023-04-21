package com.fredsonchaves07.moviecatchapi.api.services.authentication;

import com.fredsonchaves07.moviecatchapi.api.exception.BadRequestException;
import com.fredsonchaves07.moviecatchapi.api.exception.UnauthorizedException;
import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.DomainException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailOrPasswordIncorrectException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.authentication.RecoveryPasswordUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecoveryPasswordApiService {

    @Autowired
    private RecoveryPasswordUseCase recoveryPasswordUseCase;

    public UserDTO execute(LoginDTO loginDTO) {
        try {
            return recoveryPasswordUseCase.execute(loginDTO);
        } catch (EmailOrPasswordIncorrectException emailOrPasswordIncorrectException) {
            throw new UnauthorizedException(emailOrPasswordIncorrectException);
        } catch (DomainException domainException) {
            throw new BadRequestException(domainException);
        }
    }
}
