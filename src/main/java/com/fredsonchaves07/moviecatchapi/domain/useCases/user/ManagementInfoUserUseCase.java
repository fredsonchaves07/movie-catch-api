package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UpdateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UnconfirmedUserException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class ManagementInfoUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Transactional
    public UserDTO execute(TokenDTO token, UpdateUserDTO updateUserDTO) {
        String email = tokenService.decrypt(Optional.ofNullable(token));
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        if (!user.isConfirm())
            throw new UnconfirmedUserException();
        if (updateUserDTO.name() != null)
            user.setName(updateUserDTO.name());
        if (updateUserDTO.password() != null)
            user.setPassword(passwordEncoder.encode(updateUserDTO.password()));
        return new UserDTO(userRepository.save(user));
    }
}
