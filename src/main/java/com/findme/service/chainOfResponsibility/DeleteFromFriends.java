package com.findme.service.chainOfResponsibility;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.models.Relationship;
import com.findme.models.Status;
import static com.findme.models.Status.*;

public class DeleteFromFriends extends Chains {


    public DeleteFromFriends(RelationshipDAO relationshipDAO) {
        super(relationshipDAO);
    }

    @Override
    public void setNextChain(Chains nextChain) {
        this.nextChain = nextChain;
    }

    @Override
    public boolean newChain(Status newStatus, Relationship relationship) throws BadRequestException {
        if ( newStatus.equals(WAS_FRIEND) && relationship.getStatus().equals(FRIEND)) {
            dateCheck(relationship.getLustModDate());
            relationshipDAO.update(addRelationshipStatusAndDate(relationship, newStatus));
            return true;
        } else {
            throw new BadRequestException("sorry, your status is " + relationship.getStatus());
        }

    }

}
