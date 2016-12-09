package com.jubaka.sors.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
