package com.findme.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.models.User;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String profile(Model model, @PathVariable String userId) {
        try {
            User user = new User();
            user.setId(Long.parseLong(userId));
            user = userService.findById(user);
            if (user == null)
                return "profileNotFound";
            model.addAttribute("user", user);
            return "profile";
        } catch (Exception e) {
            return "error";
        }

    }


    @RequestMapping(method = RequestMethod.POST, value = "/saveUser", produces = "text/plain")
    public @ResponseBody
    User doPost(HttpServletRequest req) {
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
            return userService.findById(user);
        } catch (IOException e) {
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
}
