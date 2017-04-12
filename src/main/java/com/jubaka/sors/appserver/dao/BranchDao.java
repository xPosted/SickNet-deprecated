package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
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

    @Transactional
    public Branch selectById(Long id) {
        return entityManager.find(Branch.class,id);
    }

    @Transactional
    public Branch eagerSelectById(Long id) {
        Branch b = entityManager.find(Branch.class,id);
        if (b != null) b.getSubntes().size();
        return b;
    }

    @Transactional
    public Branch eagerAllSelectById(Long id) {
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
    public void delete (Branch br)  {
        entityManager.remove(br);
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




}
