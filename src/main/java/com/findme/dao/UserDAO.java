package com.findme.dao;

import com.findme.exception.InternalServerException;
import com.findme.models.Relationship;
import com.findme.models.User;
import org.springframework.stereotype.Repository;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO extends GenDAO{

    private String queryFindByPhone = "SELECT * FROM USERS WHERE PHONE = ?";
    private String queryFindByPhoneOrEMail = "SELECT * FROM USERS WHERE PHONE = ? OR EMAIL = ?";
    private String queryGetIncomingRequestsUsers = "SELECT * FROM RELATIONSHIP WHERE USER_ID_TO = ? AND STATUS = 1";
    private String queryGetOutcomeRequestsUsers = "SELECT * FROM RELATIONSHIP WHERE USER_ID_FROM = ? AND STATUS = 1";
    private String queryGetFriendsFrom = "SELECT * FROM RELATIONSHIP WHERE USER_ID_FROM = ? AND STATUS = 3";
    private String queryGetFriendsTo = "SELECT * FROM RELATIONSHIP WHERE USER_ID_TO = ? AND  STATUS = 3";


    public User findByPhone(String phone) throws InternalServerException{
        try {
            Query query = entityManager.createNativeQuery(queryFindByPhone, User.class);
            query.setParameter(1, phone);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException: " + e.getMessage());
        }
    }

    public User findByPhoneOrEMail(String phone, String eMail) throws InternalServerException{
        try {
            Query query = entityManager.createNativeQuery(queryFindByPhoneOrEMail, User.class);
            query.setParameter(1, phone);
            query.setParameter(2, eMail);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException: " + e.getMessage());
        }
    }

    public List getIncomingRequestsUsers(long id) throws InternalServerException {
        try {
            Query query = entityManager.createNativeQuery(queryGetIncomingRequestsUsers, Relationship.class);
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
            throw new InternalServerException("InternalServerException: " + e.getMessage());
        }
    }

    public List getOutcomeRequestsUsers(long id) throws InternalServerException {
        try {
            Query query = entityManager.createNativeQuery(queryGetOutcomeRequestsUsers, Relationship.class);
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
            throw new InternalServerException("InternalServerException: " + e.getMessage());
        }
    }


    public List getFriends(long id) throws InternalServerException {
        try {
            Query query = entityManager.createNativeQuery(queryGetFriendsFrom, Relationship.class);
            query.setParameter(1, id);
            List<Relationship> list =  query.getResultList();
            List<User> users = new ArrayList<>();
            for (Relationship relationship: list) {
                users.add(relationship.getUserTo());
            }
            query = entityManager.createNativeQuery(queryGetFriendsTo, Relationship.class);
            query.setParameter(1, id);
            list =  query.getResultList();
            for (Relationship relationship: list) {
                users.add(relationship.getUserFrom());
            }
            return users;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException: " + e.getMessage());
        }
    }

    @Override
    Class aClass() {
            return User.class;
    }
}
