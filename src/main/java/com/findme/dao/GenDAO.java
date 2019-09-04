package com.findme.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.models.IdEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;

@Repository

public abstract class GenDAO<T extends IdEntity> {

    @PersistenceContext
    public EntityManager entityManager;
    abstract Class aClass();

    @Transactional
    public T save(HttpServletRequest req){

        try (BufferedReader br = req.getReader()) {
            System.out.println(br);
            T t = toJavaObject(br);
            entityManager.persist(t);
            return t;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
    }
    }


    public  T findById(HttpServletRequest req) {
        try (BufferedReader br = req.getReader()){
            T t = toJavaObject(br);
            return (T) entityManager.find(aClass(),t.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Transactional
    public T update(HttpServletRequest req){
        try (BufferedReader br = req.getReader()) {
            T t = toJavaObject(br);
            entityManager.merge(t);
            return t;
        } catch (Exception e){
            System.out.println(e.getMessage());
           return null;
        }
    }

    @Transactional
    public T delete(HttpServletRequest req){

        try {
            T t = findById(req);
            entityManager.remove(t);
            return t;
        } catch (Exception e){
            e.getMessage();
            return null;
        }
    }



    private <T> T toJavaObject(BufferedReader br) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return (T)  mapper.readValue(br, aClass());
    }
}
