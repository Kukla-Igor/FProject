package com.findme.service;

import com.findme.dao.PostDAO;
import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.exception.UserNotFoundException;
import com.findme.models.Post;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    public void createPost(Post post, String usersTagg) throws BadRequestException, InternalServerException, UserNotFoundException {
        if (post.getMessage().isEmpty())
            throw new BadRequestException("post message is empty");
        post.setDatePosted(new Date());
        if (!usersTagg.isEmpty()) {
            String[] arr = usersTagg.split(", ");
            List<User> usersTagget = new ArrayList<>();
            User user;
            for (String part : arr) {
                user = (User) userDAO.findById(Long.parseLong(part));
                if (user == null)
                    throw new UserNotFoundException("user with id = " + part + " not found");
                usersTagget.add(user);
            }
            post.setUsersTagget(usersTagget);
        }
        postDAO.save(post);
    }

    public List getPostsById(long userId) throws InternalServerException {

        return  postDAO.getPostsById(userId);
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
