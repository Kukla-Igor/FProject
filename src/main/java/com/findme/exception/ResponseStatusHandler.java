//package com.findme.exception;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.ModelAndView;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@ControllerAdvice
//public class ResponseStatusHandler {
//    private static final Logger log = LogManager.getLogger(ResponseStatusHandler.class);
//
//    @ExceptionHandler(value = UserNotFoundException.class)
//    public ModelAndView notFoundError (HttpServletRequest req, Exception e){
//        log.error(e.getMessage());
//        ModelAndView modelAndView =  new ModelAndView("Error");
//        modelAndView.addObject("error_message", e.getMessage());
//        modelAndView.setStatus(HttpStatus.NOT_FOUND);
//        return modelAndView;
//
//    }
////
////    @ExceptionHandler(value = BadRequestException.class)
////    public ModelAndView badRequestError (HttpServletRequest req, Exception e){
////        log.error(e.getMessage());
////        ModelAndView modelAndView =  new ModelAndView();
////        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
////        req.getHttpServletMapping();
////        return modelAndView;
////    }
//
//    @ExceptionHandler(value = InternalServerException.class)
//    public ModelAndView internalServerError (HttpServletRequest req, Exception e){
//        log.error(e.getMessage());
//        ModelAndView modelAndView =  new ModelAndView("Error");
//        modelAndView.addObject("error_message", e.getMessage());
//        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//        return modelAndView;
//    }
//
//    @ExceptionHandler(value = IOException.class)
//    public ModelAndView ioError (HttpServletRequest req, Exception e){
//       return notFoundError(req, e);
//    }
//
//
//}
