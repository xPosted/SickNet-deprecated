package com.jubaka.sors.appserver.entities;

import net.sf.ehcache.config.PersistenceConfiguration;

import javax.persistence.*;

/**
 * Created by root on 17.04.17.
 */

@Entity
public class PacketPayload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Lob
    private byte[] payload;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

}
