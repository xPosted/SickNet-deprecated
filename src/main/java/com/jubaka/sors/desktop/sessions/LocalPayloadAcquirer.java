package com.jubaka.sors.desktop.sessions;

import javax.ejb.Local;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by root on 11.04.17.
 */
public class LocalPayloadAcquirer implements PayloadAcquirer {
    private long pointer = (long)0;
    private File dataFile;
    private int length;

    public LocalPayloadAcquirer(long pointer,int length, File dataFile) {
        this.dataFile = dataFile;
        this.pointer = pointer;
        this.length = length;
    }
    @Override
    public byte[] getPayload() {
        byte[] buf = null;
        try {
            if (dataFile == null) return null;
            RandomAccessFile raf = new RandomAccessFile(dataFile,"r");
            raf.seek(pointer);
            buf = new byte[length];
            raf.read(buf);
            raf.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
        return buf;

    }
}
