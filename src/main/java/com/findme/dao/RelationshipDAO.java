package com.findme.dao;

import com.findme.exception.InternalServerException;
import com.findme.models.Relationship;
import com.findme.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class RelationshipDAO extends GenDAO {

    public String getStatus(Relationship relationship) throws InternalServerException {
        try {
            Query query = entityManager.createNativeQuery("select STATUS from RELATIONSHIP where USER_ID_FROM = ? AND USER_ID_TO = ?", User.class);
            query.setParameter(1, relationship.getUserFrom().getId());
            query.setParameter(2, relationship.getUserTo().getId());
            return (String) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException");

        }
    }

    @Override
    Class aClass() {
        return Relationship.class;
    }
}
