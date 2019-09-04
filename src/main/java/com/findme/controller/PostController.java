package com.findme.controller;

import com.findme.models.Post;
import com.findme.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PostController {
    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }



    @RequestMapping(method = RequestMethod.POST, value = "/savePost", produces = "text/plain")
    public @ResponseBody
    Post doPost (HttpServletRequest req, HttpServletResponse resp) {
        return postService.save(req);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findPost", produces = "text/plain")
    public @ResponseBody
    Post doGet (HttpServletRequest req) {
        Post post = postService.findById(req);
        System.out.println(post);
        return post;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updatePost", produces = "text/plain")
    public @ResponseBody
    Post doPut (HttpServletRequest req) {
        return postService.update(req);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deletePost", produces = "text/plain")
    public @ResponseBody
    Post doDelete (HttpServletRequest req) {
        return postService.delete(req);
    }
}
