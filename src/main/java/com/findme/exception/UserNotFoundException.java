package com.findme.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "body")
public class UserNotFoundException extends Exception {
    private static final Logger log = LogManager.getLogger(UserNotFoundException.class);

    public UserNotFoundException(String message) {
        super(message);
        log.error(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
        log.error(message);
    }

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
