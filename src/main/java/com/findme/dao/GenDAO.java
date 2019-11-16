package com.findme.dao;

import com.findme.exception.InternalServerException;
import com.findme.models.IdEntity;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository

public abstract class GenDAO<T extends IdEntity> {

    @PersistenceContext
    public EntityManager entityManager;
    abstract Class aClass();

    @Transactional
    public T save(T t) throws InternalServerException{

        try  {
            entityManager.persist(t);
            return t;
        } catch (Exception e){
            throw new InternalServerException("InternalServerException: " + e.getMessage());
    }
    }


    public  T findById(long id) throws InternalServerException {
        try {
            return (T) entityManager.find(aClass(),id);
        } catch (Exception e) {
           throw new InternalServerException("InternalServerException: " + e.getMessage());
        }
    }

    @Transactional
    public T update(T t){
        try{
            entityManager.merge(t);
            return t;
        } catch (Exception e){
           return null;
        }
    }

    @Transactional
    public T delete(T t){
        try {
            t = findById(t.getId());
            entityManager.remove(t);
            return null;
        } catch (Exception e){
            return null;
        }
    }
}
