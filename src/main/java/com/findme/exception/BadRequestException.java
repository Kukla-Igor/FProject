package com.findme.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "body")
public class BadRequestException extends Exception {
    private static final Logger log = LogManager.getLogger(BadRequestException.class);

    public BadRequestException(String message) {
        super(message);
        log.error(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
        log.error(message);
    }

    public BadRequestException() {
        super();
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }
}
