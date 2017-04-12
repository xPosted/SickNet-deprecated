package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.Subnet;

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
    public Subnet eagerSelectById(Long id) {
        Subnet s = entityManager.find(Subnet.class,id);
        if (s!=null) s.getHosts().size();
        return s;
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
