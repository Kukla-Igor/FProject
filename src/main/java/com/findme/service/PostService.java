package com.findme.service;

import com.findme.dao.PostDAO;
import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.exception.UserNotFoundException;
import com.findme.models.Post;
import com.findme.models.User;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostService {
    PostDAO postDAO;
    UserDAO userDAO;

    @Autowired
    public PostService(PostDAO postDAO, UserDAO userDAO) {
        this.postDAO = postDAO;
        this.userDAO = userDAO;
    }

    public void createPost(Post post, JSONArray arr) throws BadRequestException, InternalServerException {
        if (post.getMessage().isEmpty())
            throw new BadRequestException("post message is empty");
        post.setDatePosted(new Date());
        if (!arr.isEmpty()) {
            List usersTagget = new ArrayList();
            User user;
            for (Object object :  arr) {
                long id = Long.parseLong(object.toString());
                user = (User) userDAO.findById(id);
                if (user == null)
                    throw new BadRequestException("user with id = " + id + " not found");
                usersTagget.add(user);
            }
            post.setUsersTagget(usersTagget);
        }
        postDAO.save(post);
    }

    public List getPosts(String filterStatus, long userId) throws InternalServerException, BadRequestException {
        if (filterStatus == null)
            return  postDAO.getAllPosts(userId);
        Long idFilterStatus = Long.parseLong(filterStatus);

        if (idFilterStatus.equals((long) 0))
            return  postDAO.getHomePagePosts(userId);
        if (userDAO.findById(idFilterStatus) == null)
            throw new BadRequestException("user with id = " + idFilterStatus + " not found");

        return postDAO.getPostsToUser(userId,idFilterStatus);






    }

    public Post save(Post post) throws InternalServerException {
        return (Post) postDAO.save(post);
    }

    public Post findById(Post post) throws InternalServerException {
        return (Post) postDAO.findById(post.getId());
    }

    public Post update (Post post) {
        return (Post) postDAO.update(post);
    }

    public Post delete(Post post) {
        return (Post) postDAO.delete(post);
    }

}
