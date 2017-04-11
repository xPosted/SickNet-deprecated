package com.jubaka.sors.desktop.sessions;

import java.io.File;
import java.io.Serializable;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JPanel;

import com.jubaka.sors.desktop.protocol.application.HTTP;
import com.jubaka.sors.desktop.protocol.tcp.TCP;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;

//import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;


public class Session extends Observable implements Serializable, CustomObserver {

    private DataSaver dsaver;
    private JPanel view;
    private Long srcDataLen = (long) 0;
    private Long dstDataLen = (long) 0;
    protected Date established = null;
    protected Date closed = null;
    protected Long seq;
    protected Long ack;
    protected Long initSeq;
    protected IPaddr srcIP;
    protected IPaddr dstIP;
    protected Integer srcP;
    protected Integer dstP;
    private boolean closedBySrc = false;
    private boolean closedByDst = false;

    private ArrayList<TCP> packetList = new ArrayList<>();

    private ArrayList<HTTP> httpBuf = new ArrayList<HTTP>();
    private ExecutorService es = Executors.newCachedThreadPool();

    public Session(DataSaver dataSaver) {
        dsaver = dataSaver;
    }

    public JPanel getView() {
        return view;
    }

    public void setView(JPanel view) {
        this.view = view;
    }

    public void setDataSaver(DataSaver dataSaver) {
        dsaver = dataSaver;
    }

    public DataSaver getDataSaver() {
        return dsaver;
    }


    public void setClosed(Date closed) {
        this.closed = closed;
        dsaver.closeConnection();
    }

    public void handlePacket(Tcp tcp, Http http, InetAddress srcAddress, Long time) {
        TCP packet = null;
        if (http != null) {
            HTTP httpObj = HTTP.build(http);
            buildTcpLayer(tcp, httpObj, srcAddress, time);
            putDataDirect(http, httpObj, srcAddress, time);
            packet = httpObj;
            httpBuf.add((HTTP) packet);
        } else {
            TCP tcpObj = new TCP();
            buildTcpLayer(tcp, tcpObj, srcAddress, time);
            putDataDirect(tcp, tcpObj, srcAddress, time);
            packet = tcpObj;
        }
        packetList.add(packet);


    }


    private TCP buildTcpLayer(Tcp tcp, TCP tcpObj, InetAddress srcAddress, Long time) {
        if (srcAddress.equals(this.srcIP.getAddr())) {
            tcpObj.setSrcIP(this.srcIP.getAddr().getHostAddress());
            tcpObj.setDstIP(this.dstIP.getAddr().getHostAddress());
            tcpObj.setSrcPort(this.srcP);
            tcpObj.setDstPort(this.dstP);
            tcpObj.setTimestamp(time);
            tcpObj.setStraight(true);
        } else {
            tcpObj.setSrcIP(this.dstIP.getAddr().getHostAddress());
            tcpObj.setDstIP(this.srcIP.getAddr().getHostAddress());
            tcpObj.setSrcPort(this.dstP);
            tcpObj.setDstPort(this.srcP);
            tcpObj.setTimestamp(time);
            tcpObj.setStraight(false);
        }
        return tcpObj;

    }

    /*	public void addHttp(Http protocol) {
            HTTP myHttpHeader =  HTTP.build(protocol);
            httpBuf.add(myHttpHeader);
        //	System.out.println("httpadded");
        }
    */
    public ArrayList<TCP> getPacketList() {
        return packetList;
    }

    public void setPacketList(ArrayList<TCP> packetList) {
        this.packetList = packetList;
    }

    public List<HTTP> getHTTPList() {
        return httpBuf;
    }
    /*
	public void putData(byte[] buf,InetAddress srcIP,Long time) {
		
		PutDataThread pdt = new PutDataThread(buf, srcIP, time);
		es.execute(pdt);
	}
*/

