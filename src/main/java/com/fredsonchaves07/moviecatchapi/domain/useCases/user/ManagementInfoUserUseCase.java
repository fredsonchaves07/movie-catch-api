package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.user.UpdateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailOrPasswordIncorrectException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UnconfirmedUserException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ManagementInfoUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO execute(UserDTO userDTO, UpdateUserDTO updateUserDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            throw new EmailOrPasswordIncorrectException();
        String userEmail = (String) authentication.getPrincipal();
        if (!userDTO.getEmail().equals(userEmail) || userEmail.equals("anonymousUser"))
            throw new EmailOrPasswordIncorrectException();
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        if (!user.isConfirm())
            throw new UnconfirmedUserException();
        if (updateUserDTO.name() != null)
            user.setName(updateUserDTO.name());
        if (updateUserDTO.password() != null)
            user.setPassword(passwordEncoder.encode(updateUserDTO.password()));
        return new UserDTO(userRepository.save(user));
    }
}
