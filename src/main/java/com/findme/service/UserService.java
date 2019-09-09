package com.findme.service;

import com.findme.dao.UserDAO;
import com.findme.exception.InternalServerException;
import com.findme.exception.UserNotFoundException;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService  {
    UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User save(User user) {
        return (User) userDAO.save(user);
    }

    public User findById(User user) throws InternalServerException  {
        return (User) userDAO.findById(user);
    }

    public User update (User user) {
        return (User) userDAO.update(user);
    }

    public User delete(User user) {
        return (User) userDAO.delete(user);
    }

    public User profile(Long id) throws UserNotFoundException,InternalServerException {
        User user = new User();
        user.setId(id);
        user = (User) userDAO.findById(user);
        if (user == null)
            throw new UserNotFoundException("profileNotFound");
        return user;
    }

}
