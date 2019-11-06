package com.findme.service;

import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.models.Relationship;
import com.findme.models.Status;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshipService {
    RelationshipDAO relationshipDAO;

    public Relationship addRelationship(User userTo, User userFrom) throws InternalServerException, BadRequestException {
        Relationship relationship = new Relationship();
        relationship.setUserTo(userTo);
        relationship.setUserFrom(userFrom);
        Status status = relationshipDAO.getStatus(relationship);

        if (status == null) {
            relationship.setStatus(Status.REQUEST_SENT);
            return (Relationship) relationshipDAO.save(relationship);
        }
        throw new BadRequestException("sorry, your status is " + status);
    }



    @Autowired
    public RelationshipService(RelationshipDAO relationshipDAO) {
        this.relationshipDAO = relationshipDAO;
    }


}
