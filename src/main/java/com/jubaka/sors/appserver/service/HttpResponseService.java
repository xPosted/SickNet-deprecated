package com.jubaka.sors.appserver.service;

import com.jubaka.sors.appserver.dao.HttpResponseDao;
import com.jubaka.sors.appserver.entities.HttpResponse;
import com.jubaka.sors.appserver.entities.Session;
import com.jubaka.sors.appserver.entities.TcpPacket;
import com.jubaka.sors.desktop.protocol.application.HTTPResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by root on 30.12.16.
 */

@Named
@ApplicationScoped
public class HttpResponseService {

    @Inject
    private HttpResponseDao responseDao;

    public HttpResponse persist(HttpResponse resp) {
        return responseDao.insert(resp);
    }

    public HttpResponse update(HttpResponse resp) {
        return responseDao.update(resp);
    }

    public HttpResponse selectById(Long id) {
        return responseDao.selectById(id);
    }

    public static HttpResponse prepareEntity(TcpPacket tcp, HTTPResponse resp, Session ses) {
        HttpResponse entity = new HttpResponse();


        entity.setRequestVersion(resp.getRequestVersion());
        entity.setRequestUrl(resp.getRequestUrl());
        entity.setAccept_Ranges(resp.getAccept_Ranges());
        entity.setAge(resp.getAge());
        entity.setAllow(resp.getAllow());
        entity.setCache_Control(resp.getCache_Control());
        entity.setContent_Disposition(resp.getContent_Disposition());
        entity.setContent_Encoding(resp.getContent_Encoding());
        entity.setContent_Length(resp.getContent_Length());
        entity.setContent_Location(resp.getContent_Location());
        entity.setContent_MD5(resp.getContent_MD5());
        entity.setContent_Range(resp.getContent_Range());
        entity.setContent_Type(resp.getContent_Type());
        entity.setExpires(resp.getExpires());
        entity.setResponseCode(resp.getResponseCode());
        entity.setResponseCodeMsg(resp.getResponseCodeMsg());
        entity.setServer(resp.getServer());
        entity.setSet_Cookie(resp.getSet_Cookie());
        entity.setTcpP(tcp);
        entity.setPayloadOffset(resp.getPayloadOffset());
        entity.setSession(ses);

        return entity;

    }
}
