package com.fredsonchaves07.moviecatchapi.api.services.email;

import com.fredsonchaves07.moviecatchapi.domain.dto.email.MessageEmailDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.SendEmailException;
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
    public void send(MessageEmailDTO messageEmailDTO) throws SendEmailException {
        System.out.println("Send fake mail");
        System.out.println("From: " + supportMail);
        System.out.println("Subject: " + messageEmailDTO.subject());
        System.out.println("To: " + messageEmailDTO.email());
        System.out.println("Content: " + messageEmailDTO.content());
    }
}
