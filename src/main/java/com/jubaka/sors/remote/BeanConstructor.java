package com.jubaka.sors.remote;

import com.jubaka.sors.beans.*;
import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.limfo.LoadInfo;
import com.jubaka.sors.limfo.LoadLimits;
import com.jubaka.sors.sessions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by root on 30.08.16.
 */
public class BeanConstructor {

    public SessionLightBean prepareSessionLightBean(Session ses, SessionBean item) {
        SessionLightBean bean;
        if (item == null) bean = new SessionLightBean(); else
            bean = item;
        bean.setSrcDataLen(ses.getSrcDataLen());
        bean.setDstDataLen(ses.getDstDataLen());
        bean.setInitSeq(ses.getInitSeq());
        bean.setAck(ses.getAck());
        bean.setClosed(ses.getClosed());
        bean.setDstIP(ses.getDstIP().getAddr().getHostAddress());
        bean.setDstP(ses.getDstP());
        bean.setEstablished(ses.getEstablished());
        bean.setSeq(ses.getSeq());
        bean.setSrcIP(ses.getSrcIP().getAddr().getHostAddress());
        bean.setSrcP(ses.getSrcP());

        return bean;

    }

    public SessionBean translateSessionToBean(Session ses) {
        SessionBean sb = new SessionBean();
        sb = (SessionBean) prepareSessionLightBean(ses,sb);

        sb.setSrcDataTimeBinding(ses.getDataSaver().getSrcTimeBinding());
        sb.setDstDataTimeBinding(ses.getDataSaver().getDstTimeBinding());
        sb.setHttpBuf(ses.getHTTPList());
        return sb;
    }


    public IPItemLightBean prepareIPItemLightBean(Integer brId, IPaddr ipaddr, IPItemLightBean item) {
        IPItemLightBean bean;
        if (item == null) bean = new IPItemLightBean(); else
            bean = item;
        String ipStr = ipaddr.getAddr().getHostAddress();
        bean.setIp(ipStr);
        bean.setDnsName(ipaddr.getDnsName());
        bean.setActivated(ipaddr.getActivated());
        bean.setActiveCount(ipaddr.getActiveSesCount());
        bean.setDataDown(ipaddr.getDataDown());
        bean.setDataUp(ipaddr.getDataUp());
        bean.setInputCount(ipaddr.getInSessionCount());
        bean.setOutputCount(ipaddr.getOutSessionCount());
        bean.setInputActiveCount(ipaddr.getInputActiveCount());
        bean.setOutputActiveCount(ipaddr.getOutputActiveCount());
        bean.setSavedCount(ipaddr.getSavedSesCount());
        bean.setBrId(brId);
        return bean;

    }

