package com.fredsonchaves07.moviecatchapi.domain.useCases.authentication;

import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailOrPasswordIncorrectException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UnconfirmedUserException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService jwtService;

    private User user;

    public TokenDTO execute(LoginDTO loginDTO) {
        return authenticateUser(loginDTO);
    }

    private TokenDTO authenticateUser(LoginDTO loginDTO) {
        validateLoginDTO(loginDTO);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );
        String tokenEncrypted = jwtService.encrypt(Optional.of(new UserDTO(null, loginDTO.getEmail()))).getToken();
        return new TokenDTO(tokenEncrypted);
    }

    private void validateLoginDTO(LoginDTO loginDTO) {
        if (loginDTO == null)
            throw new EmailOrPasswordIncorrectException();
        if (loginDTO.getEmail() == null || loginDTO.getPassword() == null)
            throw new EmailOrPasswordIncorrectException();
        if (!emailAlreadyExist(loginDTO.getEmail()))
            throw new EmailOrPasswordIncorrectException();
        if (!isEmailPasswordMatch(loginDTO.getEmail(), loginDTO.getPassword()))
            throw new EmailOrPasswordIncorrectException();
        if (!userIsConfirmed())
            throw new UnconfirmedUserException();
    }

    private boolean emailAlreadyExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean isEmailPasswordMatch(String email, String password) {
        user = userRepository.findByEmail(email).orElseThrow();
        return passwordEncoder.matches(password, user.getPassword());
    }

    private boolean userIsConfirmed() {
        return user.isConfirm();
    }
}
