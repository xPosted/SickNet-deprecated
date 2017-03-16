package com.jubaka.sors.appserver.entities;

import javax.persistence.*;
import java.io.*;
import java.util.TreeMap;

/**
 * Created by root on 09.11.16.
 */

@Entity
@Table(name = "session_chart")
public class SessionChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] srcDataTime;
    @Lob
    private byte[] dstDataTime;

    public void setSrcDataTime(TreeMap<Long, Integer> srcMap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(srcMap);
            srcDataTime = baos.toByteArray();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public TreeMap<Long, Integer> getSrcDataTime () {
        TreeMap<Long, Integer> srcDataTimeMap = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(srcDataTime);
            ObjectInputStream ois = new ObjectInputStream(bais);
            srcDataTimeMap =(TreeMap<Long, Integer>) ois.readObject();
        } catch (IOException | ClassNotFoundException io) {
            io.printStackTrace();
        }
        return srcDataTimeMap;
    }

    public void setDstDataTime(TreeMap<Long, Integer> srcMap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(srcMap);
            dstDataTime = baos.toByteArray();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public TreeMap<Long, Integer> getDstDataTime () {
        TreeMap<Long, Integer> dstDataTimeMap = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(dstDataTime);
            ObjectInputStream ois = new ObjectInputStream(bais);
            dstDataTimeMap =(TreeMap<Long, Integer>) ois.readObject();
        } catch (IOException | ClassNotFoundException io) {
            io.printStackTrace();
        }
        return dstDataTimeMap;
    }
}
