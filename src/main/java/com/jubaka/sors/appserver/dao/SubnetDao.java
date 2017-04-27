package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.Subnet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
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
    @Transactional
    public void updateStatData(Subnet net) {
        Query q = entityManager.createQuery("update Subnet s set s.dataSend = :dataSend, s.dataReceive = :dataReceive," +
                " s.activeSesCnt = :activeSesCnt, s.activeAddrCnt = :activeAddrCnt, s.activeInSesCnt = :activeInSesCnt," +
                " s.activeOutSesCnt = :activeOutSesCnt, s.inSesCnt = :inSesCnt, s.outSesCnt = :outSesCnt," +
                " s.sesCnt = :sesCnt, s.addrCnt = :addrCnt, s.subnetMask = :subnetMask where s.id = :id");
        q.setParameter("dataSend",net.getDataSend());
        q.setParameter("dataReceive",net.getDataReceive());
        q.setParameter("activeSesCnt",net.getActiveSesCnt());
        q.setParameter("activeAddrCnt",net.getActiveAddrCnt());
        q.setParameter("activeInSesCnt",net.getActiveInSesCnt());
        q.setParameter("activeOutSesCnt",net.getActiveOutSesCnt());
        q.setParameter("inSesCnt",net.getInSesCnt());
        q.setParameter("outSesCnt",net.getOutSesCnt());
        q.setParameter("sesCnt",net.getSesCnt());
        q.setParameter("addrCnt",net.getAddrCnt());
        q.setParameter("subnetMask",net.getSubnetMask());
        q.setParameter("id",net.getId());
        q.executeUpdate();

    }
}
