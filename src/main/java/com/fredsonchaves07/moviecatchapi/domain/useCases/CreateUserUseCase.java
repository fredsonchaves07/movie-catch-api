package com.fredsonchaves07.moviecatchapi.domain.useCases;

import com.fredsonchaves07.moviecatchapi.domain.dto.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.SendEmailService;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.EmailAlreadyExistException;
import com.fredsonchaves07.moviecatchapi.domain.useCases.exceptions.EmailOrPasswordInvalidException;

import java.util.regex.Pattern;

public class CreateUserUseCase {

    private static final String EMAIL_PATTERN = "" +
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)" +
            "*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\." +
            "[A-Za-z]{2,})$";

    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    private UserRepository userRepository;

    private SendEmailService sendEmailService;

    public CreateUserUseCase(UserRepository userRepository, SendEmailService sendEmailService) {
        this.userRepository = userRepository;
        this.sendEmailService = sendEmailService;
    }

    public UserDTO execute(CreateUserDTO createUserDTO) {
        String name = createUserDTO.getName();
        String email = createUserDTO.getEmail();
        String password = createUserDTO.getPassword();
        if (emailAlreadyExist(email))
            throw new EmailAlreadyExistException();
        if (!isEmailAndPasswordValid(email, password))
            throw new EmailOrPasswordInvalidException();
        UserDTO user = createUser(name, email, password);
        sendMail(user.getEmail());
        return user;
    }

    private boolean emailAlreadyExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean isEmailAndPasswordValid(String email, String password) {
        return emailIsValid(email) && passwordIsValid(password);
    }

    private boolean emailIsValid(String email) {
        return PATTERN.matcher(email).matches();
    }

    private boolean passwordIsValid(String password) {
        return password.length() >= 8 && (!password.contains(" "));
    }

    private UserDTO createUser(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return new UserDTO(user);
    }

    private void sendMail(String email) {
        String content = "Olá! Tudo bem?\nPara confirmar seu cadastro por favor clique no link abaixo\n";
        String subject = "Bem vindo ao moviecatch";
        sendEmailService.send(subject, email, content);
    }
}
