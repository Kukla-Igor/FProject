package com.findme.controller;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;

import java.util.Map;

@Controller
public class RelationshipController {
    RelationshipService relationshipService;


    @Autowired
    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }


    @RequestMapping(value = "add-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> addRelationship(HttpSession session) {
        try {
            relationshipService.addRelationship(session);
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

            relationshipService.updateRelationship(session, params);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (InternalServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "abort", method = RequestMethod.POST)
    public ResponseEntity<String> deleteRelationship(HttpSession session,  @RequestBody Map<String, String> params) {
        try {
            relationshipService.abort(session, params);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (InternalServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

