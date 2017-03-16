package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.HttpRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Created by root on 30.12.16.
 */
@Named
@ApplicationScoped
public class HttpRequestDao {

    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    public HttpRequest insert(HttpRequest req) {
        entityManager.persist(req);
        return req;
    }

    public HttpRequest update(HttpRequest req) {
        HttpRequest reqRes = entityManager.merge(req);
        entityManager.flush();
        return reqRes;
    }

    public HttpRequest selectById(Long id) {
        return entityManager.find(HttpRequest.class,id);
    }


}
