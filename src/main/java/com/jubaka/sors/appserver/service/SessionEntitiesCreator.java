package com.jubaka.sors.appserver.service;

import com.jubaka.sors.beans.branch.SessionBean;
import com.jubaka.sors.appserver.entities.*;
import com.jubaka.sors.desktop.http.HTTP;
import com.jubaka.sors.desktop.http.HTTPRequest;
import com.jubaka.sors.desktop.http.HTTPResponse;
import com.jubaka.sors.desktop.http.protocol.tcp.TCP;

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
        if (sessionMap.containsKey(sesBean.getInitSeq())) {
            entity = sessionMap.get(sesBean.getInitSeq());
            fillEntity(entity, sesBean);
        }
        else {
            entity = new Session();
            sessionMap.put(sesBean.getInitSeq(),entity);
        }

        entity.setDstHost(host);
        return entity;
    }

    public Session createEntityLikeOutputSession(Host host, SessionBean sesBean) {
        Session entity = null;
        if (sessionMap.containsKey(sesBean.getInitSeq())) {
            entity = sessionMap.get(sesBean.getInitSeq());
            fillEntity(entity, sesBean);
        }
        else {
            entity = new Session();
            sessionMap.put(sesBean.getInitSeq(),entity);
        }

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
          //  entity.setRequestList(getHttpRequestList(entity, sesBean));
          //  entity.setResponseList(getHttpResponseList(entity,sesBean));
            fillPackets(entity,sesBean);
    }

    private void fillPackets(Session entity, SessionBean sesBean) {
        Integer counterTcp = 0;
        List<TcpPacket> tcps = new ArrayList<>();
        List<HttpRequest> requests = new ArrayList<>();
        List<HttpResponse> responses = new ArrayList<>();

        for (TCP tcp : sesBean.getTcpBuf()) {
            TcpPacket tcpEnt = new TcpPacket();
            tcpEnt.setSession(entity);
            if (tcp.isStraight()) {
                tcpEnt.setDstHost(entity.getDstHost());
                tcpEnt.setSrcHost(entity.getSrcHost());
            } else {
                tcpEnt.setDstHost(entity.getSrcHost());
                tcpEnt.setSrcHost(entity.getDstHost());
            }
            tcpEnt.setDstPort(tcp.getDstPort());
            tcpEnt.setSrcPort(tcp.getSrcPort());
            tcpEnt.setTimestamp(tcp.getTimestamp());
            tcpEnt.setPayload(tcp.getPayload());
            if (tcp instanceof HTTPRequest) {
               HTTPRequest req = (HTTPRequest) tcp;
                requests.add(HttpRequestService.prepareEntity(tcpEnt,req,counterTcp,entity));

            }
            if (tcp instanceof HTTPResponse) {
                HTTPResponse resp = (HTTPResponse) tcp;
                responses.add(HttpResponseService.prepareEntity(tcpEnt,resp,counterTcp, entity));
            }
            tcps.add(tcpEnt);
            counterTcp++;
        }
        entity.setTcps(tcps);
        entity.setRequestList(requests);
        entity.setResponseList(responses);
    }

    private List<HttpRequest> getHttpRequestList(Session entity, SessionBean sesBean) {
        Integer counter = 0;
        List<HttpRequest> requests = new ArrayList<>();
        for (HTTP http : sesBean.getHttpBuf()) {
            if (http instanceof HTTPRequest) {
                HTTPRequest req = (HTTPRequest) http;
                requests.add(HttpRequestService.prepareEntity(null,req,counter,entity));
            }
            counter++;
        }
        return requests;
    }

    private List<HttpResponse> getHttpResponseList(Session entity, SessionBean sesBean) {
        Integer counter = 0;
        List<HttpResponse> responses = new ArrayList<>();
        for (HTTP http : sesBean.getHttpBuf()) {
            if (http instanceof HTTPResponse) {
                HTTPResponse resp = (HTTPResponse) http;
                responses.add(HttpResponseService.prepareEntity(null,resp,counter, entity));

            }
            counter++;
        }
        return responses;
    }



}
