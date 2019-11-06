package com.findme.dao;

import com.findme.exception.InternalServerException;
import com.findme.models.Relationship;
import com.findme.models.Status;
import com.findme.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RelationshipDAO extends GenDAO {

    public Status getStatus(Relationship relationship) throws InternalServerException {
        try {
            Query query = entityManager.createNativeQuery("select STATUS from RELATIONSHIP where USER_ID_FROM = ? AND USER_ID_TO = ?");
            query.setParameter(1, relationship.getUserFrom().getId());
            query.setParameter(2, relationship.getUserTo().getId());
            int index = Integer.parseInt( (String) query.getSingleResult());

            Status status = Status.values()[index];
            return status;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new InternalServerException("InternalServerException");
        }
    }



    @Override
    Class aClass() {
        return Relationship.class;
    }
}
