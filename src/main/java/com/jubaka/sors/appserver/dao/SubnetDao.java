package com.jubaka.sors.dao;

import com.jubaka.sors.entities.Subnet;

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
public class SubnetDao {
    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    public Subnet selectById(Long id) {
        return entityManager.find(Subnet.class,id);
    }

    @Transactional
    public Subnet update(Subnet sub) {
        Subnet resBr = entityManager.merge(sub);
        entityManager.flush();
        return resBr;
    }
    @Transactional
    public Subnet insert(Subnet sub) {
        entityManager.persist(sub);
        return sub;

    }
}
