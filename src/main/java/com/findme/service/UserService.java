package com.findme.service;

import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.exception.UserNotFoundException;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

@Service
public class UserService  {
    UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User save(User user) throws InternalServerException, BadRequestException {
        if (userDAO.findByPhone(user.getPhone()) == null)
            return (User) userDAO.save(user);
        throw new BadRequestException("such user is already registered");
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


    public User loginUser( String phone, String password) throws UserNotFoundException, InternalServerException, BadRequestException{
        User user = userDAO.findByPhone(phone);
        if (user == null)
            throw new UserNotFoundException("User isn`t registered");
        if (!user.getPassword().equals(password))
            throw new BadRequestException("Password is wrong");
        return user;
    }

}
