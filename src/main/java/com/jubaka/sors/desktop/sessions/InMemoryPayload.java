package com.jubaka.sors.desktop.sessions;

/**
 * Created by root on 11.04.17.
 */
public class InMemoryPayload implements  PayloadAcquirer{

    byte[] payload;
    public InMemoryPayload(byte[] payload) {
        this.payload = payload;
    }

    @Override
    public byte[] getPayload() {
        return payload;
    }
}
