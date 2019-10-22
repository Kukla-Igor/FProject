package com.findme.dao;

import com.findme.models.Relationship;
import org.springframework.stereotype.Repository;

@Repository
public class RelationshipDAO extends GenDAO {

    @Override
    Class aClass() {
        return Relationship.class;
    }
}
