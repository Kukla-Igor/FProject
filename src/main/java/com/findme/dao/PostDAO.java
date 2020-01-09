package com.findme.dao;

import com.findme.exception.InternalServerException;
import com.findme.models.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDAO extends GenDAO {

    private String queryGetPostById = "SELECT * FROM POST WHERE USER_POSTED = :id OR  USER_PAGE_POSTED = :id ORDER BY DATE_POSTED DESC ";

    @Override
    Class aClass() {
        return Post.class;
    }

    public List getPostsById(long id) throws InternalServerException {
        try {
            Query query = entityManager.createNativeQuery(queryGetPostById, Post.class);
            query.setParameter("id", id);
            List<Post> posts =  query.getResultList();
            return posts;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException: " + e.getMessage());
        }
    }
}
