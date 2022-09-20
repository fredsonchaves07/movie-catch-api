package com.fredsonchaves07.moviecatchapi.api.services.user;

import com.fredsonchaves07.moviecatchapi.api.services.exception.CreateUserUseCaseException;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.mail.SendEmailService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.CreateUserUseCase;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.DomainUseCaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserAPIService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
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
        } catch (DomainUseCaseException exception) {
            throw new CreateUserUseCaseException(exception.getType(), exception.getMessage(), exception.getMessage());
        }
    }
}
