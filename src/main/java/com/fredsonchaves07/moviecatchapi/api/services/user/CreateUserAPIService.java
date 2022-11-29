package com.fredsonchaves07.moviecatchapi.api.services.user;

import com.fredsonchaves07.moviecatchapi.api.services.exception.CreateUserUseCaseException;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.DomainUseCaseException;
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
        } catch (DomainUseCaseException exception) {
            throw new CreateUserUseCaseException(exception.getType(), exception.getTitle(), exception.getMessage());
        }
    }
}
