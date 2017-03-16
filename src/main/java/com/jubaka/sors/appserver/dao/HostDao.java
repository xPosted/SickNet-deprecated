package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.Host;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * Created by root on 03.11.16.
 */

@Named
@ApplicationScoped
public class HostDao implements Serializable {

    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    public Host selectById(Long id) {
        return entityManager.find(Host.class,id);
    }

    @Transactional
    public Host update(Host h) {
        Host resBr = entityManager.merge(h);
        entityManager.flush();
        return resBr;
    }
    @Transactional
    public Host insert(Host h) {
        entityManager.persist(h);
        return h;

    }
}
