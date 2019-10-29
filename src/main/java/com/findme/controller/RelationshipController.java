package com.findme.controller;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.models.User;
import com.findme.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class RelationshipController {
    RelationshipService relationshipService;

    @RequestMapping(value = "add-relationship",  method = RequestMethod.POST)
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
        } catch (InternalServerException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


        private Long toLong(String text) throws BadRequestException {
            try {
                Long number = Long.parseLong(text);
                return number;
            } catch (NumberFormatException e) {
                throw new BadRequestException("Bad request");
            }
        }
    @Autowired
    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

}
