package com.findme.dao;

import com.findme.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;


@Repository


public class UserDAO extends GenDAO{

    @Override
    Class aClass() {
            return User.class;
    }
}
