package com.findme.service.chainOfResponsibility;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.models.Relationship;
import com.findme.models.Status;
import static com.findme.models.Status.REQUEST_SENT;

public class AbortRelationship extends Chains {

    public AbortRelationship(RelationshipDAO relationshipDAO) {
        super(relationshipDAO);
    }

    @Override
    public void setNextChain(Chains nextChain) {
        this.nextChain = nextChain;
    }



    @Override
    public boolean newChain(Status newStatus, Relationship relationship) throws InternalServerException, BadRequestException {
        if (newStatus == null && relationship.getStatus().equals(REQUEST_SENT)) {

            relationshipDAO.delete(relationship);
            return true;
        } else if (nextChain != null)
            return nextChain.newChain(newStatus, relationship);
        return false;
    }

}
