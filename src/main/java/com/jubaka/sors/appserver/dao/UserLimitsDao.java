package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.UserLimits;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.nio.file.attribute.UserPrincipalLookupService;

/**
 * Created by root on 12.05.17.
 */
@Named
@ApplicationScoped
public class UserLimitsDao {

    @PersistenceContext(unitName = "SorsPersistence", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    public UserLimits selectById(long id) {
        return entityManager.find(UserLimits.class,id);
    }

    @Transactional
    public UserLimits insert(UserLimits lims) {
        entityManager.persist(lims);
        return lims;
    }

    @Transactional
    public void delete(UserLimits lims) {
        entityManager.remove(lims);
    }

    @Transactional
    public UserLimits getLimitsByValues(long totalTaskLen, long totalDataLen) {
        UserLimits result = null;
        try {
            Query q = entityManager.createQuery("select lim from UserLimits lim where lim.totalTasksLen = :tasksLen and lim.totalDataLen = :dataLen");
            q.setParameter("tasksLen",totalTaskLen);
            q.setParameter("dataLen",totalDataLen);
            result =  (UserLimits) q.getSingleResult();
        } catch (NoResultException noRes) {

        }
        return result;

    }

}
