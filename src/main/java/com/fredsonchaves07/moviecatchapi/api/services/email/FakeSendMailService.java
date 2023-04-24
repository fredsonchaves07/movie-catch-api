package com.fredsonchaves07.moviecatchapi.api.services.email;

import com.fredsonchaves07.moviecatchapi.domain.dto.email.MessageEmailDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.SendEmailException;
import com.fredsonchaves07.moviecatchapi.domain.service.mail.SendEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile({"dev, test"})
public class FakeSendMailService implements SendEmailService {

    private String supportMail = "admin@moviecatch.com";

    final Logger logger = LoggerFactory.getLogger(FakeSendMailService.class);

    @Override
    public void send(MessageEmailDTO messageEmailDTO) throws SendEmailException {
        if (!isEmailValid(messageEmailDTO)) throw new SendEmailException();
        logger.info("Send fake mail");
        logger.info("From: " + supportMail);
        logger.info("Subject: " + messageEmailDTO.subject());
        logger.info("To: " + messageEmailDTO.email());
        logger.info("Content: Fake send mail successful!");
    }
}
