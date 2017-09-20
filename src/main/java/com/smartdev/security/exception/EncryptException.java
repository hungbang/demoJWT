package com.smartdev.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = "Error when encrypt or decrypt key/content.")
public class EncryptException extends RuntimeException {

    public EncryptException() {
    }

    public EncryptException(String message) {
        super(message);
    }

    public EncryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncryptException(Throwable cause) {
        super(cause);
    }

    public EncryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
