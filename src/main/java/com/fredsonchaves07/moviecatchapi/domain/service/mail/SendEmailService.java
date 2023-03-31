package com.fredsonchaves07.moviecatchapi.domain.service.mail;

import com.fredsonchaves07.moviecatchapi.domain.dto.email.MessageEmailDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.SendEmailException;

import java.util.Objects;

public interface SendEmailService {

    void send(MessageEmailDTO message) throws SendEmailException;

    default boolean isEmailValid(MessageEmailDTO messageEmailDTO) {
        return Objects.nonNull(messageEmailDTO) &&
                Objects.nonNull(messageEmailDTO.subject()) &&
                Objects.nonNull(messageEmailDTO.email());
    }
}
