package com.findme.dao;

import com.findme.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;


@Repository


public class UserDAO extends GenDAO{

    public boolean newUserCheck(User user){
        Query query = entityManager.createNativeQuery("select phone from users where phone = ?");
        query.setParameter(1, user.getPhone());
        List<Integer> list = query.getResultList();

        return list.isEmpty();
    }

    @Override
    Class aClass() {
            return User.class;
    }
}
