package com.findme.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.exception.UserNotFoundException;
import com.findme.models.Post;
import com.findme.models.User;
import com.findme.service.PostService;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class PostController  {
    PostService postService;
    UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @RequestMapping(value = "createPost", method = RequestMethod.POST)
    public ResponseEntity<String> createPost(HttpSession session, @RequestBody Map<String, String> params) {
        try {
            if(params.get("message").isEmpty())
                return new ResponseEntity<>("empty field", HttpStatus.BAD_REQUEST);
            Post post = new Post();
            post.setMessage(params.get("message"));
            post.setDatePosted(new Date());
            if(!params.get("location").isEmpty())
                post.setLocation(params.get("location"));
            post.setUserPosted((User) session.getAttribute("user"));
            if (session.getAttribute("lustUserPage") == null)
                post.setUserPagePosted((User) session.getAttribute("user"));
            else
                post.setUserPagePosted((User) session.getAttribute("lustUserPage"));
            if (!params.get("usersTagget").isEmpty()){
                String[] arr = params.get("usersTagget").split(", ");
                List<User> usersTagget = new ArrayList<>();
                for (String part: arr) {
                    usersTagget.add(userService.findById(Long.parseLong(part)));
                }
                post.setUsersTagget(usersTagget);
            }
            postService.save(post);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (InternalServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/savePost", produces = "text/plain")
    public @ResponseBody
    Post doPost(HttpServletRequest req, HttpServletResponse resp) throws InternalServerException {
        try (BufferedReader br = req.getReader()) {
            Post post = toJavaObject(br);
            return postService.save(post);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findPost", produces = "text/plain")
    public @ResponseBody
    Post doGet(HttpServletRequest req)  {
        try (BufferedReader br = req.getReader()) {
            Post post = toJavaObject(br);
            return postService.findById(post);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updatePost", produces = "text/plain")
    public @ResponseBody
    Post doPut(HttpServletRequest req) {
        try (BufferedReader br = req.getReader()) {
            Post post = toJavaObject(br);
            return postService.update(post);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deletePost", produces = "text/plain")
    public @ResponseBody
    Post doDelete(HttpServletRequest req) {
        try (BufferedReader br = req.getReader()) {
            Post post = toJavaObject(br);
            return postService.delete(post);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Post toJavaObject(BufferedReader br) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(br, Post.class);
    }
}
