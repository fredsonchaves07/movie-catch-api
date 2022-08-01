package com.fredsonchaves07.moviecatchapi.application.services.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.SendEmailService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.CreateUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserAPIService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private CreateUserUseCase createUserUseCase;

//    public CreateUserAPIService(UserRepository userRepository, SendEmailService sendEmailService) {
//        this.userRepository = userRepository;
//        this.sendEmailService = sendEmailService;
//        this.createUserUseCase = new CreateUserUseCase(userRepository, sendEmailService);
//    }

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        return createUserUseCase.execute(createUserDTO);
    }
}
