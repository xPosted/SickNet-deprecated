package com.jubaka.sors.appserver.service;

import com.jubaka.sors.beans.branch.SessionBean;
import com.jubaka.sors.appserver.entities.*;
import com.jubaka.sors.desktop.http.HTTP;
import com.jubaka.sors.desktop.http.HTTPRequest;
import com.jubaka.sors.desktop.http.HTTPResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            entity.setRequestList(getHttpRequestList(sesBean.getHttpBuf()));
            entity.setResponseList(getHttpResponseList(sesBean.getHttpBuf()));

    }

    private List<HttpRequest> getHttpRequestList(List<HTTP> https) {
        Integer counter = 0;
        List<HttpRequest> requests = new ArrayList<>();
        for (HTTP http : https) {
            if (http instanceof HTTPRequest) {
                HTTPRequest req = (HTTPRequest) http;
                requests.add(HttpRequestService.prepareEntity(req,counter));
            }
            counter++;
        }
        return requests;
    }

    private List<HttpResponse> getHttpResponseList(List<HTTP> https) {
        Integer counter = 0;
        List<HttpResponse> responses = new ArrayList<>();
        for (HTTP http : https) {
            if (http instanceof HTTPResponse) {
                HTTPResponse resp = (HTTPResponse) http;
                responses.add(HttpResponseService.prepareEntity(resp,counter));

            }
            counter++;
        }
        return responses;
    }



}
