package com.findme.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.models.Post;
import com.findme.models.User;
import com.findme.service.PostService;
import com.findme.service.UserService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Controller
public class PostController  {

    PostService postService;
    UserService userService;

    private static final Logger log = LogManager.getLogger(PostController.class);

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @RequestMapping(value = "createPost", method = RequestMethod.POST)
    public ResponseEntity<String> createPost(HttpSession session, HttpServletRequest req) {
        try(BufferedReader br = req.getReader()) {
            ObjectMapper mapper = new ObjectMapper();
            Post post = new Post();
            JsonNode list = mapper.readTree(br);
            post.setMessage(list.get("message").asText());
            post.setLocation(list.get("location").asText());
            JSONArray arr = new JSONArray(list.get("usersTagg").asText());

            post.setUserPosted((User) session.getAttribute("user"));
            if (session.getAttribute("lustUserPage") == null)
                post.setUserPagePosted((User) session.getAttribute("user"));
            else
                post.setUserPagePosted((User) session.getAttribute("lustUserPage"));
            postService.createPost(post, arr);
            log.info("post create");
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (InternalServerException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException | NumberFormatException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "postFilter", method = RequestMethod.POST)
    public ResponseEntity<String> postFilter(HttpSession session, @RequestBody Map<String, String> params) {

        session.setAttribute("filterPostsUserId", (params.get("filterPostsUserId")));
        return new ResponseEntity<>("OK", HttpStatus.OK);

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


    @RequestMapping (value = "feed", method = RequestMethod.GET)
    public String feed(Model model, HttpSession session,  String nextPage) {
        try {
            int k = 1;
            if (nextPage != null)
                k = 1 + (int) session.getAttribute("k");

            session.setAttribute("k", k);

            List friends = (List) session.getAttribute("friends");

            model.addAttribute("Posts", postService.getNews(friends, k));

            return "feed";
        } catch (InternalServerException | BadRequestException | NumberFormatException e) {
            log.error(e.getMessage());
            return "Error";
        }
    }


    private Post toJavaObject(BufferedReader br) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(br, Post.class);
    }
}
