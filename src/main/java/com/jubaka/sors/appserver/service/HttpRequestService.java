package com.jubaka.sors.appserver.service;

import com.jubaka.sors.appserver.dao.HttpRequestDao;
import com.jubaka.sors.appserver.entities.HttpRequest;
import com.jubaka.sors.appserver.entities.Session;
import com.jubaka.sors.appserver.entities.TcpPacket;
import com.jubaka.sors.beans.branch.SessionBean;
import com.jubaka.sors.desktop.http.HTTPRequest;
import sun.rmi.transport.tcp.TCPEndpoint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by root on 30.12.16.
 */

@Named
@ApplicationScoped
public class HttpRequestService {

    @Inject
    private HttpRequestDao requestDao;

    public HttpRequest persist(HttpRequest req) {
        return requestDao.insert(req);
    }

    public HttpRequest update(HttpRequest req) {
        return requestDao.update(req);
    }

    public HttpRequest selectById(Long id) {
        return requestDao.selectById(id);
    }

    public static HttpRequest prepareEntity(TcpPacket tcp,HTTPRequest req, Integer seq, Session ses) {

        HttpRequest entity = new HttpRequest();
        entity.setAccept(req.getAccept());
        entity.setAccept_Charset(req.getAccept_Charset());
        entity.setAccept_Encoding(req.getAccept_Encoding());
        entity.setAccept_Language(req.getAccept_Language());
        entity.setAccept_Ranges(req.getAccept_Ranges());
        entity.setAuthorization(req.getAuthorization());
        entity.setCache_Control(req.getCache_Control());
        entity.setConnection(req.getConnection());
        entity.setContent_Length(req.getContent_Length());
        entity.setContent_Type(req.getContent_Type());
        entity.setCookie(req.getCookie());
        entity.setDate(req.getDate());
        entity.setHost(req.getHost());
        entity.setIf_Modified_Since(req.getIf_Modified_Since());
        entity.setIf_None_Match(req.getIf_None_Match());
        entity.setProxy_Connection(req.getProxy_Connection());
        entity.setReferer(req.getReferer());
        entity.setRequestMethod(req.getRequestMethod());
        entity.setRequestUrl(req.getRequestUrl());
        entity.setRequestVersion(req.getRequestVersion());
        entity.setUA_CPU(req.getUA_CPU());
        entity.setUser_Agent(req.getUser_Agent());
        entity.setSequence(seq);
        entity.setTcpP(tcp);
        entity.setSession(ses);

        return entity;

    }
}