    public IPItemBean prepareIpBean(Integer branch_id, String ip) {


        try {
            SessionsAPI cntr = ClassFactory.getInstance().getSesionInstance(
                    branch_id);
            IPaddr ipaddr = IPaddr.getInstance(branch_id, ip);
            return prepareIpBean(branch_id,ipaddr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public IPItemBean prepareIpBean(Integer branch_id, IPaddr ip) {
        DataSaverInfo dsi = ClassFactory.getInstance().getDataSaverInfo(
                branch_id);
        IPItemBean ipInfo = new IPItemBean();
        try {
            SessionsAPI cntr = ClassFactory.getInstance().getSesionInstance(
                    branch_id);
            IPaddr ipaddr = ip;
            String ipStr = ip.getAddr().getHostAddress();

            ipInfo = (IPItemBean) prepareIPItemLightBean(branch_id, ip,ipInfo);

            ipInfo.setActiveOutSes(translateSessionSet(cntr.getOutputActiveSes(ipStr)));
            ipInfo.setActiveInSes(translateSessionSet(cntr.getInputActiveSes(ipStr)));
            ipInfo.setStoredOutSes(translateSessionSet(cntr.getOutputStoredSes(ipStr)));
            ipInfo.setStoredInSes(translateSessionSet(cntr.getInputStoredSes(ipStr)));
            SesCaptureInfo sci = dsi.getCatchInfo(ip.getAddr());
            SesDataCapBean sdcb = new SesDataCapBean();

            if (sci == null) {
                ipInfo.setDataCapInfo(null);
            } else {
                sdcb.setObject(ip.getDnsName());
                sdcb.setInPort(sci.getInPorts());
                sdcb.setOutPort(sci.getOutPorts());
                ipInfo.setDataCapInfo(sdcb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ipInfo;
    }


    public SubnetLightBean prepareSubnetLightBean(Integer brId, String subnetStr, SubnetLightBean subnetBean) {
        SessionsAPI sApi = ClassFactory.getInstance().getSesionInstance(brId);
        Subnet subnet =  sApi.getNetByName(subnetStr);
        return prepareSubnetLightBean(subnet,subnetBean);
    }

    public SubnetLightBean prepareSubnetLightBean(Subnet net, SubnetLightBean subnetBean) {

        SubnetLightBean sb;
        if (subnetBean == null) {
            sb = new SubnetLightBean();
        } else
            sb = subnetBean;
        sb.setBrId(net.getId());
        sb.setSubnet(net.getSubnet());
        sb.setSubnetMask(net.getSubnetMask());
        sb.setDataReceive(net.getInputData());
        sb.setDataSend(net.getOutptData());
        sb.setActiveAddrCnt(net.getLiveIps().size());
        sb.setAddrCnt(net.getIps().size());
        sb.setActiveSesCnt(net.getLiveSesCount());
        sb.setSesCnt(net.getSesCount());
        sb.setActiveInSesCnt(net.getInputActiveSesCount());
        sb.setActiveOutSesCnt(net.getOutputActiveSesCount());
        sb.setInSesCnt(net.getInputSesCount());
        sb.setOutSesCnt(net.getOutputSesCount());

        HashSet<IPItemLightBean> ipBeans = new HashSet<IPItemLightBean>();
        HashSet<IPItemLightBean> liveIpBeans = new HashSet<IPItemLightBean>();
        for (IPaddr ip : net.getIps()) {
            ipBeans.add(prepareIPItemLightBean(net.getId(),ip,null));
        }

        for (IPaddr ip : net.getLiveIps()) {
            liveIpBeans.add(prepareIPItemLightBean(net.getId(),ip,null));
        }

        sb.setLightIps(ipBeans);
        sb.setLightLiveIps(liveIpBeans);

        return sb;
    }

    public FileListBean prepareFileListBean(Integer brId, String sorsPath) {
            String recoveredPath = ClassFactory.getInstance().getRecoveredDataPath(
                    brId);

            Branch br = ClassFactory.getInstance().getBranch(brId);
            FileListBean flb = new FileListBean();
            File recoveredFileObj = new File(recoveredPath);
            flb.setSize(sizeOf(recoveredFileObj));
            flb.setFileCount(getFileCount(recoveredFileObj));
            flb.setDate(br.getLastRecovered());
            flb.setBrId(brId);
            flb.setFilter(null);
            flb.setMainDir(prepareDirectoryBean(sorsPath, brId));
            flb.setBranchName(br.getName());
            return flb;

    }
    public FileBean prepareFileBean(File f, Integer brId, String sorsPath) {
        String branchPath = ClassFactory.getInstance().getBranchPath(brId);
        FileBean fBean = new FileBean();
        fBean.setName(f.getName());
        fBean.setFullPath(sorsPath + "/" + f.getName());
        fBean.setSize(f.length());
        fBean.setLastModify(f.lastModified());
        String rawDataFileName = getRawDataByRFileName(f.getName(), branchPath);
        SessionBean sb = translateSessionToBean(getSessionByRawDataFile(
                rawDataFileName, brId));
        fBean.setSession(sb);
        if (rawDataFileName.endsWith("src"))
            fBean.setTransmittedBySrc(true);
        if (rawDataFileName.endsWith("dst"))
            fBean.setTransmittedBySrc(false);
        return fBean;
    }

    public DirectoryBean prepareDirectoryBean(String sorsPath, Integer brId) {
        ClassFactory cl = ClassFactory.getInstance();
        String recoveredDataPath = cl.getRecoveredDataPath(brId);
        File f = new File(recoveredDataPath + "/" + sorsPath);
        DirectoryBean dirBean = new DirectoryBean();
        dirBean.setFullPath(sorsPath);
        dirBean.setName(f.getName());

        for (File item : f.listFiles()) {
            if (item.isDirectory()) {
                DirectoryBean subDir = prepareDirectoryBean(sorsPath + "/"
                        + item.getName(), brId);
                subDir.setParent(dirBean);
                dirBean.addDir(subDir);
            }
            if (item.isFile()) {
                dirBean.addFile(prepareFileBean(item, brId, sorsPath));
            }
        }
        dirBean.setSize(sizeOf(f));
        return dirBean;

    }


    public SecPolicyBean prepareSecPolBean() {
        ClassFactory cf = ClassFactory.getInstance();
        LoadLimits ll = cf.getLimits();
        SecPolicyBean scb = new SecPolicyBean();
        scb.setIpPolicy(ll.getIpPolicy());
        scb.setUserPolicy(ll.getUserPolicy());
        return scb;
    }



    public BranchBean prepareBranchBean(Integer id) {
        Branch br = ClassFactory.getInstance().getBranch(id);
        SessionsAPI sApi = ClassFactory.getInstance().getSesionInstance(id);
        BranchBean bb = new BranchBean();
        bb.setBib(prepareBranchInfoBean(br));
        List<SubnetBean> netBeanSet = new ArrayList<>();
        for (Subnet net : sApi.getAllSubnets()) {
            netBeanSet.add(prepareSubnetBean(id, net));
        }
        bb.setSubnets(netBeanSet);
        return bb;
    }

    public BranchLightBean prepareLightBranchBean(Integer id) {
        Branch br = ClassFactory.getInstance().getBranch(id);
        SessionsAPI sApi = ClassFactory.getInstance().getSesionInstance(id);
        BranchLightBean bb = new BranchLightBean();
        bb.setBib(prepareBranchInfoBean(br));
        List<SubnetLightBean> netBeanSet = new ArrayList<>();
        for (Subnet net : sApi.getAllSubnets()) {
            netBeanSet.add(prepareSubnetLightBean(net,null));
        }
        bb.setSubnetsLight(netBeanSet);
        return bb;
    }




    public SubnetBean prepareSubnetBean(Integer id, Subnet net) {

        DataSaverInfo dsi = ClassFactory.getInstance().getDataSaverInfo(id);
        SubnetBean sb = new SubnetBean();
        sb = (SubnetBean) prepareSubnetLightBean(net, sb);

        List<IPItemBean> ipBeans = new ArrayList<>();
        List<IPItemBean> liveIpBeans = new ArrayList<>();
        for (IPaddr ip : net.getIps()) {
            ipBeans.add(prepareIpBean(id, ip));
        }

        for (IPaddr ip : net.getLiveIps()) {
            liveIpBeans.add(prepareIpBean(id, ip));
        }

        sb.setIps(ipBeans);
        sb.setLiveIps(liveIpBeans);
        SesDataCapBean sdcb = prepareSesDataCapBean(dsi.getCatchInfo(net));
        if (sdcb == null) {
            sb.setSesDataCapBean(null);
        } else {
            sdcb.setObject(net.getSubnet().getHostName());
            sb.setSesDataCapBean(sdcb);
        }

        return sb;
    }

    public SesDataCapBean prepareSesDataCapBean(SesCaptureInfo sci) {
        if (sci == null)
            return null;
        SesDataCapBean bean = new SesDataCapBean();
        bean.setInPort(sci.getInPorts());
        bean.setOutPort(sci.getOutPorts());
        return bean;
    }

    public SesDataCapBean prepareSesDataCapBean(String addr, Integer id) {
        addr = addr.trim();
        ClassFactory cl = ClassFactory.getInstance();
        SessionsAPI sesAPI = cl.getSesionInstance(id);
        Subnet subnet = sesAPI.getNetByName(addr);
        IPaddr ip = IPaddr.getInstance(id, addr);

        if (subnet != null) {

            SesDataCapBean bean = new SesDataCapBean();
            SesCaptureInfo sci = cl.getDataSaverInfo(id).getCatchInfo(subnet);
            if (sci == null) {
                bean.setObject("capture_not_set");
                return bean;
            }
            bean.setObject(addr);
            bean.setInPort(sci.getInPorts());
            bean.setOutPort(sci.getOutPorts());
            return bean;

        }

        if (ip != null) {
            SesDataCapBean bean = new SesDataCapBean();
            SesCaptureInfo sci = cl.getDataSaverInfo(id).getCatchInfo(
                    ip.getAddr());
            if (sci == null) {
                bean.setObject("capture_not_set");
                return bean;
            }

            bean.setObject(addr);
            bean.setInPort(sci.getInPorts());
            bean.setOutPort(sci.getOutPorts());
            return bean;
        }
        return (new SesDataCapBean());

    }

    public HashSet<SessionBean> translateSessionSet(HashSet<Session> sesSet) {
        HashSet<SessionBean> res = new HashSet<SessionBean>();
        if (sesSet == null)
            return res;
        for (Session item : sesSet) {
            SessionBean sb = translateSessionToBean(item);
            res.add(sb);
        }
        return res;
    }


    public SubnetBeanList prepareSubnetBeanList(Integer brId) {
        SubnetBeanList bean = new SubnetBeanList();
        Set<Subnet> nets = ClassFactory.getInstance().getSesionInstance(brId)
                .getAllSubnets();
        Set<SubnetBean> beanSet = new HashSet<SubnetBean>();
        for (Subnet net : nets) {
            beanSet.add(prepareSubnetBean(brId, net));
        }
        bean.setBrId(brId);
        bean.setNets(beanSet);
        return bean;
    }

    public BranchStatBean prepareBrStat(String byUser) {
        ClassFactory cf = ClassFactory.getInstance();
        BranchStatBean bsb = new BranchStatBean();
        bsb.setByUser(byUser);
        bsb.setBranchProcessed(cf.getBranchRceivedCount());
        bsb.setTotalBranchLen(cf.getTotalReceivedBrLen());
        bsb.setAvailableBranchCount(cf.getAvailableBranchCnt());
        bsb.setAvailableBranchLen(cf.getAvailableBranchLen());
        bsb.setAvailableBranchByUser(cf.getAvailableBranchCntByUser(byUser));
        bsb.setAvailableBranchByUserLen(cf.getAvailableBranchByUserLen(byUser));
        return bsb;

    }

    public BranchInfoBean prepareBranchInfoBean(String user, Integer brId) {

        ClassFactory cf = ClassFactory.getInstance();
        LoadLimits ll = cf.getLimits();
        SecPolicy sp = cf.getLimits().getPolicyByUser(user);
        Branch b = ClassFactory.getInstance().getBranch(brId);
        if (b == null)
            return null;
        if (ll.checkBranchAvailable(sp, b) == false) {
            return null;
        }

        return prepareBranchInfoBean(b);
    }

    public BranchInfoBean prepareBranchInfoBean(Branch br) {

        SessionsAPI sApi = ClassFactory.getInstance().getSesionInstance(br.getId());

        BranchInfoBean bib = new BranchInfoBean();
        bib.setId(br.getId());
        bib.setFileName(br.getFileName());
        bib.setDesc(br.getDesc());
        bib.setIface(br.getIface());
        bib.setTime(br.getTime());
        bib.setUploadSize(br.getDumpLen());
        bib.setUserName(br.getUserName());
        bib.setWebIP(br.getWebIP());
        bib.setNodeName("capricornus@" + LoadInfo.getNodeName());
        bib.setBranchName(br.getName());
        bib.setState(br.getState());
        bib.setSubnetCount(sApi.getSubnetCount());
        bib.setHostsCount(sApi.getIPsCount());
        bib.setSessionsCount(sApi.getSessionsCount());
        return bib;
    }

    public Set<BranchInfoBean> prepareBranchInfoSet(String userName) {
        ClassFactory cf = ClassFactory.getInstance();
        LoadLimits ll = cf.getLimits();
        SecPolicy sp = cf.getLimits().getPolicyByUser(userName);
        HashSet<BranchInfoBean> res = new HashSet<BranchInfoBean>();
        for (Branch b : cf.getBranches()) {
            if (ll.checkBranchAvailable(sp, b)) {

                res.add(prepareBranchInfoBean(b));
            }
        }
        return res;

    }

    public SessionDataBean prepareSessionDataBean(Integer brID, String net,
                                                   Long sesID) {
        SessionsAPI sesAPI = ClassFactory.getInstance().getSesionInstance(brID);
        if (sesAPI == null)
            return null;
        Subnet subnet = sesAPI.getNetByName(net);
        if (subnet == null)
            return null;
        Session ses = subnet.getSessionById(sesID);
        if (ses == null)
            return null;
        SessionDataBean sdb = new SessionDataBean();
        sdb.setTm(sesID);
        sdb.setSrcData(ses.getSrcData());
        sdb.setDstData(ses.getDstData());
        return sdb;
    }


    public InfoBean prepareInfoBean() {
        LoadInfo li = new LoadInfo();
        ClassFactory cf = ClassFactory.getInstance();
        LoadLimits ll = cf.getLimits();
        InfoBean ib = new InfoBean();
        ib.setNodeName(LoadInfo.getNodeName());
        ib.setOwner(WebConnection.getUserName());
        ib.setAvailableMem(li.getAvailableMem());
        ib.setDesc(LoadInfo.getDesc());
        ib.setHomeMax(ll.getHomeRemoteMaxSize());
        ib.setHomeUsed(li.getHomeUsedRemote());
        ib.setUsedMem(li.getUsedMem());
        ib.setProcCount(li.getProcCount());
        ib.setOsArch(li.getOsArch());
        ib.setMaxMem(li.getMaxMem());
        ib.setCurrentDumpCount(cf.getAvailableBranchCnt());
        ib.setReceivedDumpCount(cf.getAllReceivedDumpCount());
        ib.setCurrentDumpSize(cf.getAvailableBranchLen());
        ib.setReceivedDumpSize(cf.getTotalReceivedBrLen());
        ib.setStatus(ll.getStatus());
        return ib;
    }

    public String getRawDataByRFileName(String name, String branchPath) {
        File audit = new File(branchPath + "/" + "audit.txt");
        Scanner scan = null;
        try {
            scan = new Scanner(audit);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        String rawDataFileName;
        while (scan.hasNext()) {
            String line = scan.nextLine();
            if (line.startsWith("File: ")) {
                rawDataFileName = line;
                while (line.startsWith("Finish: ") == false) {
                    line = scan.nextLine();
                    if (line.contains(name)) {
                        Integer lastIndex = rawDataFileName
                                .lastIndexOf("File: ");
                        rawDataFileName = rawDataFileName.substring(
                                lastIndex + 1, rawDataFileName.length());
                        return rawDataFileName;
                    }
                }
            }
        }
        return null;
    }



    public Session getSessionByRawDataFile(String fileName, Integer brId) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM_HH:mm:ss");

        String[] splitedFileName = fileName.split("/");
        String directFileName = splitedFileName[splitedFileName.length - 1];
        String[] splitedDirectFileName = directFileName.split("_");
        String initDateStr = splitedDirectFileName[1] + "_"
                + splitedDirectFileName[2];
        long initTime = Long.parseLong(splitedDirectFileName[0]);
        String IpPort = splitedDirectFileName[3];
        String srcIpPort = IpPort.split("-")[0];
        Integer lastDot = srcIpPort.lastIndexOf(".");
        String ip = srcIpPort.substring(0, lastDot);
        IPaddr ipaddr = IPaddr.getInstance(brId, ip);

        if (ipaddr != null) {

            Subnet net = ipaddr.getNet();
            Session ses = net.getSessionById(initTime);
            if (ses == null) {
                System.out.println(initTime);
            }
            return ses;
        }
        return null;
    }


    private Integer getFileCount(File f) {
        Integer count = 0;
        if (f.isFile()) {
            return 1;
        }
        if (f.isDirectory()) {
            for (File item : f.listFiles())
                if (item.isFile())
                    count++;
                else
                    count = count + getFileCount(item);
        }
        return count;
    }

    public static Long sizeOf(File f) {
        Long size = (long) 0;
        if (f.isFile()) {
            return f.length();
        }
        if (f.isDirectory()) {
            for (File item : f.listFiles())
                size = size + sizeOf(item);
        }
        return size;
    }
}
