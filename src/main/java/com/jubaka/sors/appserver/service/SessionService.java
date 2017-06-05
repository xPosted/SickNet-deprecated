package com.jubaka.sors.appserver.service;

import com.jubaka.sors.appserver.dao.SessionDao;
import com.jubaka.sors.appserver.entities.Session;
import com.jubaka.sors.beans.branch.SessionBean;
import com.jubaka.sors.desktop.remote.BeanConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public Session selectByIdWithData(Long id) {
        return sessionDao.selectByIdWithData(id);
    }

    public Session selectById(Long id) {
        return sessionDao.selectById(id);
    }

    public Session update(Session ses) {
        return sessionDao.update(ses);
    }

    public List<SessionBean> getByTaskAndSrcHostAndTime(long taskId, String srcHost, Date est) {
        List<Session> enteties = sessionDao.selectByTaskAndSrcHostAndTime(taskId,srcHost,est);
        List<SessionBean> resultBeans = new ArrayList<>();
        BeanEntityConverter converter = new BeanEntityConverter();
        for (Session ent : enteties) {
            SessionBean sesBean = BeanEntityConverter.castToBean(ent,false);
            resultBeans.add(sesBean);
        }
        return resultBeans;
    }
}


