package com.fredsonchaves07.moviecatchapi.api.services.authentication;

import com.fredsonchaves07.moviecatchapi.api.exception.BadRequestException;
import com.fredsonchaves07.moviecatchapi.api.exception.ResourceNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UnconfirmedUserException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.authentication.RecoveryPasswordByTokenUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecoveryPasswordByTokenApiService {

    @Autowired
    private RecoveryPasswordByTokenUseCase recoveryPasswordByTokenUseCase;

    public UserDTO execute(TokenDTO tokenDTO) {
        try {
            return recoveryPasswordByTokenUseCase.execute(tokenDTO);
        } catch (UserNotFoundException userNotFoundException) {
            throw new ResourceNotFoundException(userNotFoundException);
        } catch (UnconfirmedUserException unconfirmedUserException) {
            throw new BadRequestException(unconfirmedUserException);
        }
    }
}
