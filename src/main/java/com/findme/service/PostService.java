package com.findme.service;

import com.findme.dao.PostDAO;
import com.findme.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class PostService {
    PostDAO postDAO;

    @Autowired
    public PostService(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    public Post save(HttpServletRequest req) {
        return (Post) postDAO.save(req);
    }

    public Post findById(HttpServletRequest req) {
        return (Post) postDAO.findById(req);
    }

    public Post update (HttpServletRequest req) {
        return (Post) postDAO.update(req);
    }

    public Post delete(HttpServletRequest req) {
        return (Post) postDAO.delete(req);
    }

}
