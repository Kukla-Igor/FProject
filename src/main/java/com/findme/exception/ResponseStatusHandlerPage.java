package com.findme.exception;

import com.findme.controller.page.PostController;
import com.findme.controller.page.UserController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ControllerAdvice (basePackageClasses = {UserController.class, PostController.class})
public class ResponseStatusHandlerPage {
    private static final Logger log = LogManager.getLogger(ResponseStatusHandlerPage.class);

    @ExceptionHandler(value = UserNotFoundException.class)
    public ModelAndView notFoundError (HttpServletRequest req, Exception e){
        log.error(e.getMessage());
        ModelAndView modelAndView =  new ModelAndView("Error");
        return modelAndView;

    }

    @ExceptionHandler(value = BadRequestException.class)
    public ModelAndView badRequestError (HttpServletRequest req, Exception e){
        log.error(e.getMessage());
        ModelAndView modelAndView =  new ModelAndView("Error");
        return modelAndView;
    }

    @ExceptionHandler(value = InternalServerException.class)
    public ModelAndView internalServerError (HttpServletRequest req, Exception e){
        log.error(e.getMessage());
        ModelAndView modelAndView =  new ModelAndView("Error");
        return modelAndView;
    }

    @ExceptionHandler(value = IOException.class)
    public ModelAndView ioError (HttpServletRequest req, Exception e){
       return null;
    }


}
