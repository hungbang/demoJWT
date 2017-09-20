package com.smartdev.security.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class EncryptExceptionProcessor {

    @ExceptionHandler(value = { EncryptException.class })
    protected ResponseEntity<Object> handleNotImplemented(EncryptException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_IMPLEMENTED, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(EncryptException ex, String bodyOfResponse, HttpHeaders httpHeaders, HttpStatus conflict, WebRequest request) {
        return new ResponseEntity<>(ex, conflict);
    }


}
