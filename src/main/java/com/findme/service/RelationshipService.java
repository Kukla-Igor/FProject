package com.findme.service;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.models.Relationship;
import com.findme.models.Status;
import com.findme.models.User;
import com.findme.service.chainOfResponsibility.*;
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
        long userToId = ((User) session.getAttribute("user")).getId();
        long userFromId = Long.parseLong(params.get("idUser"));
        Relationship relationship = relationshipDAO.getRelationship(userFromId, userToId);
        Status newStatus = null;
        if (params.get("status") != null)
            newStatus = Status.valueOf(params.get("status"));


        Chains abort = new AbortRelationship(relationshipDAO);
        Chains addToFriends = new AddToFriends(relationshipDAO, userService, userToId, userFromId);
        Chains denyToFriends = new DenyToFriends(relationshipDAO);
        Chains deleteFromFriends = new DeleteFromFriends(relationshipDAO);

        abort.setNextChain(addToFriends);
        addToFriends.setNextChain(denyToFriends);
        denyToFriends.setNextChain(deleteFromFriends);
        deleteFromFriends.setNextChain(null);

        abort.newChain(newStatus, relationship);
    }
    }
