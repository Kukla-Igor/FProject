package com.findme.controller;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerException;
import com.findme.service.RelationshipService;
import org.apache.log4j.Logger;
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
    private static final Logger log = Logger.getLogger(PostController.class);


    @Autowired
    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }


    @RequestMapping(value = "add-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> addRelationship(HttpSession session) {
        try {
            relationshipService.addRelationship(session);
            log.error("relationship is added");
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "update-relationship", method = RequestMethod.POST)
    public ResponseEntity<String> updateRelationship(HttpSession session, @RequestBody Map<String, String> params) {
        try {
            relationshipService.updateRelationship(session, params);
            log.info("relationship is updated");
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (InternalServerException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

