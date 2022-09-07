package com.fredsonchaves07.moviecatchapi.api.services.mail;

import com.fredsonchaves07.moviecatchapi.api.services.email.FakeSendMailService;
import com.fredsonchaves07.moviecatchapi.domain.service.mail.SendEmailService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SendEmailTest {

    private SendEmailService sendEmailService = new FakeSendMailService();

    @Test
    public void shouldSendEmail() {
        String email = "user@email.com";
        String subject = "Teste de envio de email";
        String content = "Testando envio de email";
        assertDoesNotThrow(() -> sendEmailService.send(subject, email, content));
    }
}
