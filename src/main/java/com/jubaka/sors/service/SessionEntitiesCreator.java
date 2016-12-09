package com.jubaka.sors.service;

import com.jubaka.sors.beans.branch.SessionBean;
import com.jubaka.sors.entities.Host;
import com.jubaka.sors.entities.Session;
import com.jubaka.sors.entities.SessionChart;

import java.util.HashMap;

/**
 * Created by root on 04.11.16.
 */
public class SessionEntitiesCreator {

    private HashMap<Long, Session> sessionMap = new HashMap<>();

    public Session createEntityLikeInputSession(Host host, SessionBean sesBean) {
        Session entity = null;
        if (sessionMap.containsKey(sesBean.getInitSeq()))
            entity = sessionMap.get(sesBean.getInitSeq());
        else {
            entity = new Session();
            sessionMap.put(sesBean.getInitSeq(),entity);
        }
        fillEntity(entity, sesBean);
        entity.setDstHost(host);
        return entity;
    }

    public Session createEntityLikeOutputSession(Host host, SessionBean sesBean) {
        Session entity = null;
        if (sessionMap.containsKey(sesBean.getInitSeq()))
            entity = sessionMap.get(sesBean.getInitSeq());
        else {
            entity = new Session();
            sessionMap.put(sesBean.getInitSeq(),entity);
        }
        fillEntity(entity, sesBean);
        entity.setSrcHost(host);
        return entity;
    }

    private void fillEntity(Session entity, SessionBean sesBean) {

            entity.setInitSeq(sesBean.getInitSeq());
            entity.setSrcP(sesBean.getSrcP());
            entity.setDstP(sesBean.getDstP());
            entity.setClosed(sesBean.getClosed());
            entity.setDstDataLen(sesBean.getDstDataLen());
            entity.setSrcDataLen(sesBean.getSrcDataLen());
            entity.setEstablished(sesBean.getEstablished());

            SessionChart chart = new SessionChart();
            chart.setSrcDataTime(sesBean.getSrcDataTimeBinding());
            chart.setDstDataTime(sesBean.getDstDataTimeBinding());

            entity.setChartData(chart);

    }



}
