package com.findme.dao;

import com.findme.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;


@Repository


public class UserDAO extends GenDAO{

    public User userIdCheck(User user){
        try {
            Query query = entityManager.createNativeQuery("select * from users where phone = ?", User.class);
            query.setParameter(1, user.getPhone());
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    Class aClass() {
            return User.class;
    }
}
