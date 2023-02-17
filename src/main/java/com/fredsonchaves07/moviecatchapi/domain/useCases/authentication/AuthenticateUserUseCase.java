package com.fredsonchaves07.moviecatchapi.domain.useCases.authentication;

import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailOrPasswordIncorrectException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService jwtService;

    private User user;

    public TokenDTO execute(LoginDTO loginDTO) {
        return authenticateUser(loginDTO);
    }

    private TokenDTO authenticateUser(LoginDTO loginDTO) {
        if (!emailAlreadyExist(loginDTO.email())) throw new EmailOrPasswordIncorrectException();
        if (!isEmailPasswordMatch(loginDTO.email(), loginDTO.password())) throw new EmailOrPasswordIncorrectException();
        return new TokenDTO(jwtService.encrypt(new UserDTO(loginDTO.email(), loginDTO.email())).token());
    }

    private boolean emailAlreadyExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean isEmailPasswordMatch(String email, String password) {
        user = userRepository.findByEmail(email);
        return passwordEncoder.matches(user.getPassword(), passwordEncoder.encode(password));
    }
}
