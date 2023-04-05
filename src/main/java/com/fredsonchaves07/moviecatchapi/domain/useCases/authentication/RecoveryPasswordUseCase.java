package com.fredsonchaves07.moviecatchapi.domain.useCases.authentication;

import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.RecoveryPasswordDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailOrPasswordInvalidException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UnconfirmedUserException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RecoveryPasswordUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO execute(RecoveryPasswordDTO recoveryPasswordDTO) {
        User user = getUserByRecoverPasswordDTO(recoveryPasswordDTO);
        user.setPassword(recoveryPasswordDTO.getPassword());
        if (!user.isEmailAndPasswordValid())
            throw new EmailOrPasswordInvalidException();
        user.setPassword(passwordEncoder.encode(recoveryPasswordDTO.getPassword()));
        return new UserDTO(user);
    }

    private User getUserByRecoverPasswordDTO(RecoveryPasswordDTO recoveryPasswordDTO) {
        User user = userRepository.findByEmail(recoveryPasswordDTO.getEmail()).orElseThrow(UserNotFoundException::new);
        if (!user.isConfirm())
            throw new UnconfirmedUserException();
        return user;
    }
}
