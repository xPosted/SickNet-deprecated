package com.jubaka.sors.appserver.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by root on 24.10.16.
 */

@Named
@ApplicationScoped
public class TestDao {

    @PersistenceContext(unitName = "SorsPersistence")
    private EntityManager entityManager;

@Transactional
    public void test() {


    Persistence.generateSchema("SorsPersistence",null);

    }
}
