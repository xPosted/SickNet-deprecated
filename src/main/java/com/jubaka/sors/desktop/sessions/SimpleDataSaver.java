package com.jubaka.sors.desktop.sessions;

import com.jubaka.sors.desktop.factories.ClassFactory;

import java.io.*;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class SimpleDataSaver implements Serializable, DataSaver {

//	private Integer srclimitation = 0;
//	private Integer dstlimitation = 0;

    private Long srcDataLen = (long) 0;

    private Long dstDataLen = (long) 0;
    //	BufferedOutputStream bosSrc =null;
//	BufferedOutputStream bosDst =null;
    private String path = "";
    private Session sInst = null;
    private InetAddress srcIP;
    private InetAddress dstIP;
    private String srcDataFileName;
    private String dstDataFileName;
    private RandomAccessFile srcDataRandomFile;
    private RandomAccessFile dstDataRandomFile;
    private File srcDataFile;
    private File dstDataFile;

    private Branch  br;

    private TreeMap<Long, Integer> srcDataTimeBinding = new TreeMap<Long, Integer>();
    private TreeMap<Long, Integer> dstDataTimeBinding = new TreeMap<Long, Integer>();

    public SimpleDataSaver(Branch br, Session sesInst) {
        this.br = br;
        sInst = sesInst;
        srcIP = sInst.getSrcIP().getAddr();
        dstIP = sInst.getDstIP().getAddr();

        try {

            path = br.getFactory().getRawDataPath(br.getId());
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println("simple data sever created");
        try {
            Date initDate = sInst.getEstablished();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM_HH:mm:ss");

            srcDataFileName = initDate.getTime() + "_" + sdf.format(initDate) + "_" + srcIP.getHostAddress() + "." + sInst.getSrcP() + "-" + dstIP.getHostAddress() + "." + sInst.getDstP() + "_src";
            dstDataFileName = initDate.getTime() + "_" + sdf.format(initDate) + "_" + srcIP.getHostAddress() + "." + sInst.getSrcP() + "-" + dstIP.getHostAddress() + "." + sInst.getDstP() + "_dst";
            srcDataFile = new File(path + "/" + srcDataFileName);
            dstDataFile = new File(path + "/" + dstDataFileName);

            try {

                dstDataFile.createNewFile();
                srcDataFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            srcDataRandomFile = new RandomAccessFile(srcDataFile, "rw");
            dstDataRandomFile = new RandomAccessFile(dstDataFile, "rw");

            //	bosSrc= new BufferedOutputStream(new FileOutputStream(srcData));
            //	bosDst= new BufferedOutputStream(new FileOutputStream(dstData));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    private void bindSrcData(Integer len, long time) {
        Long lastKey;
        try {
            lastKey = srcDataTimeBinding.lastKey();
        } catch (NoSuchElementException e) {
            lastKey = (long) 0;
        }

        if (time - lastKey > 1000) {
            srcDataTimeBinding.put(time, len);
        } else {
            Integer dataCount = srcDataTimeBinding.get(lastKey);
            dataCount += len;
            srcDataTimeBinding.remove(lastKey);
            srcDataTimeBinding.put(lastKey, dataCount);
        }
    }

    private void bindDstData(Integer len, long time) {
        Long lastKey;
        try {
            lastKey = dstDataTimeBinding.lastKey();
        } catch (NoSuchElementException e) {
            lastKey = (long) 0;
        }


        if (time - lastKey > 1000) {
            dstDataTimeBinding.put(time, len);
        } else {
            Integer dataCount = dstDataTimeBinding.get(lastKey);
            dataCount += len;
            dstDataTimeBinding.remove(lastKey);
            dstDataTimeBinding.put(lastKey, dataCount);
        }


    }

    protected void incrementSrcData(Integer len, long time) {
        bindSrcData(len, time);
        srcDataLen = srcDataLen + len;

    }


    public void incrementDstData(Integer len, long time) {
        bindDstData(len, time);
        dstDataLen = dstDataLen + len;
    }

    public void writeSrcData(byte[] buf) {
        try {
            srcDataRandomFile.write(buf);
            //	bosSrc.write(buf);
            //		bosSrc.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void writeDstData(byte[] buf) {
        try {
            dstDataRandomFile.write(buf);
            //	bosDst.write(buf);
            //	bosDst.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setPath(String newPath) {
        path = newPath;

    }
/*
    @Override
    public RandomAccessFile putData(byte[] databuf, boolean bySrc, long time) {
        if (bySrc) {
            incrementSrcData(databuf.length, time);
            if (databuf.length != 0)
                writeSrcData(databuf);
            return srcDataRandomFile;
        } else {
            incrementDstData(databuf.length, time);
            if (databuf.length != 0)
                writeDstData(databuf);
            return dstDataRandomFile;
        }

    }
*/
    public Long putSrcData(byte[] databuf, long time) {
        long pos = -1;
        try {
            incrementSrcData(databuf.length, time);
            if (databuf.length != 0)
                writeSrcData(databuf);
            pos = srcDataRandomFile.getFilePointer();
        } catch (IOException io) {
            io.printStackTrace();
        }

        return pos;
    }

    public Long putDstData(byte[] databuf, long time) {
        long pos = -1;
        try {
            incrementDstData(databuf.length, time);
            if (databuf.length != 0)
                writeDstData(databuf);
            pos = dstDataRandomFile.getFilePointer();
        } catch (IOException io) {
            io.printStackTrace();
        }

        return pos;
    }

    @Override
    public Long getSrcDataLen() {
        // TODO Auto-generated method stub

        return srcDataLen;
    }

    @Override
    public Long getDstDataLen() {
        // TODO Auto-generated method stub
        return dstDataLen;
    }

    @Override
    public byte[] getSrcData() {
        // TODO Auto-generated method stub

        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path + "/" + srcDataFileName));
            byte[] resBytes = new byte[bis.available()];
            bis.read(resBytes);
            return resBytes;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public void closeConnection() {
        try {

            srcDataRandomFile.close();
            dstDataRandomFile.close();
            //	bosSrc.flush();
            //	bosDst.flush();
            //bosDst.close();
            //bosSrc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getDstData() {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path + File.separator + dstDataFileName));
            byte[] resBytes = new byte[bis.available()];
            bis.read(resBytes);
            return resBytes;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public TreeMap<Long, Integer> getSrcTimeBinding() {

        return srcDataTimeBinding;
    }

    @Override
    public TreeMap<Long, Integer> getDstTimeBinding() {
        // TODO Auto-generated method stub
        return dstDataTimeBinding;
    }

    public InputStream getSrcDataAsStream() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path + File.separator + srcDataFileName);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fis;

    }

    public InputStream getDstDataAsStream() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path + File.separator + dstDataFileName);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fis;
    }

    public String getSrcDataFilePath() {
        return path + File.separator + srcDataFileName;
    }

    public String getDstDataFilePath() {
        return path + File.separator + dstDataFileName;
    }

    @Override
    public File getSrcDataFile() {
        return srcDataFile;
    }

    @Override
    public File getDstDataFile() {
        return dstDataFile;
    }


}
