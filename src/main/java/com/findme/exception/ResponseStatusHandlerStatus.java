package com.findme.exception;

import com.findme.controller.status.PostControllerStatus;
import com.findme.controller.status.RelationshipControllerStatus;
import com.findme.controller.status.UserControllerStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ControllerAdvice(basePackageClasses = {UserControllerStatus.class, PostControllerStatus.class, RelationshipControllerStatus.class})
public class ResponseStatusHandlerStatus {
    private static final Logger log = LogManager.getLogger(ResponseStatusHandlerStatus.class);

    @ExceptionHandler(value = UserNotFoundException.class)
    public HttpStatus notFoundError (HttpServletRequest req, Exception e){
        log.error(e.getMessage());
        return HttpStatus.NOT_FOUND;

    }

    @ExceptionHandler(value = BadRequestException.class)
    public HttpStatus badRequestError (HttpServletRequest req, Exception e){
        log.error(e.getMessage());
        return HttpStatus.BAD_REQUEST;
    }

    @ExceptionHandler(value = InternalServerException.class)
    public HttpStatus internalServerError (HttpServletRequest req, Exception e){
        log.error(e.getMessage());
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
//
//    @ExceptionHandler(value = IOException.class)
//    public ModelAndView ioError (HttpServletRequest req, Exception e){
//       return notFoundError(req, e);
//    }


}
