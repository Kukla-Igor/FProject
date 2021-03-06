package com.findme.service;

import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.exception.UserNotFoundException;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService  {
    UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User save(User user) throws InternalServerException, BadRequestException {
        if (user.getPhone().isEmpty() || user.getEMail().isEmpty() || user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getPassword().isEmpty())
            throw new BadRequestException("empty field");
        if (userDAO.findByPhoneOrEMail(user.getPhone(), user.getEMail()) != null)
            throw new BadRequestException("such user is already registered");

        return (User) userDAO.save(user);

    }

    public User findById(long id) throws InternalServerException  {
        return (User) userDAO.findById(id);
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
        user = (User) userDAO.findById(user.getId());
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

    public List getIncomingRequests(long userId) throws InternalServerException {
        return  userDAO.getIncomingRequestsUsers(userId);
    }

    public List getOutcomeRequests(long userId) throws InternalServerException {
        return  userDAO.getOutcomeRequestsUsers(userId);
    }

    public List getFriends(long userId) throws InternalServerException {
        return  userDAO.getFriends(userId);
    }

}
