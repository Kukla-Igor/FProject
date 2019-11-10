package com.findme.controller;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.exception.UserNotFoundException;
import com.findme.models.Status;
import com.findme.models.User;
import com.findme.service.RelationshipService;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

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
            relationshipService.addRelationship(userTo, userFrom);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "update-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> updateRelationship(HttpSession session, String idUser, String status) {
        try {
            Status newStatus = Status.valueOf(status);
            User userTo = (User) session.getAttribute("user");
            long idFrom = Long.parseLong(idUser);
            User userFrom = userService.findById(idFrom);
//            if (newStatus.equals(Status.WAS_FRIEND))
//                relationshipService.deleteFriend()
            relationshipService.updateRelationship(userTo, userFrom, newStatus);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (InternalServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "delete-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> deleteRelationship(HttpSession session, String idUser) {
        try {
            User userFrom = (User) session.getAttribute("user");
            long idTo = Long.parseLong(idUser);
            User userTo = userService.findById(idTo);
            relationshipService.delete(userTo, userFrom);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (InternalServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

