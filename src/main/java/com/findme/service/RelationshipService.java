package com.findme.service;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.models.Relationship;
import com.findme.models.User;
import com.findme.service.updateRelationship.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.findme.models.Status.*;

@Service
public class RelationshipService {
    RelationshipDAO relationshipDAO;
    UserService userService;
    @Autowired
    public RelationshipService(RelationshipDAO relationshipDAO, UserService userService) {
        this.relationshipDAO = relationshipDAO;
        this.userService = userService;
    }



    public Relationship addRelationship(HttpSession session) throws InternalServerException, BadRequestException {
        User userFrom = (User) session.getAttribute("user");
        if (userFrom == null)
            throw new BadRequestException("you are not logged in");
        User userTo = (User) session.getAttribute("lustUserPage");
        List outgoingRequests = userService.getOutcomeRequests(userFrom.getId());
        if (outgoingRequests.size() > 10)
            throw new BadRequestException("too many outgoing requests");

        Relationship relationship = relationshipDAO.getRelationship(userFrom.getId(), userTo.getId());

        if (relationship == null) {
            Relationship newRelationship = new Relationship(REQUEST_SENT, userFrom, userTo, new Date());
            return (Relationship) relationshipDAO.save(newRelationship);
        }
        throw new BadRequestException("sorry, your status is " + relationship.getStatus());
    }

    public void updateRelationship(HttpSession session, Map<String, String> params) throws InternalServerException, BadRequestException {
        Update abort = new AbortRelationship(relationshipDAO, userService);
        Update addToFriends = new AddToFriends(relationshipDAO, userService);
        Update denyToFriends = new DenyToFriends(relationshipDAO, userService);
        Update deleteFromFriends = new DeleteFromFriends(relationshipDAO, userService);

        abort.setNextUpdate(addToFriends);
        addToFriends.setNextUpdate(denyToFriends);
        denyToFriends.setNextUpdate(deleteFromFriends);
        deleteFromFriends.setNextUpdate(null);

        abort.isUpdate(session,params);


    }
    }
