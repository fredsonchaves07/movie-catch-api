package com.fredsonchaves07.moviecatchapi.domain.useCases;

import com.fredsonchaves07.moviecatchapi.domain.dto.user.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.mail.SendEmailService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.EmailAlreadyExistException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.EmailOrPasswordInvalidException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.NameInvalidException;

public class CreateUserUseCase {

    private UserRepository userRepository;

    private SendEmailService sendEmailService;

    private User user;

    public CreateUserUseCase(UserRepository userRepository, SendEmailService sendEmailService) {
        this.userRepository = userRepository;
        this.sendEmailService = sendEmailService;
    }

    public UserDTO execute(CreateUserDTO createUserDTO) {
        createUser(createUserDTO);
        validateUser();
        sendMail(user.getEmail());
        return saveUser();
    }

    private void createUser(CreateUserDTO createUserDTO) {
        String name = createUserDTO.getName();
        String email = createUserDTO.getEmail();
        String password = createUserDTO.getPassword();
        user = new User(name, email, password);
    }

    private void validateUser() {
        if (!user.isNameValid()) throw new NameInvalidException();
        if (!user.isEmailAndPasswordValid()) throw new EmailOrPasswordInvalidException();
        if (emailAlreadyExist()) throw new EmailAlreadyExistException();
    }

    private boolean emailAlreadyExist() {
        return userRepository.findByEmail(user.getEmail()) != null;
    }

    private UserDTO saveUser() {
        userRepository.save(user);
        return new UserDTO(user);
    }

    private void sendMail(String email) {
        String content = "Olá! Tudo bem?\nPara confirmar seu cadastro por favor clique no link abaixo\n";
        String subject = "Bem vindo ao moviecatch";
        sendEmailService.send(subject, email, content);
    }
}
