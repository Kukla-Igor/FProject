package com.findme.service.updateRelationship;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Map;
import static com.findme.models.Status.REQUEST_DENIED;
import static com.findme.models.Status.REQUEST_SENT;

public class DenyToFriends extends Update {

    public DenyToFriends(RelationshipDAO relationshipDAO, UserService userService) {
        super(relationshipDAO, userService);
    }

    @Override
    public void setNextUpdate(Update nextUpdate) {
        this.nextUpdate = nextUpdate;
    }

    @Override
    public boolean isUpdate(HttpSession session, Map<String, String> params) throws InternalServerException, BadRequestException {
       getProperty(session, params);
        if ( newStatus.equals(REQUEST_DENIED) && relationship.getStatus().equals(REQUEST_SENT)) {
            relationshipDAO.update(addRelationshipStatusAndDate(relationship, newStatus));
            return true;
        } else if (nextUpdate != null)
            return nextUpdate.isUpdate(session, params);
        return false;
    }

}
