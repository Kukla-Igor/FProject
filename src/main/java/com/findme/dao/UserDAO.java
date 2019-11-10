package com.findme.dao;

import com.findme.exception.InternalServerException;
import com.findme.models.Relationship;
import com.findme.models.Status;
import com.findme.models.User;
import org.springframework.stereotype.Repository;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


@Repository


public class UserDAO extends GenDAO{

    public User findByPhone(String phone) throws InternalServerException{
        try {
            Query query = entityManager.createNativeQuery("select * from users where phone = ?", User.class);
            query.setParameter(1, phone);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException");
        }
    }

    public List getIncomingRequestsUsers(long id) throws InternalServerException {
        try {
            Query query = entityManager.createNativeQuery("select * from RELATIONSHIP where USER_ID_TO = ? and STATUS = 1", Relationship.class);
            query.setParameter(1, id);

            List<Relationship> list =  query.getResultList();
            List<User> users = new ArrayList<>();
            for (Relationship relationship: list) {
                users.add(relationship.getUserFrom());
            }
            return users;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException");
        }
    }

    public List getOutcomeRequestsUsers(long id) throws InternalServerException {
        try {
            Query query = entityManager.createNativeQuery("select * from RELATIONSHIP where USER_ID_FROM = ? and STATUS = 1", Relationship.class);
            query.setParameter(1, id);

            List<Relationship> list =  query.getResultList();
            List<User> users = new ArrayList<>();
            for (Relationship relationship: list) {
                users.add(relationship.getUserTo());
            }
            return users;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException");
        }
    }


    public List getFriends(long id) throws InternalServerException {
        try {
            Query query = entityManager.createNativeQuery("select * from RELATIONSHIP where USER_ID_FROM = ? and STATUS = 3", Relationship.class);
            query.setParameter(1, id);
            List<Relationship> list =  query.getResultList();
            List<User> users = new ArrayList<>();
            for (Relationship relationship: list) {
                users.add(relationship.getUserTo());
            }
            query = entityManager.createNativeQuery("select * from RELATIONSHIP where USER_ID_TO = ? and  STATUS = 3", Relationship.class);
            query.setParameter(1, id);
            list =  query.getResultList();
            for (Relationship relationship: list) {
                users.add(relationship.getUserFrom());
            }

            return users;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException");
        }
    }

    @Override
    Class aClass() {
            return User.class;
    }
}
