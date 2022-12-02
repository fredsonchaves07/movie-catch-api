package com.fredsonchaves07.moviecatchapi.api.services.user;

import com.fredsonchaves07.moviecatchapi.api.exception.ApiExpiredTokenException;
import com.fredsonchaves07.moviecatchapi.api.exception.ApiInvalidTokenException;
import com.fredsonchaves07.moviecatchapi.api.exception.ApiUserNotFoundException;
import com.fredsonchaves07.moviecatchapi.api.exception.BadRequestException;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.ExpiredTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.InvalidTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserAlreadyConfirmedException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
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
        } catch (UserAlreadyConfirmedException exception) {
            throw new BadRequestException(exception.getType(), exception.getTitle(), exception.getMessage());
        } catch (InvalidTokenException exception) {
            throw new ApiInvalidTokenException(exception.getType(), exception.getTitle(), exception.getMessage());
        } catch (ExpiredTokenException exception) {
            throw new ApiExpiredTokenException(exception.getType(), exception.getTitle(), exception.getMessage());
        } catch (UserNotFoundException exception) {
            throw new ApiUserNotFoundException(exception.getType(), exception.getTitle(), exception.getMessage());
        }
    }
}
