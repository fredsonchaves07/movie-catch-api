package com.fredsonchaves07.moviecatchapi.application.services;

import com.fredsonchaves07.moviecatchapi.application.doubles.FakeSendMail;
import com.fredsonchaves07.moviecatchapi.domain.service.SendEmailService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SendEmailTest {

    private SendEmailService sendEmailService = new FakeSendMail();

    @Test
    public void shouldSendEmail() {
        String email = "user@email.com";
        String subject = "Teste de envio de email";
        String content = "Testando envio de email";
        assertDoesNotThrow(() -> sendEmailService.send(subject, email, content));
    }
}
