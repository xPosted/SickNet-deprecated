package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.*;
import com.jubaka.sors.desktop.protocol.tcp.TCP;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by root on 26.10.16.
 */

@Named
@ApplicationScoped
public class SessionDao  {
    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    @Inject
    private PayloadDao payloadDao;
    @Inject
    private TcpPacketDao tcpPacketDao;
    @Inject
    private HttpRequestDao httpRequestDao;
    @Inject
    private HttpResponseDao httpResponseDao;

    public Session selectById(Long id) {

        Session ses = entityManager.find(Session.class,id);
        ses.setResponseList(new ArrayList<>());
        ses.setRequestList(new ArrayList<>());
        ses.setTcps(new ArrayList<>());

        return ses;

    }

    public Session selectByDateAndBrid() {

    }

    @Transactional
    public Session selectByIdWithData(Long id) {
      //  Session entity = entityManager.find(Session.class,id);

        Query q = entityManager.createQuery("select s from Session s left join fetch s.requestList left join fetch s.responseList left join fetch s.tcps t left join fetch t.payload where s.id = :sesid");
        q.setParameter("sesid",id);
        Session ent = (Session) q.getSingleResult();

/*

        entity.getRequestList().size();   // overhead - double quering tcp packet
        entity.getResponseList().size();  // overhead - double quering tcp packet

        List<TcpPacket> tcpEntities = tcpPacketDao.selectWithPayloadBySession(entity);
        HashMap<Long,TcpPacket> idTcpMap = new HashMap<>();
        for (TcpPacket tcp : tcpEntities) {
            idTcpMap.put(tcp.getId(),tcp);
        }


        for (HttpRequest req : entity.getRequestList()) {
            req.setTcpP(idTcpMap.get(req.getTcpP().getId()));
        }
        for (HttpResponse resp : entity.getResponseList()) {
            resp.setTcpP(idTcpMap.get(resp.getTcpP().getId()));
        }
        entity.setTcps(tcpEntities);
*/

       // entity.getTcps().size();
        /* for (TcpPacket tcp : entity.getTcps()){
            PacketPayload payload = payloadDao.selectById(tcp.getPayload().getId());
            tcp.setPayload(payload);
        }
        */
        return  ent;
    }

    @Transactional
    public List<Session> selectEmptyBySrcHost(Host srcHost) {
        Query q = entityManager.createQuery("select ses from Session ses where ses.srcHost = :hostId");
        q.setParameter("hostId",srcHost);
        List<Session> sessions = q.getResultList();
        for (Session ses : sessions) {
            ses.setResponseList(new ArrayList<>());
            ses.setRequestList(new ArrayList<>());
            ses.setTcps(new ArrayList<>());


        }
        return  sessions;
    }

    @Transactional
    public List<Session> selectEmptyByDstHost(Host dstHost) {
        Query q = entityManager.createQuery("select ses from Session ses where ses.dstHost = :hostId");
        q.setParameter("hostId",dstHost);
        List<Session> sessions = q.getResultList();
        for (Session ses : sessions) {
            ses.setResponseList(new ArrayList<>());
            ses.setRequestList(new ArrayList<>());
            ses.setTcps(new ArrayList<>());


        }
        return  sessions;
    }

    @Transactional
    public List<Session> selectWithHttpBySrcHost(Host srcHost) {
        Query q = entityManager.createQuery("select ses from Session ses where ses.srcHost = :host");
        q.setParameter("host",srcHost);
        List<Session> resulstSet = q.getResultList();
        for (Session s : resulstSet) {
            s.setRequestList(httpRequestDao.selectBySessionEagerTcp(s));
            s.setResponseList(httpResponseDao.selectBySessionEagerTcp(s));
            s.setTcps(new ArrayList<>());
        }
        return  resulstSet;
    }
    @Transactional
    public List<Session> selectWithHttpByDstHost(Host dstHost) {
        Query q = entityManager.createQuery("select ses from Session ses where ses.dstHost = :host");
        q.setParameter("host",dstHost);
        List<Session> resulstSet = q.getResultList();
        for (Session s : resulstSet) {
            s.setRequestList(httpRequestDao.selectBySessionEagerTcp(s));
            s.setResponseList(httpResponseDao.selectBySessionEagerTcp(s));
            s.setTcps(new ArrayList<>());
        }
        return  resulstSet;
    }


    public Session update(Session ses) {
        Session resBr = entityManager.merge(ses);
        entityManager.flush();
        return resBr;
    }

    public Session insert(Session ses) {
        entityManager.persist(ses);
        return ses;

    }
}
