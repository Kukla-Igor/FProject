package com.findme.service;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.models.Relationship;
import com.findme.models.Status;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import static com.findme.models.Status.*;

@Service
public class RelationshipService {
    RelationshipDAO relationshipDAO;

    public Relationship addRelationship(User userTo, User userFrom, List outgoingRequests) throws InternalServerException, BadRequestException {
        requestSizeCheck(outgoingRequests);

        Relationship relationship = relationshipDAO.getRelationship(userFrom.getId(), userTo.getId());

        if (relationship == null) {
            Relationship newRelationship = new Relationship(REQUEST_SENT, userFrom, userTo, new Date());
            return (Relationship) relationshipDAO.save(newRelationship);
        }
        throw new BadRequestException("sorry, your status is " + relationship.getStatus());
    }


    public Relationship updateRelationship(User userTo, User userFrom, Status newStatus, List outgoingRequests, List friends) throws InternalServerException, BadRequestException {
        Relationship relationship = relationshipDAO.getRelationship(userFrom.getId(), userTo.getId());

        if (newStatus.equals(FRIEND)  && relationship.getStatus().equals(REQUEST_SENT)) {
            requestSizeCheck(outgoingRequests, friends);
            return (Relationship) relationshipDAO.update(addRelationshipStatusAndDate(relationship, newStatus));
        } else if ( newStatus.equals(REQUEST_DENIED) && relationship.getStatus().equals(REQUEST_SENT)) {
            return (Relationship) relationshipDAO.update(addRelationshipStatusAndDate(relationship, newStatus));
        } else if (newStatus.equals(WAS_FRIEND) && relationship.getStatus().equals(FRIEND)) {
            dateCheck(relationship.getLustModDate());
            return (Relationship) relationshipDAO.update(addRelationshipStatusAndDate(relationship, newStatus));
        }
        throw new BadRequestException("sorry, your status is " + relationship.getStatus());
    }



    public Relationship delete(User userTo, User userFrom) throws InternalServerException, BadRequestException {
        Relationship relationship = relationshipDAO.getRelationship(userFrom.getId(), userTo.getId());
        if (relationship.getStatus().equals(REQUEST_SENT)) {
            return (Relationship) relationshipDAO.delete(relationship);
        }
        throw new BadRequestException("sorry, your status is " + relationship.getStatus());
    }

    private void requestSizeCheck(List outgoingRequests) throws BadRequestException{
        if (outgoingRequests.size() > 10)
            throw new BadRequestException("too many outgoing requests");
    }

    private void requestSizeCheck(List outgoingRequests, List friends) throws BadRequestException{
        if (outgoingRequests.size() > 10 || friends.size() > 100)
            throw new BadRequestException("too many outgoing requests or friends");
    }

    private void dateCheck(Date lustModDate) throws BadRequestException{
        Calendar nowDate = new GregorianCalendar();
        Calendar lustMod = new GregorianCalendar();

        nowDate.setTime(new Date());
        lustMod.setTime(lustModDate);

        int daysBetween = nowDate.get(Calendar.DAY_OF_YEAR) - lustMod.get(Calendar.DAY_OF_YEAR);

        if (daysBetween <= 3 )
            throw new BadRequestException("sorry, you can delete a user three days after adding to friends");
    }

    private Relationship addRelationshipStatusAndDate(Relationship relationship, Status newStatus){
        relationship.setStatus(newStatus);
        relationship.setLustModDate(new Date());
        return relationship;
    }



    @Autowired
    public RelationshipService(RelationshipDAO relationshipDAO) {
        this.relationshipDAO = relationshipDAO;
    }


}
