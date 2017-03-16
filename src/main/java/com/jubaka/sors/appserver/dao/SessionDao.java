package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Created by root on 26.10.16.
 */

@Named
@ApplicationScoped
public class SessionDao  {
    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;


    public Session selectById(Long id) {
        return entityManager.find(Session.class,id);
    }

    public Session update(Session ses) {
        Session resBr = entityManager.merge(ses);
        entityManager.flush();
        return resBr;
    }

    public Session insert(Session ses) {
        entityManager.persist(ses);
        return ses;

    }
}
