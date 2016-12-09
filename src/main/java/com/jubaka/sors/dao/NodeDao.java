package com.jubaka.sors.dao;

import com.jubaka.sors.entities.Node;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.transaction.Transactional;

/**
 * Created by root on 26.10.16.
 */

@Named
@ApplicationScoped
public class NodeDao {


    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    public Node getNodeByName(String name) {
        Query q = entityManager.createQuery("select node from Node node where node.nodeName = :name");
        q.setParameter("name",name);

        return (Node) q.getSingleResult();
    }

    public Node selectById(Long id) {
        return entityManager.find(Node.class,id);

    }
    @Transactional
    public Node update(Node n) {
        n = entityManager.merge(n);
        entityManager.flush();
        return n;
    }

    @Transactional
    public Node insert(Node n) {
        entityManager.persist(n);
        return n;
    }


}
