package com.findme.dao;

import com.findme.models.Post;
import org.springframework.stereotype.Repository;

@Repository
public class PostDAO extends GenDAO {

    @Override
    Class aClass() {
        return Post.class;
    }
}
