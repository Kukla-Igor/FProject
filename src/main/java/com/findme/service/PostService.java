package com.findme.service;

import com.findme.dao.PostDAO;
import com.findme.exception.InternalServerException;
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

    public Post save(Post post) throws InternalServerException {
        return (Post) postDAO.save(post);
    }

    public Post findById(Post post) throws InternalServerException {
        return (Post) postDAO.findById(post);
    }

    public Post update (Post post) {
        return (Post) postDAO.update(post);
    }

    public Post delete(Post post) {
        return (Post) postDAO.delete(post);
    }

}
