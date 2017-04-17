package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.Session;
import com.jubaka.sors.appserver.entities.TcpPacket;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

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

    @Transactional
    public Session selectByIdWithData(Long id) {
        Session entity = entityManager.find(Session.class,id);
        for (TcpPacket tcp : entity.getTcps()){
            tcp.getPayload();
        }
        return  entity;
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
