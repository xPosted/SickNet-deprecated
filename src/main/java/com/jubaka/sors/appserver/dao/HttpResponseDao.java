package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.HttpResponse;

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
public class HttpResponseDao {

    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    public HttpResponse insert(HttpResponse resp) {
        entityManager.persist(resp);
        return resp;
    }

    public HttpResponse update(HttpResponse resp) {
        HttpResponse respRes = entityManager.merge(resp);
        entityManager.flush();
        return respRes;

    }

    public HttpResponse selectById(Long id) {
        return entityManager.find(HttpResponse.class,id);

    }
}
