package com.fredsonchaves07.moviecatchapi.api.services.user;

import com.fredsonchaves07.moviecatchapi.api.exception.BadRequestException;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailAlreadyExistException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailOrPasswordInvalidException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.NameInvalidException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.CreateUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserAPIService {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    public UserDTO execute(CreateUserDTO createUserDTO) {
        try {
            return createUserUseCase.execute(createUserDTO);
        } catch (NameInvalidException | EmailOrPasswordInvalidException | EmailAlreadyExistException exception) {
            throw new BadRequestException(exception.getType(), exception.getTitle(), exception.getMessage());
        }

    }
}
