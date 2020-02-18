package com.findme.dao;

import com.findme.exception.InternalServerException;
import com.findme.models.Post;
import com.findme.models.User;
import org.json.JSONArray;
import org.springframework.stereotype.Repository;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PostDAO extends GenDAO {

    private String queryGetAllPostsById = "SELECT * FROM POST WHERE USER_POSTED = :id OR  USER_PAGE_POSTED = :id ORDER BY DATE_POSTED DESC ";
    private String queryGetHomePagePostsById = "SELECT * FROM POST WHERE USER_POSTED = :id  ORDER BY DATE_POSTED DESC ";
    private String getPostsToUser = "SELECT * FROM POST WHERE USER_POSTED = :HomePageId AND USER_PAGE_POSTED = :AnotherUserId ORDER BY DATE_POSTED DESC ";
    private String queryGetListUsers = "SELECT * FROM USERS WHERE ID IN ";
    private String queryGetNews = "SELECT * FROM(SELECT ID, MESSAGE, DATE_POSTED, USER_POSTED, LOCATION, USER_PAGE_POSTED, row_number() over (order by DATE_POSTED DESC) rn FROM POST WHERE  USER_POSTED IN :id OR USER_PAGE_POSTED IN :id ORDER BY DATE_POSTED DESC) WHERE rn > 5 * (:k - 1) AND rn <= 5 * :k ";


    @Override
    Class aClass() {
        return Post.class;
    }

    public List getAllPosts(long id) throws InternalServerException {
        try {
            Query query = entityManager.createNativeQuery(queryGetAllPostsById, Post.class);
            query.setParameter("id", id);
            List<Post> posts =  query.getResultList();
            return posts;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException: " + e.getMessage());
        }
    }

    public List getHomePagePosts(long id) throws InternalServerException {
        try {
            Query query = entityManager.createNativeQuery(queryGetHomePagePostsById, Post.class);
            query.setParameter("id", id);
            List<Post> posts =  query.getResultList();
            return posts;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException: " + e.getMessage());
        }
    }

    public List getPostsToUser(long HomePageId, long AnotherUserId) throws InternalServerException {
        try {
            Query query = entityManager.createNativeQuery(getPostsToUser, Post.class);
            query.setParameter("HomePageId", HomePageId);
            query.setParameter("AnotherUserId", AnotherUserId);
            List<Post> posts =  query.getResultList();
            return posts;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException: " + e.getMessage());
        }
    }

    public List getListUsers (JSONArray arr) throws InternalServerException {
        try {
            String usersId = arr.toString();
            usersId = usersId.replace("[", "(");
            usersId = usersId.replace("]", ")");
            Query query = entityManager.createNativeQuery(queryGetListUsers + usersId, User.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    public List getNews( List<Long> idUsers, int k) throws InternalServerException {
        try {
            Query query;
            query = entityManager.createNativeQuery(queryGetNews,  Post.class);
            query.setParameter("id", idUsers);
            query.setParameter("k", k);
            List<Post> posts =  query.getResultList();
            return posts;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException: " + e.getMessage());
        }
    }


}
