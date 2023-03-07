package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.email.MessageEmailDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.Role;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailAlreadyExistException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailOrPasswordInvalidException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.NameInvalidException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.RoleRepository;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.mail.SendEmailService;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Component
public class CreateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${api.url.confirm.user}")
    private String apiURL;

    private User user;

    private UserDTO userDTO;

    @Transactional
    public UserDTO execute(CreateUserDTO createUserDTO) {
        createUser(createUserDTO);
        validateUser();
        encryptPassword();
        saveUser();
        sendMail();
        return userDTO;
    }

    private void createUser(CreateUserDTO createUserDTO) {
        String name = createUserDTO.getName();
        String email = createUserDTO.getEmail();
        String password = createUserDTO.getPassword();
        Role role = roleRepository.findUserRole();
        user = new User(name, email, password, role);
    }

    private void validateUser() {
        if (!user.isNameValid()) throw new NameInvalidException();
        if (!user.isEmailAndPasswordValid()) throw new EmailOrPasswordInvalidException();
        if (emailAlreadyExist()) throw new EmailAlreadyExistException();
    }


    private boolean emailAlreadyExist() {
        return userRepository.findByEmail(user.getEmail()) != null;
    }

    private void saveUser() {
        userRepository.save(user);
        userDTO = new UserDTO(user);
    }

    private void encryptPassword() {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private void sendMail() {
        String token = getToken();
        String subject = "Welcome to MovieCatch!";
        MessageEmailDTO messageEmail = new MessageEmailDTO(subject, user.getEmail());
        HashMap<String, Object> templateParams = createTemplateMail(token, messageEmail);
        sendEmailService.send(messageEmail, "welcome_mail", templateParams);
    }

    private String getToken() {
        return tokenService.encrypt(userDTO).token();
    }

    private HashMap<String, Object> createTemplateMail(String token, MessageEmailDTO messageEmailDTO) {
        HashMap<String, Object> templateParams = new HashMap<>();
        templateParams.put("url", apiURL + "/" + token);
        templateParams.put("mesmessageEmail", messageEmailDTO);
        return templateParams;
    }
}
