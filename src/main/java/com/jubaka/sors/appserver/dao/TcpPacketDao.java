package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.TcpPacket;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

/**
 * Created by root on 10.04.17.
 */

@Named
@ApplicationScoped
public class TcpPacketDao {

    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    public TcpPacket selectById(Long id) {
       return  entityManager.find(TcpPacket.class,id);

    }

    @Transactional
    public void insert(TcpPacket tcpP) {
        entityManager.persist(tcpP);
    }

    @Transactional
    public TcpPacket update(TcpPacket tcpP) {
        TcpPacket res = entityManager.merge(tcpP);
        entityManager.flush();
        return res;
    }
}
