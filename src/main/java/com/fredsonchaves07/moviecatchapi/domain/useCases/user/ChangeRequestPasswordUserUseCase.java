package com.fredsonchaves07.moviecatchapi.domain.useCases.user;

import com.fredsonchaves07.moviecatchapi.domain.dto.email.MessageEmailDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.EmailDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import com.fredsonchaves07.moviecatchapi.domain.service.mail.SendEmailService;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
public class ChangeRequestPasswordUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SendEmailService sendEmailService;

    @Value("${api.url.recovery.user}")
    private String recoveryUserUrl;

    @Value("${api.url.confirm.user}")
    private String confirmUserUrl;

    public void execute(EmailDTO email) {
        if (email == null)
            throw new UserNotFoundException();
        User user = userRepository.findByEmail(email.email()).orElseThrow(UserNotFoundException::new);
        if (user.isConfirm())
            sendMailRequestPassword(user);
        sendMailConfirmUser(user);
    }

    private void sendMailRequestPassword(User user) {
        UserDTO userDTO = new UserDTO(user);
        String token = tokenService.encrypt(Optional.of(userDTO)).getToken();
        String subject = "Change Password";
        HashMap<String, Object> mailParams = createChangePasswordTemplateMail(token);
        MessageEmailDTO messageEmail = new MessageEmailDTO(subject, user.getEmail(), mailParams);
        sendEmailService.send(messageEmail);
    }

    private void sendMailConfirmUser(User user) {
        UserDTO userDTO = new UserDTO(user);
        String token = tokenService.encrypt(Optional.of(userDTO)).getToken();
        String subject = "Welcome to MovieCatch!";
        HashMap<String, Object> mailParams = createConfirmUserTemplateMail(token);
        MessageEmailDTO messageEmail = new MessageEmailDTO(subject, user.getEmail(), mailParams);
        sendEmailService.send(messageEmail);
    }

    private HashMap<String, Object> createChangePasswordTemplateMail(String token) {
        HashMap<String, Object> templateParams = new HashMap<>();
        templateParams.put("template", "change_password");
        templateParams.put("url", recoveryUserUrl + "/" + token);
        return templateParams;
    }

    private HashMap<String, Object> createConfirmUserTemplateMail(String token) {
        HashMap<String, Object> templateParams = new HashMap<>();
        templateParams.put("template", "welcome_mail");
        templateParams.put("url", confirmUserUrl + "/" + token);
        return templateParams;
    }
}
