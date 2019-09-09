package com.findme.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.exception.InternalServerException;
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
    public T save(T t){

        try  {
            entityManager.persist(t);
            return t;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
    }
    }


    public  T findById(T t) throws InternalServerException {
        try {
            return (T) entityManager.find(aClass(),t.getId());
        } catch (Exception e) {
           throw new InternalServerException("InternalServerException");
        }
    }

    @Transactional
    public T update(T t){
        try{
            entityManager.merge(t);
            return t;
        } catch (Exception e){
            System.out.println(e.getMessage());
           return null;
        }
    }

    @Transactional
    public T delete(T t){
        try {
            t = findById(t);
            entityManager.remove(t);
            return null;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
