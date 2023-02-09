package com.fredsonchaves07.moviecatchapi.domain.useCases.authentication;

import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    private User user;

    public UserDTO execute(LoginDTO loginDTO) {
        String email = loginDTO.email();
        if (!emailAlreadyExist(email)) return null;
        user = userRepository.findByEmail(loginDTO.email());
        return new UserDTO(user);
    }

    private void validateLogin(LoginDTO loginDTO) {
        if (!emailAlreadyExist(loginDTO.email())) ; //Throw Exception
        if (!emailAndPasswordIsValid(loginDTO.email(), loginDTO.password())) ; //Throw Exception;
    }

    private boolean emailAlreadyExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean isEmailPasswordMatch(String email, String password) {
        user = userRepository.findByEmail(email);
        return user.getPassword().
    }
}
