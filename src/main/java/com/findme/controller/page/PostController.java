package com.findme.controller.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.exception.InternalServerException;
import com.findme.models.Post;
import com.findme.service.PostService;
import com.findme.service.UserService;
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
public class PostController {

    PostService postService;
    UserService userService;

    private static final Logger log = LogManager.getLogger(PostController.class);

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @RequestMapping(value = "postFilter", method = RequestMethod.POST)
    public ResponseEntity<String> postFilter(HttpSession session, @RequestBody Map<String, String> params) {
        session.setAttribute("filterPostsUserId", (params.get("filterPostsUserId")));
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/savePost", produces = "text/plain")
    public @ResponseBody
    Post doPost(HttpServletRequest req, HttpServletResponse resp) throws InternalServerException, IOException {
        try (BufferedReader br = req.getReader()) {
            Post post = toJavaObject(br);
            return postService.save(post);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findPost", produces = "text/plain")
    public @ResponseBody
    Post doGet(HttpServletRequest req)  throws IOException, InternalServerException {
        try (BufferedReader br = req.getReader()) {
            Post post = toJavaObject(br);
            return postService.findById(post);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updatePost", produces = "text/plain")
    public @ResponseBody
    Post doPut(HttpServletRequest req)  throws IOException {
        try (BufferedReader br = req.getReader()) {
            Post post = toJavaObject(br);
            return postService.update(post);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deletePost", produces = "text/plain")
    public @ResponseBody
    Post doDelete(HttpServletRequest req)  throws IOException {
        try (BufferedReader br = req.getReader()) {
            Post post = toJavaObject(br);
            return postService.delete(post);
        }
    }


    @RequestMapping(value = "feed", method = RequestMethod.GET)
    public String feed(Model model, HttpSession session, String nextPage) throws InternalServerException {

            int numNextPage = 1;
            if (nextPage != null)
                numNextPage = 1 + (int) session.getAttribute("numNextPage");

            session.setAttribute("numNextPage", numNextPage);

            List friends = (List) session.getAttribute("friends");

            model.addAttribute("Posts", postService.getNews(friends, numNextPage));

            return "feed";
    }


    private Post toJavaObject(BufferedReader br) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(br, Post.class);
    }

  
}
