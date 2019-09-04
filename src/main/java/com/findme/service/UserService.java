package com.findme.service;

import com.findme.dao.UserDAO;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {
    UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User save(HttpServletRequest req) {
        return (User) userDAO.save(req);
    }

    public User findById(HttpServletRequest req) {
        return (User) userDAO.findById(req);
    }

    public User update (HttpServletRequest req) {
        return (User) userDAO.update(req);
    }

    public User delete(HttpServletRequest req) {
        return (User) userDAO.delete(req);
    }

}
