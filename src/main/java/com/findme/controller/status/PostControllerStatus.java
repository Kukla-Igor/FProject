package com.findme.controller.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.models.Post;
import com.findme.models.User;
import com.findme.service.PostService;
import com.findme.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class PostControllerStatus {

    PostService postService;
    UserService userService;

    private static final Logger log = LogManager.getLogger(PostControllerStatus.class);

    @Autowired
    public PostControllerStatus(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @RequestMapping(value = "createPost", method = RequestMethod.POST)
    public ResponseEntity<String> createPost(HttpSession session, HttpServletRequest req) throws InternalServerException, BadRequestException, IOException {
        {
            try (BufferedReader br = req.getReader()) {
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
            }
        }
    }
}
