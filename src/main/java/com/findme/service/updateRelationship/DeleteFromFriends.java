package com.findme.service.updateRelationship;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Map;
import static com.findme.models.Status.*;

public class DeleteFromFriends extends Update {


    public DeleteFromFriends(RelationshipDAO relationshipDAO, UserService userService) {
        super(relationshipDAO, userService);
    }

    @Override
    public void setNextUpdate(Update nextUpdate) {
        this.nextUpdate = nextUpdate;
    }

    @Override
    public boolean isUpdate(HttpSession session, Map<String, String> params) throws InternalServerException, BadRequestException {
       getProperty(session, params);
        if ( newStatus.equals(WAS_FRIEND) && relationship.getStatus().equals(FRIEND)) {
            dateCheck(relationship.getLustModDate());
            relationshipDAO.update(addRelationshipStatusAndDate(relationship, newStatus));
            return true;
        } else {
            throw new BadRequestException("sorry, your status is " + relationship.getStatus());
        }

    }

}
