package com.findme.dao;

import com.findme.exception.InternalServerException;
import com.findme.models.Relationship;
import org.springframework.stereotype.Repository;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class RelationshipDAO extends GenDAO {

    private String queryGetRelationship = "SELECT * FROM RELATIONSHIP WHERE (USER_ID_FROM = :firstId and USER_ID_TO = :secondId) OR (USER_ID_FROM = :secondId AND USER_ID_TO = :firstId)";

    public Relationship getRelationship (long idFrom,  long idTo) throws InternalServerException {
        try {
            Query query = entityManager.createNativeQuery(queryGetRelationship, Relationship.class);
            query.setParameter("firstId", idFrom);
            query.setParameter("secondId", idTo);
            return (Relationship) query.getSingleResult();
        } catch (NoResultException | NullPointerException e ) {
            return null;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException: " + e.getMessage());
        }
    }


    @Override
    Class aClass() {
        return Relationship.class;
    }
}
