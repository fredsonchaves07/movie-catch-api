package com.fredsonchaves07.moviecatchapi.application.services;

import com.fredsonchaves07.moviecatchapi.domain.service.SendEmailService;
import com.fredsonchaves07.moviecatchapi.domain.service.exception.SendEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Profile("dev")
public class SpringSendEmailService implements SendEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${support.email}")
    private String supportMail;

    @Override
    public void send(String subject, String email, String content) throws SendEmailException {
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mail);
            message.setSubject(subject);
            message.setText(content, false);
            message.setFrom(supportMail);
            message.setTo(email);
            mailSender.send(mail);
        } catch (MessagingException exception) {
            throw new SendEmailException(exception.getMessage());
        }
    }
}
