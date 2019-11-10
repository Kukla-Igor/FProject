package com.findme.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.exception.UserNotFoundException;
import com.findme.models.User;
import com.findme.service.UserService;
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



@Controller
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable String userId, HttpSession session) {
        try {
            Long id = toLong(userId);
            User user = userService.profile(id);
            model.addAttribute("user", user);
            session.setAttribute("lustUserPage", user);
            return "profile";
        } catch (UserNotFoundException e) {
            return "profileNotFound";
        } catch (BadRequestException | InternalServerException e) {
            model.addAttribute("error", e.getMessage());
            return "Error";
        }
    }

    @RequestMapping(value = "user-registration", method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(User user) {
        try {
            userService.save(user);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<String> loginUser(HttpSession session, User user) {
        try {
            if (session.getAttribute("user") != null)
                return new ResponseEntity<>("the user is already logged in", HttpStatus.BAD_REQUEST);
            user = userService.loginUser(user.getPhone(), user.getPassword());
            session.setAttribute("user", user);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "myProfile", method = RequestMethod.GET)
    public String MyProfile(Model model, HttpSession session) {
        try {
            if (session.getAttribute("user") == null)
                return "Error";
            User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
            model.addAttribute("incomingRequests", userService.getIncomingRequests(user.getId()));
            model.addAttribute("outgoingRequests", userService.getOutcomeRequests(user.getId()));
            model.addAttribute("friends", userService.getFriends(user.getId()));
            return "MyProfile";
        } catch (InternalServerException e) {
            return "Error";
        }
    }


    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ResponseEntity<String> logoutUser(HttpSession session) {
        if (session.getAttribute("user") == null)
            return new ResponseEntity<>("user is still online", HttpStatus.INTERNAL_SERVER_ERROR);
        session.setAttribute("user", null);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/saveUser", produces = "text/plain")
    public @ResponseBody
    User doPost(HttpServletRequest req) throws InternalServerException, BadRequestException {
        try (BufferedReader br = req.getReader()) {
            User user = toJavaObject(br);
            return userService.save(user);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/findUser", produces = "text/plain")
    public @ResponseBody
    User doGet(HttpServletRequest req) {
        try (BufferedReader br = req.getReader()) {
            User user = toJavaObject(br);
            return userService.findById(user.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateUser", produces = "text/plain")
    public @ResponseBody
    User doPut(HttpServletRequest req) {
        try (BufferedReader br = req.getReader()) {
            User user = toJavaObject(br);
            return userService.update(user);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteUser", produces = "text/plain")
    public @ResponseBody
    User doDelete(HttpServletRequest req) {
        try (BufferedReader br = req.getReader()) {
            User user = toJavaObject(br);
            return userService.delete(user);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
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
