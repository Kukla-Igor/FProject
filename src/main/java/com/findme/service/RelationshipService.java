package com.findme.service;

import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.exception.UserNotFoundException;
import com.findme.models.Relationship;
import com.findme.models.Status;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.findme.models.Status.*;

@Service
public class RelationshipService {
    RelationshipDAO relationshipDAO;

    public Relationship addRelationship(User userTo, User userFrom) throws InternalServerException, BadRequestException {
        Relationship relationship = relationshipDAO.getRelationship(userFrom.getId(), userTo.getId());

        if (relationship == null) {
            Relationship newRelationship = new Relationship(REQUEST_SENT, userFrom, userTo);
            return (Relationship) relationshipDAO.save(newRelationship);
        }
        throw new BadRequestException("sorry, your status is " + relationship.getStatus());
    }


    public Relationship updateRelationship(User userTo, User userFrom, Status newStatus) throws UserNotFoundException, InternalServerException, BadRequestException {
        Relationship relationship = relationshipDAO.getRelationship(userFrom.getId(), userTo.getId());

        if ((newStatus.equals(FRIEND) || newStatus.equals(REQUEST_DENIED)) && relationship.getStatus().equals(REQUEST_SENT)) {
            relationship.setStatus(newStatus);
            return (Relationship) relationshipDAO.update(relationship);
        } else if (newStatus.equals(WAS_FRIEND) && relationship.getStatus().equals(FRIEND)) {
            relationship.setStatus(newStatus);
            return (Relationship) relationshipDAO.update(relationship);
        }
        throw new BadRequestException("sorry, your status is " + relationship.getStatus());
    }



    public Relationship delete(User userTo, User userFrom) throws InternalServerException, BadRequestException, UserNotFoundException {
        Relationship relationship = relationshipDAO.getRelationship(userFrom.getId(), userTo.getId());
        if (relationship.getStatus().equals(REQUEST_SENT)) {
            return (Relationship) relationshipDAO.delete(relationship);
        }
        throw new BadRequestException("sorry, your status is " + relationship.getStatus());
    }



    @Autowired
    public RelationshipService(RelationshipDAO relationshipDAO) {
        this.relationshipDAO = relationshipDAO;
    }


}