    public void putDataDirect(Tcp tcp, TCP tcph, InetAddress srcIP, Long time) {

            byte[] buf = tcp.getPayload();
            Long pointer = null;
            File dataFile = null;

            if (tcph.isStraight()) {
                this.srcIP.updateCounters((long) buf.length, false);
                this.dstIP.updateCounters((long) buf.length, true);
                pointer = dsaver.putSrcData(buf, time);
                dataFile = dsaver.getSrcDataFile();


            } else {
                this.srcIP.updateCounters((long) buf.length, true);
                this.dstIP.updateCounters((long) buf.length, false);
                pointer = dsaver.putDstData(buf, time);
                dataFile = dsaver.getDstDataFile();
            }
            if (pointer != null) {

                LocalPayloadAcquirer acquire = new LocalPayloadAcquirer(pointer,buf.length, dataFile);
           //     tcph.setDataPointer(pointer - buf.length);
           //     tcph.setDataFile(dataFile);
                tcph.setPayloadAcquirer(acquire);
                tcph.setPayloadLen(buf.length);
            }

        setChanged();
    }

    public void putDataDirect(Http http, HTTP httph, InetAddress srcIP, Long time) {

            byte[] bufHeader = http.getHeader();
            byte[] bufPayload = http.getPayload();
            Long totalLen = (long)(bufHeader.length + bufPayload.length);
            File dataFile;
            Long pointer = null;
            if (httph.isStraight()) {
                this.srcIP.updateCounters(totalLen, false);
                this.dstIP.updateCounters(totalLen, true);
                pointer = dsaver.putSrcData(bufHeader, time);
                pointer = dsaver.putSrcData(bufPayload, time);
                dataFile = dsaver.getSrcDataFile();

            } else {
                this.srcIP.updateCounters(totalLen, true);
                this.dstIP.updateCounters(totalLen, false);
                pointer = dsaver.putDstData(bufHeader, time);
                pointer = dsaver.putDstData(bufPayload, time);
                dataFile = dsaver.getDstDataFile();
            }
            if (pointer != null) {
                LocalPayloadAcquirer acquire = new LocalPayloadAcquirer(pointer,totalLen.intValue(), dataFile);

              //  httph.setDataPointer(pointer - totalLen);
            //    httph.setHttpHeaderPointer(pointer - totalLen);
             //   httph.setHttpDataPointer(pointer - bufPayload.length);
            //    httph.setDataFile(dataFile);
                httph.setPayloadLen(totalLen.intValue());
                httph.setPayloadOffset(bufHeader.length);
                httph.setPayloadAcquirer(acquire);

            }
        setChanged();


    }

    /*
public void putDataDirect(byte[] buf,InetAddress srcIP,Long time) {


        if (srcIP.equals(this.srcIP.getAddr()))  {
            this.srcIP.updateCounters((long)buf.length, false);
            this.dstIP.updateCounters((long)buf.length, true);
            dsaver.putData(buf, true,time);
        } else {
            this.srcIP.updateCounters((long)buf.length, true);
            this.dstIP.updateCounters((long)buf.length, false);
            dsaver.putData(buf, false,time);

        }
        setChanged();

    }
*/
    public void closeCon(IPaddr closedBy, long ts) {
        if (closedBy == srcIP) closedBySrc = true;
        if (closedBy == dstIP) closedByDst = true;

        if (closedBySrc & closedByDst) {
            srcIP.sessionClose(this);
            dstIP.sessionClose(this);
            setClosed(new Date(ts));

        }
    }

    public static Integer tryToDetectSrc(InetAddress one, InetAddress two) {
        if ((isLocal(one) == true) & (isLocal(two) == false)) return 0;
        if ((isLocal(one) == false) & (isLocal(two) == true)) return 1;
        return -1;

    }

