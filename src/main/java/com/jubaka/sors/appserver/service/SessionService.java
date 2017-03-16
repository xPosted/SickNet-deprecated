package com.jubaka.sors.service;

import com.jubaka.sors.beans.branch.SessionBean;
import com.jubaka.sors.beans.branch.SessionDataBean;
import com.jubaka.sors.dao.SessionDao;
import com.jubaka.sors.entities.Host;
import com.jubaka.sors.entities.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * Created by root on 03.11.16.
 */
@Named
@ApplicationScoped
public class SessionService {

    @Inject
    private SessionDao sessionDao;
    @Inject
    private NodeService nodeService;

    @Transactional
    public Session insert(Session ses) {
        return sessionDao.insert(ses);
    }

    public Session selectById(Long id) {
        return sessionDao.selectById(id);
    }

    public Session update(Session ses) {
        return sessionDao.update(ses);
    }


}


