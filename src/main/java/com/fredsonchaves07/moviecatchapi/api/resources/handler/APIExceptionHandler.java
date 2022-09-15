package com.fredsonchaves07.moviecatchapi.api.resources.handler;

import com.fredsonchaves07.moviecatchapi.api.resources.exception.*;
import com.fredsonchaves07.moviecatchapi.api.resources.logger.ApiErrorLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> badRequestException(BadRequestException error, WebRequest request) {
        String instance = ((ServletWebRequest) request).getRequest().getRequestURI();
        StandardError standardError = getStandardError(error, instance);
        return ResponseEntity.status(standardError.getStatus()).body(standardError);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<StandardError> notFoundException(BadRequestException error, WebRequest request) {
        String detail = "Resource id" + request.getContextPath() + " not found";
        String instance = ((ServletWebRequest) request).getRequest().getRequestURI();
        ResourceNotFoundException notFoundException = new ResourceNotFoundException(detail);
        StandardError standardError = getStandardError(notFoundException, instance);
        return ResponseEntity.status(standardError.getStatus()).body(standardError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> methodNotAllowed(HttpRequestMethodNotSupportedException error, WebRequest request) {
        MethodNotAllowedException exception = new MethodNotAllowedException();
        String instance = ((ServletWebRequest) request).getRequest().getRequestURI();
        StandardError standardError = getStandardError(exception, instance);
        return ResponseEntity.status(standardError.getStatus()).body(standardError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> serverError(ServerErrorException error, WebRequest request) {
        String instance = ((ServletWebRequest) request).getRequest().getRequestURI();
        StandardError standardError = getStandardError(error, instance);
        ApiErrorLogger.generateLog(error);
        return ResponseEntity.status(standardError.getStatus()).body(standardError);
    }

    private StandardError getStandardError(ApiException error, String instance) {
        StandardError standardError = new StandardError();
        standardError.setStatus(error.getStatus());
        standardError.setType(error.getType());
        standardError.setTitle(error.getTitle());
        standardError.setDetail(error.getDetail());
        standardError.setInstance(instance);
        return standardError;
    }
}
