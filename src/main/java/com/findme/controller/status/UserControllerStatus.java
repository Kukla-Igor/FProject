package com.findme.controller.status;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.exception.UserNotFoundException;
import com.findme.models.User;
import com.findme.service.PostService;
import com.findme.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserControllerStatus {
    UserService userService;
    PostService postService;

    private static final Logger log = LogManager.getLogger(UserControllerStatus.class);

    @Autowired
    public UserControllerStatus(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }



    @RequestMapping(value = "user-registration", method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(User user) throws BadRequestException, InternalServerException {
        userService.save(user);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<String> loginUser(HttpSession session, @RequestBody Map<String, String> params) throws UserNotFoundException, BadRequestException, InternalServerException {
        if (params.get("password").isEmpty() || (params.get("phone").isEmpty()))
            return new ResponseEntity<>("empty field", HttpStatus.BAD_REQUEST);
        if (session.getAttribute("user") != null)
            return new ResponseEntity<>("the user is already logged in", HttpStatus.BAD_REQUEST);
        User user = userService.loginUser(params.get("phone"), params.get("password"));
        session.setAttribute("user", user);
        log.info("user is logged");
        return new ResponseEntity<>("OK", HttpStatus.OK);

    }


}
