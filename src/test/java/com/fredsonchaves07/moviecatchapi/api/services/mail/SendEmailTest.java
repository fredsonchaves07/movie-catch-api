package com.fredsonchaves07.moviecatchapi.api.services.mail;

import com.fredsonchaves07.moviecatchapi.api.services.email.FakeSendMailService;
import com.fredsonchaves07.moviecatchapi.domain.dto.email.MessageEmailDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.SendEmailException;
import com.fredsonchaves07.moviecatchapi.domain.service.mail.SendEmailService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SendEmailTest {

    private SendEmailService sendEmailService = new FakeSendMailService();

    @Test
    public void shouldSendEmail() {
        String email = "user@email.com";
        String subject = "Teste de envio de email";
        String content = "Testando envio de email";
        assertDoesNotThrow(() -> sendEmailService.send(new MessageEmailDTO(subject, email, content)));
    }

    @Test
    public void notShouldSendEmailIfEmailIsNull() {
        String subject = "Teste de envio de email";
        String content = "Testando envio de email";
        assertThrows(
                SendEmailException.class,
                () -> sendEmailService.send(new MessageEmailDTO(subject, null, content))
        );
    }
}
