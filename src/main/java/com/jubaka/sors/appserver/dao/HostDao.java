package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.Host;
import com.jubaka.sors.appserver.entities.Session;
import com.jubaka.sors.appserver.entities.Subnet;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.TransientReference;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 03.11.16.
 */

@Named
@ApplicationScoped
public class HostDao implements Serializable {

    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    @Inject
    private SessionDao sessionDao;

    public Host selectById(Long id) {
        return entityManager.find(Host.class,id);
    }

    @Transactional
    public Host selectByIdWithSesWithHttpv2(Long id) {
      Host h = entityManager.find(Host.class,id);
      for (Session s : h.getSessionsOutput()) {
          s.getRequestList().size();
          s.getResponseList().size();
          s.setTcps(new ArrayList<>());
      }
      for (Session s : h.getSessionsInput()) {
          s.getRequestList().size();
          s.getResponseList().size();
          s.setTcps(new ArrayList<>());
      }
      return h;

    }

    @Transactional
    public Host selectByIdWithSesWithHttp(Long id) {

      //  Host h = entityManager.find(Host.class,id);
        Query q = entityManager.createQuery("select h from Host h left join fetch h.sessionsInput sesIn " +
                "left join fetch sesIn.requestList left join fetch sesIn.responseList " +
                "left join fetch h.sessionsOutput sesOut " +
                "left join fetch sesOut.requestList left join fetch sesOut.responseList " +
                "where h.id = :hostId");

        q.setParameter("hostId",id);
        Host h = (Host) q.getSingleResult();
        if (h != null) {
                for (Session s : h.getSessionsInput()) s.setTcps(new ArrayList<>());
                for (Session s : h.getSessionsOutput()) s.setTcps(new ArrayList<>());
        }
        return h;
    }

    @Transactional
    public List<Host> selectBySubnetWithSessions(Subnet s) {
        Query q = entityManager.createQuery("select h from Host h left join h.sessionsInput sesIn left join h.sessionsOutput where h.subnet = :net");
        q.setParameter("net",s);
        return q.getResultList();

    }

    @Transactional
    public Host update(Host h) {
        Host resBr = entityManager.merge(h);
        entityManager.flush();
        return resBr;
    }
    @Transactional
    public Host insert(Host h) {
        entityManager.persist(h);
        return h;

    }
}
