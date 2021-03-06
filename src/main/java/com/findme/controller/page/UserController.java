package com.findme.controller.page;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {
    UserService userService;
    PostService postService;

    private static final Logger log = LogManager.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }


    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable String userId, HttpSession session) throws BadRequestException, UserNotFoundException, InternalServerException {

        Long id = toLong(userId);
        User userNow = (User) session.getAttribute("user");
        if (id.equals(userNow.getId()))
            return MyProfile(model, session);
        User user = userService.profile(id);
        model.addAttribute("user", user);
        session.setAttribute("lustUserPage", user);
        return "profile";
    }


    @RequestMapping(value = "myProfile", method = RequestMethod.GET)
    public String MyProfile(Model model, HttpSession session) throws BadRequestException, InternalServerException {
        try {
            if (session.getAttribute("user") == null) {
                log.error("Error");
                return "Error";
            }
            User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
            model.addAttribute("incomingRequests", userService.getIncomingRequests(user.getId()));
            model.addAttribute("outgoingRequests", userService.getOutcomeRequests(user.getId()));
            List friends = userService.getFriends(user.getId());
            session.setAttribute("friends", friends);
            model.addAttribute("friends", friends);
            String filterStatus = null;
            if (session.getAttribute("filterPostsUserId") != null)
                filterStatus = session.getAttribute("filterPostsUserId").toString();
            model.addAttribute("Posts", postService.getPosts(filterStatus, user.getId()));

            return "MyProfile";
        } finally {
            session.setAttribute("filterPostsUserId", null);
        }
    }


    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ResponseEntity<String> logoutUser(HttpSession session) {
        if (session.getAttribute("user") == null)
            return new ResponseEntity<>("user is still online", HttpStatus.INTERNAL_SERVER_ERROR);
        session.setAttribute("user", null);
        log.info("user is loggouted");
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/saveUser", produces = "text/plain")
    public @ResponseBody
    User doPost(HttpServletRequest req) throws InternalServerException, BadRequestException, IOException {
        try (BufferedReader br = req.getReader()) {
            User user = toJavaObject(br);
            return userService.save(user);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findUser", produces = "text/plain")
    public @ResponseBody
    User doGet(HttpServletRequest req) throws InternalServerException, IOException {
        try (BufferedReader br = req.getReader()) {
            User user = toJavaObject(br);
            return userService.findById(user.getId());
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateUser", produces = "text/plain")
    public @ResponseBody
    User doPut(HttpServletRequest req) throws IOException{
        try (BufferedReader br = req.getReader()) {
            User user = toJavaObject(br);
            return userService.update(user);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteUser", produces = "text/plain")
    public @ResponseBody
    User doDelete(HttpServletRequest req)  throws IOException {
        try (BufferedReader br = req.getReader()) {
            User user = toJavaObject(br);
            return userService.delete(user);
        }
    }

    private User toJavaObject(BufferedReader br) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(br, User.class);
    }

    private Long toLong(String text) throws BadRequestException {
        try {
            Long number = Long.parseLong(text);
            return number;
        } catch (NumberFormatException e) {
            throw new BadRequestException("Bad request");
        }
    }

}
