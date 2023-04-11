package com.fredsonchaves07.moviecatchapi.api.services.user;

import com.fredsonchaves07.moviecatchapi.api.exception.BadRequestException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.DomainException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.ChangeRequestPasswordUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangeRequestPasswordUserApiService {

    @Autowired
    private ChangeRequestPasswordUserUseCase changeRequestPasswordUserUseCase;

    public void execute(String email) {
        try {
            changeRequestPasswordUserUseCase.execute(email);
        } catch (DomainException domainException) {
            throw new BadRequestException(domainException);
        }
    }
}
