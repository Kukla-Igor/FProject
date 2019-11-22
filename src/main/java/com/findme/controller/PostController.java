package com.findme.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.exception.InternalServerException;
import com.findme.models.Post;
import com.findme.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class PostController  {
    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
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
