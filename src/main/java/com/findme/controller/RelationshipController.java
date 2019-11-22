package com.findme.controller;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.models.Status;
import com.findme.models.User;
import com.findme.service.RelationshipService;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class RelationshipController {
    RelationshipService relationshipService;

    @Autowired
    UserService userService;

    @Autowired
    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }


    @RequestMapping(value = "add-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> addRelationship(HttpSession session) {
        try {
            User userFrom = (User) session.getAttribute("user");
            if (userFrom == null)
                return new ResponseEntity<>("you are not logged in", HttpStatus.BAD_REQUEST);
            User userTo = (User) session.getAttribute("lustUserPage");
            List outgoingRequests = userService.getOutcomeRequests(userFrom.getId());
            relationshipService.addRelationship(userTo, userFrom, outgoingRequests);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "update-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> updateRelationship(HttpSession session, @RequestBody Map<String, String> params) {
        try {
            Status newStatus = Status.valueOf(params.get("status"));
            User userTo = (User) session.getAttribute("user");
            User userFrom = userService.findById(Long.parseLong(params.get("idUser")));
            List outgoingRequests = userService.getOutcomeRequests(userTo.getId());
            List friends = userService.getFriends(userFrom.getId());
            relationshipService.updateRelationship(userTo, userFrom, newStatus, outgoingRequests, friends);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (InternalServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "delete-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> deleteRelationship(HttpSession session,  @RequestBody Map<String, String> params) {
        try {
            User userFrom = (User) session.getAttribute("user");
            long idTo = Long.parseLong(params.get("idUser"));
            User userTo = userService.findById(idTo);
            relationshipService.delete(userTo, userFrom);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (InternalServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

