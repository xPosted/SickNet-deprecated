package com.jubaka.sors.dao;

import com.jubaka.sors.entities.Node;
import com.jubaka.sors.entities.NodeActiveCheckPoint;
import com.jubaka.sors.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by root on 28.10.16.
 */

@Named
@ApplicationScoped
public class NodeActiveCheckPointDao  {

    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    public NodeActiveCheckPoint selectByNodeId(Node node) {
        Object queryResult = null;
        try {
            Query q = entityManager.createQuery("select checkpoint from NodeActiveCheckPoint checkpoint where checkpoint.node = :node");
            q.setParameter("node",node);
            queryResult = q.getSingleResult();
        } catch (NoResultException no) {

        }

        if (queryResult == null) return null;
        return (NodeActiveCheckPoint) queryResult;

    }

    @Transactional
    public void remove(NodeActiveCheckPoint checkPoint) {
        Query q = entityManager.createQuery("delete from NodeActiveCheckPoint point where point.id = :id");
        q.setParameter("id",checkPoint.getId());
        q.executeUpdate();
    }

    public List<NodeActiveCheckPoint> selectCheckPointsByUser(User u) {

       Query q = entityManager.createQuery("select point from NodeActiveCheckPoint point where point.user = :user");
        q.setParameter("user",u);
        return (List<NodeActiveCheckPoint>) q.getResultList();

    }

    public NodeActiveCheckPoint selectById(Long id) {
        return entityManager.find(NodeActiveCheckPoint.class,id);
    }

    @Transactional
    public NodeActiveCheckPoint insert(NodeActiveCheckPoint checkPoint) {
        entityManager.persist(checkPoint);
        return checkPoint;
    }

    @Transactional
    public NodeActiveCheckPoint update(NodeActiveCheckPoint checkPoint) {
        checkPoint = entityManager.merge(checkPoint);
        entityManager.flush();
        return checkPoint;
    }


}
