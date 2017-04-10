package com.jubaka.sors.appserver.service;

import com.jubaka.sors.appserver.dao.TcpPacketDao;
import com.jubaka.sors.appserver.entities.TcpPacket;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by root on 10.04.17.
 */

@Named
@ApplicationScoped
public class TcpPacketService {

    @Inject
    private TcpPacketDao tcpPacketDao;

    public TcpPacket selectById(Long id) {
        return tcpPacketDao.selectById(id);
    }

    public void insert(TcpPacket tcpP) {
        tcpPacketDao.insert(tcpP);
    }
    public void update(TcpPacket tcpP) {
        tcpPacketDao.update(tcpP);

    }
}
