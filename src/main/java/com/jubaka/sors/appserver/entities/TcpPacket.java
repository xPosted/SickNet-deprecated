package com.jubaka.sors.appserver.entities;

import com.jubaka.sors.desktop.sessions.IPaddr;

import javax.persistence.*;

/**
 * Created by root on 10.04.17.
 */

@Entity
public class TcpPacket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sesId")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "srcHostId")
    private Host srcHost = null;

    @ManyToOne
    @JoinColumn(name = "dstHostId")
    private Host dstHost = null;

    private Integer srcPort = null;
    private Integer dstPort = null;
    private Long timestamp = null;
    @Lob
    private byte[] payload;


    public Host getSrcHost() {
        return srcHost;
    }

    public void setSrcHost(Host srcHost) {
        this.srcHost = srcHost;
    }

    public Host getDstHost() {
        return dstHost;
    }

    public void setDstHost(Host dstHost) {
        this.dstHost = dstHost;
    }

    public Integer getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(Integer srcPort) {
        this.srcPort = srcPort;
    }

    public Integer getDstPort() {
        return dstPort;
    }

    public void setDstPort(Integer dstPort) {
        this.dstPort = dstPort;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
