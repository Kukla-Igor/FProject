package com.findme.service.updateRelationship;


import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.models.Relationship;
import com.findme.models.Status;
import com.findme.models.User;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpSession;
import java.util.*;

public abstract class Update {
    RelationshipDAO relationshipDAO;
    UserService userService;

    Update nextUpdate;
    User userTo;
    User userFrom;
    Relationship relationship;
    Status newStatus;
    List friends;
    List outgoingRequests;


    @Autowired
    public Update(RelationshipDAO relationshipDAO, UserService userService) {
        this.relationshipDAO = relationshipDAO;
        this.userService = userService;
    }


    public abstract void setNextUpdate(Update nextUpdate);


    protected void getProperty(HttpSession session, Map<String, String> params) throws InternalServerException {
        this.userTo = (User) session.getAttribute("user");
        this.userFrom = userService.findById(Long.parseLong(params.get("idUser")));
        this.relationship = relationshipDAO.getRelationship(userFrom.getId(), userTo.getId());
        if (params.get("status") == null)
            this.newStatus = null;
        else
            this.newStatus = Status.valueOf(params.get("status"));
        this.friends = userService.getFriends(userFrom.getId());
        this.outgoingRequests = userService.getOutcomeRequests(userTo.getId());
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

    public abstract boolean isUpdate(HttpSession session, Map<String, String> params) throws InternalServerException, BadRequestException;
}
