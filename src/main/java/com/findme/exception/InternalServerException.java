package com.findme.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "body")
public class InternalServerException extends Exception {
    private static final Logger log = LogManager.getLogger(InternalServerException.class);

    public InternalServerException(String message) {
        super(message);
        log.error(message);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
        log.error(message);
    }

    public InternalServerException() {
        super();
    }

    public InternalServerException(Throwable cause) {
        super(cause);
    }
}

