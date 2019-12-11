package com.findme.service.chainOfResponsibility;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.models.Relationship;
import com.findme.models.Status;
import com.findme.service.UserService;
import static com.findme.models.Status.FRIEND;
import static com.findme.models.Status.REQUEST_SENT;

public class AddToFriends extends Chains {

    public AddToFriends(RelationshipDAO relationshipDAO, UserService userService, long userToId, long userFromId) {
        super(relationshipDAO, userService, userToId, userFromId);
    }

    @Override
    public void setNextChain(Chains nextChain) {
        this.nextChain = nextChain;
    }

    @Override
    public boolean newChain(Status newStatus, Relationship relationship) throws InternalServerException, BadRequestException {
       getProperty();
        if (newStatus.equals(FRIEND) && relationship.getStatus().equals(REQUEST_SENT)) {
            if (outgoingRequests.size() > 10 || friends.size() > 100)
                throw new BadRequestException("too many outgoing requests or friends");
            relationshipDAO.update(addRelationshipStatusAndDate(relationship, newStatus));
            return true;
        } else if (nextChain != null)
            return nextChain.newChain(newStatus, relationship);
        return false;
    }

}
