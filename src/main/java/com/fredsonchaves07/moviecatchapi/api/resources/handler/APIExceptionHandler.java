package com.fredsonchaves07.moviecatchapi.api.resources.handler;

import com.fredsonchaves07.moviecatchapi.api.exception.*;
import com.fredsonchaves07.moviecatchapi.api.resources.logger.ApiErrorLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import springfox.documentation.annotations.ApiIgnore;

@ControllerAdvice
@ApiIgnore
public class APIExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> badRequestException(BadRequestException error, WebRequest request) {
        String instance = ((ServletWebRequest) request).getRequest().getRequestURI();
        StandardError standardError = getStandardError(error, instance);
        return ResponseEntity.status(standardError.getStatus()).body(standardError);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<StandardError> notFoundException(NoHandlerFoundException error, WebRequest request) {
        String instance = ((ServletWebRequest) request).getRequest().getRequestURI();
        String detail = "Resource " + instance + " not found. Verify the resource documentation";
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> propertyValueException(HttpMessageNotReadableException error, WebRequest request) {
        UnknownPropertyInvalidException exception = new UnknownPropertyInvalidException();
        String instance = ((ServletWebRequest) request).getRequest().getRequestURI();
        StandardError standardError = getStandardError(exception, instance);
        return ResponseEntity.status(standardError.getStatus()).body(standardError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> serverError(Exception error, WebRequest request) {
        ServerErrorException exception = new ServerErrorException();
        String instance = ((ServletWebRequest) request).getRequest().getRequestURI();
        StandardError standardError = getStandardError(exception, instance);
        ApiErrorLogger.generateLog(error);
        return ResponseEntity.status(standardError.getStatus()).body(standardError);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<StandardError> apiException(ApiException error, WebRequest request) {
        String instance = ((ServletWebRequest) request).getRequest().getRequestURI();
        StandardError standardError = getStandardError(error, instance);
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
