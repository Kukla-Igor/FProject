package com.findme.service.chainOfResponsibility;
import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.models.Relationship;
import com.findme.models.Status;
import com.findme.service.UserService;
import java.util.*;

public abstract class Chains {
    RelationshipDAO relationshipDAO;
    UserService userService;
    Chains nextChain;
    long userToId;
    long userFromId;
    List friends;
    List outgoingRequests;


    public Chains(RelationshipDAO relationshipDAO) {
        this.relationshipDAO = relationshipDAO;
    }

    public Chains(RelationshipDAO relationshipDAO, UserService userService, long userToId, long userFromId) {
        this.relationshipDAO = relationshipDAO;
        this.userService = userService;
        this.userFromId = userFromId;
        this.userToId = userToId;
    }

    public abstract void setNextChain(Chains nextChain);


    protected void getProperty() throws InternalServerException {
        this.friends = userService.getFriends(userFromId);
        this.outgoingRequests = userService.getOutcomeRequests(userToId);
    }


    protected Relationship addRelationshipStatusAndDate(Relationship relationship, Status newStatus) {
        relationship.setStatus(newStatus);
        relationship.setLustModDate(new Date());
        return relationship;
    }



    protected void dateCheck(Date lustModDate) throws BadRequestException {
        Calendar nowDate = new GregorianCalendar();
        Calendar lustMod = new GregorianCalendar();

        nowDate.setTime(new Date());
        lustMod.setTime(lustModDate);

        int daysBetween = nowDate.get(Calendar.DAY_OF_YEAR) - lustMod.get(Calendar.DAY_OF_YEAR);

        if (daysBetween <= 3)
            throw new BadRequestException("sorry, you can delete a user three days after adding to friends");
    }

    public abstract boolean newChain(Status newStatus, Relationship relationship) throws InternalServerException, BadRequestException;
}
