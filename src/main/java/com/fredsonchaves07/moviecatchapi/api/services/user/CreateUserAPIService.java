package com.fredsonchaves07.moviecatchapi.api.services.user;

import com.fredsonchaves07.moviecatchapi.api.services.exception.CreateUserUseCaseException;
import com.fredsonchaves07.moviecatchapi.domain.dto.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.SendEmailService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.CreateUserUseCase;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.EmailAlreadyExistException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.EmailOrPasswordInvalidException;
import org.springframework.stereotype.Service;

@Service
public class CreateUserAPIService {

    private UserRepository userRepository;

    private SendEmailService sendEmailService;

    private CreateUserUseCase createUserUseCase;

    public CreateUserAPIService(UserRepository userRepository, SendEmailService sendEmailService) {
        this.userRepository = userRepository;
        this.sendEmailService = sendEmailService;
        this.createUserUseCase = new CreateUserUseCase(userRepository, sendEmailService);
    }

    public UserDTO execute(CreateUserDTO createUserDTO) {
        try {
            return createUserUseCase.execute(createUserDTO);
        } catch (EmailAlreadyExistException | EmailOrPasswordInvalidException exception) {
            throw new CreateUserUseCaseException(exception.getMessage());
        }
    }
}
