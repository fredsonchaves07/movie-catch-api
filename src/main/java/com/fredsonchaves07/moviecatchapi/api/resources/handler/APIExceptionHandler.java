package com.fredsonchaves07.moviecatchapi.api.resources.handler;

import com.fredsonchaves07.moviecatchapi.api.resources.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> badRequestException(BadRequestException error) {
        StandardError standardError = new StandardError();
        standardError.setCodStatus(error.getCodStatus());
        standardError.setMessage(error.getMessage());
        return ResponseEntity.status(standardError.getCodStatus()).body(standardError);
    }
}
