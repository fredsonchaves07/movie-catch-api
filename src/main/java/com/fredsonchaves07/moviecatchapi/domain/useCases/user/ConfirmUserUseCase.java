package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.UserAlreadyConfirmedException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class ConfirmUserUseCase {

    private UserRepository userRepository;

    private User user;

    public ConfirmUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UserDTO userDTO) {
        user = userRepository.findByEmail(userDTO.getEmail());
        if (userIsNotFound()) throw new UserNotFoundException();
        if (isConfirmed()) throw new UserAlreadyConfirmedException();
        user.confirmUser();
    }

    public boolean isConfirmed() {
        return user.isConfirm();
    }

    public boolean userIsNotFound() {
        return user == null;
    }
}
