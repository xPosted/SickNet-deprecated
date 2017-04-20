package com.jubaka.sors.appserver.service;

import com.jubaka.sors.beans.branch.IPItemBean;
import com.jubaka.sors.beans.branch.SessionBean;
import com.jubaka.sors.appserver.dao.HostDao;
import com.jubaka.sors.appserver.entities.Host;
import com.jubaka.sors.appserver.entities.Session;
import com.jubaka.sors.appserver.entities.Subnet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 03.11.16.
 */

@Named
@ApplicationScoped
public class HostService {

    @Inject
    private HostDao hostDao;

    @Inject
    private SessionService sessionService;

    @Transactional
    public Host insert(Host h) {
        return hostDao.insert(h);
    }

    public Host selectById(Long id) {
        return hostDao.selectById(id);
    }

    @Transactional
    public Host selectByIdWithSesWithHttp(Long id) {
        return hostDao.selectByIdWithSesWithHttp(id);

    }

    public Host update(Host h) {
        return hostDao.update(h);
    }

    public Host prepareHostToPersist(IPItemBean ipaddr, Subnet subnet, SessionEntitiesCreator sesCreator) {
        Host host = new Host();
        host.setIp(ipaddr.getIp());
        host.setDataDown(ipaddr.getDataDown());
        host.setDataUp(ipaddr.getDataUp());
        host.setDnsName(ipaddr.getDnsName());
        host.setActiveCount(ipaddr.getActiveCount());
        host.setSavedCount(ipaddr.getSavedCount());
        host.setInputCount(ipaddr.getInputCount());
        host.setOutputCount(ipaddr.getOutputCount());
        host.setInputActiveCount(ipaddr.getInputActiveCount());
        host.setOutputActiveCount(ipaddr.getOutputActiveCount());
        host.setSubnet(subnet);

        List<Session> sessionsInput = new ArrayList<>();
        List<Session> sessionsOutput = new ArrayList<>();

        for  (SessionBean ses : ipaddr.getActiveInSes()) {
            sessionsInput.add(sesCreator.createEntityLikeInputSession(host,ses));
        }

        for  (SessionBean ses : ipaddr.getActiveOutSes()) {
            sessionsOutput.add(sesCreator.createEntityLikeOutputSession(host,ses));
        }

        for  (SessionBean ses : ipaddr.getStoredInSes()) {
            sessionsInput.add(sesCreator.createEntityLikeInputSession(host,ses));
        }

        for  (SessionBean ses : ipaddr.getStoredOutSes()) {
            sessionsOutput.add(sesCreator.createEntityLikeOutputSession(host,ses));
        }
        host.setSessionsInput(sessionsInput);
        host.setSessionsOutput(sessionsOutput);
        return host;

    }

}
