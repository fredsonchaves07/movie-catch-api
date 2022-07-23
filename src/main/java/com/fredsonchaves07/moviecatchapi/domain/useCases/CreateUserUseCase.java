package com.fredsonchaves07.moviecatchapi.domain.useCases;

import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;

public class CreateUserUseCase {

    private UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute() {
//        User user = UserFactory.createUser();
    }
}
