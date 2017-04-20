package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.Session;
import com.jubaka.sors.appserver.entities.TcpPacket;
import com.jubaka.sors.appserver.service.BeanEntityConverter;
import com.jubaka.sors.appserver.service.BranchService;
import com.jubaka.sors.desktop.protocol.tcp.TCP;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.TreeMap;

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


    public List<TcpPacket> selectWithPayloadBySession(Session ses) {
        Query q = entityManager.createQuery("SELECT DISTINCT  tcp from TcpPacket tcp left JOIN FETCH tcp.payload where tcp.session = :ses");
        q.setParameter("ses",ses);

        return q.getResultList();
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
