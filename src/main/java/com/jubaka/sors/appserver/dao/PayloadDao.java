package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.PacketPayload;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Created by root on 18.04.17.
 */

@Named
@ApplicationScoped
public class PayloadDao {

    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

   public PacketPayload selectById(Long id) {
        return  entityManager.find(PacketPayload.class,id);
   }


}
