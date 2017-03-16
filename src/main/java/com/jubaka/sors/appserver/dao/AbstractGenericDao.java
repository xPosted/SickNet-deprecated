package com.jubaka.sors.appserver.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 26.10.16.
 */

public abstract class AbstractGenericDao<T> implements Serializable {

    @PersistenceContext(unitName = "SorsPersistence",type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;
    private Class<T> type;
  //  protected Class entityClass;

    public AbstractGenericDao() {
        type = (Class<T>) ( getClass()
                .getGenericSuperclass());

        /*
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType)t;
        type = (Class<T>)pt.getActualTypeArguments()[1];
        */

    }


    public List<T> selectAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> rootEntry = cq.from(type);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
//        Query query = entityManager.getCriteriaBuilder().createQuery(type).from(type).;
//        List<T> entities = query.getResultList();
//        return entities;
    }

    public T selectById(Long id) {
        return (T)entityManager.find(type, id);
    }
    @Transactional
    public T update(T entity) {
        T merge = entityManager.merge(entity);
        entityManager.flush();
        return merge;
    }

    @Transactional
    public T insert(T entity) {
        entityManager.persist(entity);
        return entity;
    }
}
