package com.fredsonchaves07.moviecatchapi.api.services.user;

import com.fredsonchaves07.moviecatchapi.api.exception.ResourceNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.EmailDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.ChangeRequestPasswordUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangeRequestPasswordUserApiService {

    @Autowired
    private ChangeRequestPasswordUserUseCase useCase;

    public void execute(EmailDTO email) {
        try {
            useCase.execute(email);
        } catch (UserNotFoundException domainException) {
            throw new ResourceNotFoundException(domainException);
        }
    }
}
