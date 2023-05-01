package com.fredsonchaves07.moviecatchapi.api.services.user;

import com.fredsonchaves07.moviecatchapi.api.exception.UnauthorizedException;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UpdateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailOrPasswordIncorrectException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.user.ManagementInfoUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagementInfoUserApiService {

    @Autowired
    private ManagementInfoUserUseCase managementInfoUserUseCase;

    public UserDTO execute(UserDTO userDTO, UpdateUserDTO updateUserDTO) {
        try {
            return managementInfoUserUseCase.execute(userDTO, updateUserDTO);
        } catch (EmailOrPasswordIncorrectException emailOrPasswordIncorrectException) {
            throw new UnauthorizedException(emailOrPasswordIncorrectException);
        }
    }
}