    public static boolean isLocal(InetAddress addr) {
        byte[] bAddr = addr.getAddress();
        ByteBuffer bb = ByteBuffer.wrap(bAddr);
        bb.order(ByteOrder.BIG_ENDIAN);
        Integer addrInt = bb.getInt();
        if ((addrInt <= -1407188993) & (addrInt >= -1408237568)) {
            return true;
        }
        if ((addrInt <= -1062666241) & (addrInt >= -1062731776)) {
            return true;
        }
        if ((addrInt <= 1686110207) & (addrInt >= 1681915904)) {
            return true;
        }
        if ((addrInt <= 184549375) & (addrInt >= 167772160)) {
            return true;
        }

        return false;

    }

    public void changeSrcDst() {
        srcIP.removeSession(this);
        dstIP.removeSession(this);

        IPaddr bufIP;
        Integer bufPort;
        Long bufData;

        bufIP = srcIP;
        srcIP = dstIP;
        dstIP = bufIP;

        bufPort = srcP;
        srcP = dstP;
        dstP = bufPort;

        bufData = srcDataLen;
        srcDataLen = dstDataLen;
        dstDataLen = bufData;

        srcIP.handleOutputSession(this);
        dstIP.handleIncomingSession(this);

    }

    public Subnet getNetInit() {
        return srcIP.getNet();
    }

    public Subnet getNetTarget() {
        return dstIP.getNet();
    }

    public byte[] getSrcData() {
        return dsaver.getSrcData();
    }

    public byte[] getDstData() {
        return dsaver.getDstData();
    }

    public Long getSrcDataLen(long timeFrom, long timeTo) {
        Long resultLen = (long) 0;
        TreeMap<Long, Integer> srcDataTimeBinding = dsaver.getSrcTimeBinding();
        for (long time : srcDataTimeBinding.keySet()) {
            if (time > timeFrom & time < timeTo) {
                resultLen += srcDataTimeBinding.get(time);
            }
        }
        return resultLen;
    }

    public Long getDstDataLen(long timeFrom, long timeTo) {
        Long resultLen = (long) 0;
        TreeMap<Long, Integer> dstDataTimeBinding = dsaver.getDstTimeBinding();
        for (long time : dstDataTimeBinding.keySet()) {
            if (time > timeFrom & time < timeTo) {
                resultLen += dstDataTimeBinding.get(time);
            }
        }
        return resultLen;
    }

    public Long getSrcDataLen() {
        return dsaver.getSrcDataLen();
    }

    public Long getDstDataLen() {
        return dsaver.getDstDataLen();
    }

    /////////////////////////////////


    public IPaddr getSrcIP() {
        return srcIP;
    }

    public void setSrcIP(IPaddr srcIP) {
        this.srcIP = srcIP;
    }

    public IPaddr getDstIP() {
        return dstIP;
    }

    public void setDstIP(IPaddr dstIP) {
        this.dstIP = dstIP;
    }

    public Integer getSrcP() {
        return srcP;
    }

    public void setSrcP(Integer srcP) {
        this.srcP = srcP;
    }

    public Integer getDstP() {
        return dstP;
    }

    public void setDstP(Integer dstP) {
        this.dstP = dstP;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Long getAck() {
        return ack;
    }

    public void setAck(Long ack) {
        this.ack = ack;
    }

    public Date getClosed() {
        return closed;
    }

    public Date getEstablished() {
        return established;
    }

    public void setEstablished(Date established) {
        this.established = established;
    }

    public void setInitSeq(long seq) {
        initSeq = seq;
    }

    public long getInitSeq() {
        return initSeq;
    }

    public void setDstDataLen(Long dstDataLen) {
        this.dstDataLen = dstDataLen;
    }

    public void setSrcDataLen(Long srcDataLen) {
        this.srcDataLen = srcDataLen;
    }

    /*
    class PutDataThread implements Runnable {

        private byte[] buf;
        private InetAddress srcIP;
        private Long time;
        public PutDataThread(byte[] buf,InetAddress srcIP,Long time) {
            this.buf = buf;
            this.srcIP = srcIP;
            this.time = time;
        }
        @Override
        public void run() {
            putDataDirect(buf,srcIP,time);

        }

    }
*/
    @Override
    public void customUpdate() {
        notifyObservers();

    }

}
