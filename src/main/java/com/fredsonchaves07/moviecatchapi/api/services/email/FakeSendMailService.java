package com.fredsonchaves07.moviecatchapi.api.services.email;

import com.fredsonchaves07.moviecatchapi.domain.service.exception.SendEmailException;
import com.fredsonchaves07.moviecatchapi.domain.service.mail.SendEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile({"dev, test", "docker"})
public class FakeSendMailService implements SendEmailService {

    private String supportMail = "admin@moviecatch.com";

    final Logger logger = LoggerFactory.getLogger(FakeSendMailService.class);

    @Override
    public void send(String subject, String email, String content) throws SendEmailException {
        if (!isEmailValid(subject, email, content)) throw new SendEmailException();
        logger.info("Send fake mail");
        logger.info("From: " + supportMail);
        logger.info("Subject: " + subject);
        logger.info("To: " + email);
        logger.info("Content: " + content);
    }

    @Override
    public boolean isEmailValid(String subject, String email, String content) {
        return subject != null && email != null && content != null;
    }
}
