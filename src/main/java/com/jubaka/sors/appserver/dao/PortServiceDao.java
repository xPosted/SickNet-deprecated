package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.PortServiceBinding;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * Created by root on 24.12.16.
 */


@Named
@ApplicationScoped
public class PortServiceDao {

    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;


    public Integer getPortByServiceName (String name) {
        Query q = entityManager.createQuery("select portService from  PortServiceBinding portService where portService.serviceName = :name");
        q.setParameter("name",name);

        PortServiceBinding portSerice =  (PortServiceBinding) q.getSingleResult();
        if (portSerice == null) return null;
        return portSerice.getPortNumber();
    }

    public PortServiceBinding selectById(Integer id) {
        return entityManager.find(PortServiceBinding.class,id);

    }
    @Transactional
    public PortServiceBinding update(PortServiceBinding n) {
        n = entityManager.merge(n);
        entityManager.flush();
        return n;
    }

    @Transactional
    public PortServiceBinding insert(PortServiceBinding n) {
        entityManager.persist(n);
        return n;
    }
}
