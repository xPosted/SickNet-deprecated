package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 26.10.16.
 */

@Named
@ApplicationScoped
public class BranchDao  {

    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    @Inject
    private HostDao hostDao;

    @Inject
    private HttpRequestDao httpRequestDao;

    @Inject
    private HttpResponseDao httpResponseDao;

    @Transactional
    public Branch selectById(Long id) {
        return entityManager.find(Branch.class,id);
    }

    @Transactional
    public Branch selectByIdWithNets(Long id) {
        Branch b = entityManager.find(Branch.class,id);
        if (b != null) b.getSubntes().size();
        return b;
    }

    @Transactional
    public Branch eagerSelectByIdWithWithSubsHostsSess(Long id) {
        Branch b = entityManager.find(Branch.class,id);
        if (b != null) {
            b.getSubntes().size();
            for (Subnet s : b.getSubntes()) {
                s.getHosts().size();
                for (Host h : s.getHosts()) {
                    h.getSessionsOutput().size();
                    h.getSessionsInput().size();
                }
            }
        }
        return b;
    }

    @Transactional
    public Branch eagerSelectByIdWithWithSubsHosts(Long id) {
        Branch b = entityManager.find(Branch.class,id);
        if (b != null) {
            b.getSubntes().size();
            for (Subnet s : b.getSubntes()) {
                s.getHosts().size();
            }
        }
        return b;
    }

    @Transactional
    public Branch selectByIdFullBranchNoPayloadv2(Long id) {
        Branch b = entityManager.find(Branch.class,id);

        for (Subnet s : b.getSubntes()) {
            for (Host h : s.getHosts()) {
                for (Session ses : h.getSessionsInput()) {
                    ses.getRequestList().size();
                    ses.getResponseList().size();
                    ses.setTcps(new ArrayList<>());
                }
                for (Session ses : h.getSessionsOutput()) {
                    ses.getRequestList().size();
                    ses.getResponseList().size();
                    ses.setTcps(new ArrayList<>());
                }
            }
        }
        return b;
    }

    @Transactional
    public Branch selectByIdFullBranchNoPayload(Long id) {
        Branch b = null;
        Query q = entityManager.createQuery("select b from Branch b left join fetch b.subntes where b.id = :id");
        q.setParameter("id",id);
        b = (Branch) q.getSingleResult();
        for (Subnet s: b.getSubntes()) {
            List<Host> hostVsSes = hostDao.selectBySubnetWithSessions(s);
            for (Host h : hostVsSes) {
                for (Session ses : h.getSessionsInput()) {
                    ses.setRequestList(httpRequestDao.selectBySessionNoTcp(ses));
                    ses.setResponseList(httpResponseDao.selectBySessionNoTcp(ses));
                }
                for (Session ses : h.getSessionsOutput()) {
                    ses.setRequestList(httpRequestDao.selectBySessionNoTcp(ses));
                    ses.setResponseList(httpResponseDao.selectBySessionNoTcp(ses));
                }
            }
            s.setHosts(hostVsSes);
        }
        return b;
    }

    @Transactional
    public Branch update(Branch br) {
        Branch resBr = entityManager.merge(br);
        entityManager.flush();
        return resBr;
    }

    @Transactional
    public Branch insert(Branch br) {
        entityManager.persist(br);
        return br;

    }

    @Transactional
    public void delete(Branch br)  {
        entityManager.remove(br);
    }

    @Transactional
    public void deleteById(long id) {
        Branch branchToDelete = selectById(id);
        delete(branchToDelete);
    }

    public List<Branch> selectByUser(User user) {
        List<Branch> branchs = null;
        Query q = entityManager.createQuery("select branch from Branch branch where branch.user = :user");
        q.setParameter("user",user);
        branchs = q.getResultList();
        return branchs;
    }

    public List<Branch> selectByNode(Node node) {
        List<Branch> branchs = null;
        Query q = entityManager.createQuery("select branch from Branch branch where branch.node = :node");
        q.setParameter("node",node);
        branchs = q.getResultList();
        return branchs;
    }

    public List<Branch> selectByName(String name) {
        List<Branch> branchs = null;
        Query q = entityManager.createQuery("select branch from Branch branch where branch.branchName = :name");
        q.setParameter("name",name);
        branchs = q.getResultList();
        return branchs;
    }

    public Branch selectBranchByTimeAndNode(Date creationTime, Node node) {
        Branch b = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   // datetime format in mysql
        String dbDate = sdf.format(creationTime);
        try {

            Query q = entityManager.createQuery("select branch from Branch branch where branch.createtime = '"+dbDate+"' and branch.node = :node");
         //   q.setParameter("time", creationTime,TemporalType.DATE);
            System.out.println(q);
            q.setParameter("node", node);
            b = (Branch) q.getSingleResult();
        } catch(NoResultException no) {
            System.out.println("no branch result");
        }
        return b;
    }

    @Transactional
    public void deleteIfExist(Date creationTime, Node node) {
        Branch b = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   // datetime format in mysql
        String dbDate = sdf.format(creationTime);
        try {

            Query q = entityManager.createQuery("select branch from Branch branch where branch.createtime = '"+dbDate+"' and branch.node = :node");
            //   q.setParameter("time", creationTime,TemporalType.DATE);
            System.out.println(q);
            q.setParameter("node", node);
            b = (Branch) q.getSingleResult();
            if (b != null) entityManager.remove(b);
        } catch(NoResultException no) {
            System.out.println("no branch result");
        }

    }

    @Transactional
    public void updateStatData(Branch b) {
        Query q =entityManager.createQuery("update Branch b set b.branchName = :branchName, b.fileName = :fileName, " +
                "b.fileSize = :fileSize, b.iface = :iface, b.createtime = :createtime, b.modifyTime = :modifyTime, " +
                "b.description = :description, b.state = :state, b.subnet_count = :subnet_count, b.hosts_count = :hosts_count, " +
                "b.sessions_count = :sessions_count where b.id = :id");
        q.setParameter("branchName",b.getBranchName());
        q.setParameter("fileName",b.getFileName());
        q.setParameter("fileSize", b.getFileSize());
        q.setParameter("iface", b.getIface());
        q.setParameter("createtime", b.getCreatetime());
        q.setParameter("modifyTime",b.getModifyTime());
        q.setParameter("description",b.getDescription());
        q.setParameter("state",b.getState());
        q.setParameter("subnet_count",b.getSubnet_count());
        q.setParameter("hosts_count",b.getHosts_count());
        q.setParameter("sessions_count",b.getSessions_count());
        q.setParameter("id",b.getDbid());
        q.executeUpdate();
    }




}
