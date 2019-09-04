package com.findme.controller;

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

@Controller
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping (path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable String userId){
        User user = new User();
        user.setFirstName("Igor");
        user.setCity("City");
        model.addAttribute("user", user);
        return "profile";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/saveUser", produces = "text/plain")
    public @ResponseBody
    User doPost (HttpServletRequest req, HttpServletResponse resp) {
        return userService.save(req);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findUser", produces = "text/plain")
    public @ResponseBody
    User doGet (HttpServletRequest req) {
        User user = userService.findById(req);
        System.out.println(user);
        return user;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateUser", produces = "text/plain")
    public @ResponseBody
    User doPut (HttpServletRequest req) {
        return userService.update(req);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteUser", produces = "text/plain")
    public @ResponseBody
    User doDelete (HttpServletRequest req) {
        return userService.delete(req);
    }
}
