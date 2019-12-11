package com.findme.service.chainOfResponsibility;


import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.models.Relationship;
import com.findme.models.Status;
import static com.findme.models.Status.REQUEST_DENIED;
import static com.findme.models.Status.REQUEST_SENT;

public class DenyToFriends extends Chains {

    public DenyToFriends(RelationshipDAO relationshipDAO) {
        super(relationshipDAO);
    }

    @Override
    public void setNextChain(Chains nextChain) {
        this.nextChain = nextChain;
    }

    @Override
    public boolean newChain(Status newStatus, Relationship relationship) throws InternalServerException, BadRequestException {

        if ( newStatus.equals(REQUEST_DENIED) && relationship.getStatus().equals(REQUEST_SENT)) {
            relationshipDAO.update(addRelationshipStatusAndDate(relationship, newStatus));
            return true;
        } else if (nextChain != null)
            return nextChain.newChain(newStatus, relationship);
        return false;
    }

}
