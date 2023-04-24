package com.fredsonchaves07.moviecatchapi.domain.useCases.authentication;

import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.email.MessageEmailDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.EmailOrPasswordInvalidException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UnconfirmedUserException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.mail.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Component
public class RecoveryPasswordUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SendEmailService sendEmailService;

    @Value("${api.url.login}")
    private String apiURL;

    @Transactional
    public UserDTO execute(LoginDTO loginDTO) {
        User user = getUserByLoginDTO(loginDTO);
        user.setPassword(loginDTO.password());
        if (!user.isEmailAndPasswordValid())
            throw new EmailOrPasswordInvalidException();
        user.setPassword(passwordEncoder.encode(loginDTO.password()));
        sendMail(user);
        return new UserDTO(user);
    }

    private User getUserByLoginDTO(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.email()).orElseThrow(UserNotFoundException::new);
        if (!user.isConfirm())
            throw new UnconfirmedUserException();
        return user;
    }

    private void sendMail(User user) {
        String subject = "Password Changed!";
        HashMap<String, Object> mailParams = createPasswordChangedTemplateMail();
        MessageEmailDTO messageEmail = new MessageEmailDTO(subject, user.getEmail(), mailParams);
        sendEmailService.send(messageEmail);
    }

    private HashMap<String, Object> createPasswordChangedTemplateMail() {
        HashMap<String, Object> templateParams = new HashMap<>();
        templateParams.put("template", "password_changed");
        templateParams.put("url", apiURL);
        return templateParams;
    }
}
