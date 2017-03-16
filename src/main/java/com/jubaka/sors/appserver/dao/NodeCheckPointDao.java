package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.NodeActiveCheckPoint;
import com.jubaka.sors.appserver.entities.NodeCheckPoint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

/**
 * Created by root on 31.10.16.
 */
@Named
@ApplicationScoped
public class NodeCheckPointDao  {

    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    @Transactional
    public void insert(NodeActiveCheckPoint activeCheckPoint) {
        NodeCheckPoint checkPoint = new NodeCheckPoint();
        checkPoint.setIp(activeCheckPoint.getIp());
        checkPoint.setDate(activeCheckPoint.getDate());
        checkPoint.setNode(activeCheckPoint.getNode());
        checkPoint.setUser(activeCheckPoint.getUser());
        entityManager.persist(checkPoint);
    }

    public NodeCheckPoint selectById(Long id) {
        return entityManager.find(NodeCheckPoint.class,id);
    }

    public NodeCheckPoint update(NodeCheckPoint item) {
        NodeCheckPoint res = entityManager.merge(item);
        entityManager.flush();
        return res;
    }

    public NodeCheckPoint insert(NodeCheckPoint item) {
        entityManager.persist(item);
        return item;
    }
}
