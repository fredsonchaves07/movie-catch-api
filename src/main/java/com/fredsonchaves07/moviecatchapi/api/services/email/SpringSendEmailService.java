package com.fredsonchaves07.moviecatchapi.api.services.email;

import com.fredsonchaves07.moviecatchapi.domain.dto.email.MessageEmailDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.SendEmailException;
import com.fredsonchaves07.moviecatchapi.domain.service.mail.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Component
@Profile("prod")
public class SpringSendEmailService implements SendEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${support.email}")
    private String supportMail;

    @Override
    public void send(MessageEmailDTO messageEmailDTO) throws SendEmailException {
        try {
            if (!isEmailValid(messageEmailDTO)) throw new SendEmailException();
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(
                    mail, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name()
            );
            Map<String, Object> contextContent = new HashMap<>();
            Context context = new Context();
            context.setVariables(contextContent);
            String html = templateEngine.process(messageEmailDTO.params().get("template").toString(), context);
            message.setSubject(messageEmailDTO.subject());
            message.setText(html, true);
            message.setFrom(supportMail);
            message.setTo(messageEmailDTO.email());
            mailSender.send(mail);
        } catch (MessagingException exception) {
            throw new SendEmailException();
        }
    }
}
