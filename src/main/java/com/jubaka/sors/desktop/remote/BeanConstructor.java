package com.jubaka.sors.desktop.remote;

import com.jubaka.sors.appserver.service.SessionService;
import com.jubaka.sors.beans.*;
import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.desktop.factories.ClassFactory;
import com.jubaka.sors.desktop.limfo.LoadInfo;
import com.jubaka.sors.desktop.limfo.LoadLimits;
import com.jubaka.sors.desktop.sessions.*;

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
        if (ses.getHTTPList().size()>0)
            bean.setHttp(true);
        else
            bean.setHttp(false);

        return bean;

    }

    public SessionBean translateSessionToBean(Session ses) {
        SessionBean sb = new SessionBean();
        sb = (SessionBean) prepareSessionLightBean(ses,sb);

        sb.setSrcDataTimeBinding(ses.getDataSaver().getSrcTimeBinding());
        sb.setDstDataTimeBinding(ses.getDataSaver().getDstTimeBinding());
        sb.setHttpBuf(ses.getHTTPList());
        sb.setTcpBuf(ses.getPacketList());

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
        bean.setNewSessionsForCC(translateSessionSetToList(ipaddr.getNewSessionsForCC()));
        bean.setBrId(brId);
        return bean;

    }

    public IPItemBean prepareIpBean(Branch branch, String ip) {


        try {
            SessionsAPI sesApi = branch.getFactory().getSesionInstance(
                    branch.getId());
            IPaddr ipaddr = sesApi.getIpInstance(ip);
            return prepareIpBean(branch,ipaddr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public IPItemBean prepareIpBean(Branch br, IPaddr ip) {
        ClassFactory currentFactory = br.getFactory();
        DataSaverInfo dsi = currentFactory.getDataSaverInfo(
                br.getId());
        IPItemBean ipInfo = new IPItemBean();
        try {
            SessionsAPI cntr = currentFactory.getSesionInstance(
                    br.getId());
            IPaddr ipaddr = ip;
            String ipStr = ip.getAddr().getHostAddress();

            ipInfo = (IPItemBean) prepareIPItemLightBean(br.getId(), ip,ipInfo);

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


    public SubnetLightBean prepareSubnetLightBean(Branch br, String subnetStr, SubnetLightBean subnetBean) {
        SessionsAPI sApi = br.getFactory().getSesionInstance(br.getId());
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

        List<IPItemLightBean> ipBeans = new ArrayList<>();
        List<IPItemLightBean> liveIpBeans = new ArrayList<>();
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

    public FileListBean prepareFileListBean(BranchInfoBean bib, String sorsPath, SessionService service) {
        FileListBean flb = new FileListBean();
        File recoveredFileObj = new File(bib.getRecoveredDataPath());
        flb.setSize(sizeOf(recoveredFileObj));
        flb.setFileCount(getFileCount(recoveredFileObj));
      //  flb.setDate(br.getLastRecovered());
         flb.setDbid(bib.getDbid());
        flb.setFilter(null);
        flb.setMainDir(prepareDirectoryBean( bib,sorsPath,service));
        flb.setBranchName(bib.getBranchName());
        return flb;
    }

    public FileListBean prepareFileListBean(Branch br, String sorsPath) {
            String recoveredPath = br.getFactory().getRecoveredDataPath(
                    br.getId());


            FileListBean flb = new FileListBean();
            File recoveredFileObj = new File(recoveredPath);
            flb.setSize(sizeOf(recoveredFileObj));
            flb.setFileCount(getFileCount(recoveredFileObj));
            flb.setDate(br.getLastRecovered());
            flb.setBrId(br.getId());
            flb.setFilter(null);
            flb.setMainDir(prepareDirectoryBean(sorsPath, br));
            flb.setBranchName(br.getName());
            return flb;

    }



    public FileBean prepareFileBean(File f, BranchInfoBean bib, String sorsPath,SessionService service) {

        FileBean fBean = new FileBean();
        fBean.setName(f.getName());
        fBean.setFullPath(sorsPath + "/" + f.getName());
        fBean.setSize(f.length());
        fBean.setLastModify(f.lastModified());
        String rawDataFileName = getRawDataByRFileName(f.getName(), bib.getRecoveredDataPath());
        if (rawDataFileName != null ){
            SessionBean sb = getSessionByRawDataFile(service,bib,rawDataFileName);
            fBean.setSession(sb);
            if (rawDataFileName.endsWith("src"))
                fBean.setTransmittedBySrc(true);
            if (rawDataFileName.endsWith("dst"))
                fBean.setTransmittedBySrc(false);
        }

        return fBean;
    }

    public FileBean prepareFileBean(File f, Branch br, String sorsPath) {

        FileBean fBean = new FileBean();
        fBean.setName(f.getName());
        fBean.setFullPath(sorsPath + "/" + f.getName());
        fBean.setSize(f.length());
        fBean.setLastModify(f.lastModified());
        String rawDataFileName = getRawDataByRFileName(f.getName(), br.getFactory().getRecoveredDataPath(br.getId()));
        SessionBean sb = translateSessionToBean(getSessionByRawDataFile(br,
                rawDataFileName));
        fBean.setSession(sb);
        if (rawDataFileName.endsWith("src"))
            fBean.setTransmittedBySrc(true);
        if (rawDataFileName.endsWith("dst"))
            fBean.setTransmittedBySrc(false);
        return fBean;
    }


    public DirectoryBean prepareDirectoryBean(BranchInfoBean bib, String sorsPath,SessionService service) {

        String recoveredDataPath = bib.getRecoveredDataPath();
        File f = new File(recoveredDataPath + "/" + sorsPath);
        DirectoryBean dirBean = new DirectoryBean();
        dirBean.setFullPath(sorsPath);
        dirBean.setName(f.getName());

        for (File item : f.listFiles()) {
            if (item.isDirectory()) {
                DirectoryBean subDir = prepareDirectoryBean(bib,sorsPath + "/"
                        + item.getName(),service);
                subDir.setParent(dirBean);
                dirBean.addDir(subDir);
            }
            if (item.isFile()) {
                dirBean.addFile(prepareFileBean(item, bib, sorsPath,service));
            }
        }
        dirBean.setSize(sizeOf(f));
        return dirBean;
    }


    public DirectoryBean prepareDirectoryBean(String sorsPath, Branch br) {
        ClassFactory cl = br.getFactory();
        String recoveredDataPath = cl.getRecoveredDataPath(br.getId());
        File f = new File(recoveredDataPath + "/" + sorsPath);
        DirectoryBean dirBean = new DirectoryBean();
        dirBean.setFullPath(sorsPath);
        dirBean.setName(f.getName());

        for (File item : f.listFiles()) {
            if (item.isDirectory()) {
                DirectoryBean subDir = prepareDirectoryBean(sorsPath + "/"
                        + item.getName(),br);
                subDir.setParent(dirBean);
                dirBean.addDir(subDir);
            }
            if (item.isFile()) {
                dirBean.addFile(prepareFileBean(item, br, sorsPath));
            }
        }
        dirBean.setSize(sizeOf(f));
        return dirBean;
      //  return prepareDirectoryBean(recoveredDataPath,sorsPath);
    }


    public SecPolicyBean prepareSecPolBean(ClassFactory factory) {
        LoadLimits ll = factory.getLimits();
        SecPolicyBean scb = new SecPolicyBean();
        scb.setIpPolicy(ll.getIpPolicy());
        scb.setUserPolicy(ll.getUserPolicy());
        return scb;
    }



    public BranchBean prepareBranchBean(Branch br) {

        SessionsAPI sApi = br.getFactory().getSesionInstance(br.getId());
        BranchBean bb = new BranchBean();
        bb.setBib(prepareBranchInfoBean(br));
        List<SubnetBean> netBeanSet = new ArrayList<>();
        for (Subnet net : sApi.getAllSubnets()) {
            netBeanSet.add(prepareSubnetBean(br, net));
        }
        bb.setSubnets(netBeanSet);
        return bb;
    }

    public BranchLightBean prepareLightBranchBean(Branch br) {

        SessionsAPI sApi = br.getFactory().getSesionInstance(br.getId());
        BranchLightBean bb = new BranchLightBean();
        bb.setBib(prepareBranchInfoBean(br));
        List<SubnetLightBean> netBeanSet = new ArrayList<>();
        for (Subnet net : sApi.getAllSubnets()) {
            netBeanSet.add(prepareSubnetLightBean(net,null));
        }
        bb.setSubnetsLight(netBeanSet);
        return bb;
    }




    public SubnetBean prepareSubnetBean(Branch br, Subnet net) {

        DataSaverInfo dsi = br.getFactory().getDataSaverInfo(br.getId());
        SubnetBean sb = new SubnetBean();
        sb = (SubnetBean) prepareSubnetLightBean(net, sb);

        List<IPItemBean> ipBeans = new ArrayList<>();
        List<IPItemBean> liveIpBeans = new ArrayList<>();
        for (IPaddr ip : net.getIps()) {
            ipBeans.add(prepareIpBean(br, ip));
        }

        for (IPaddr ip : net.getLiveIps()) {
            liveIpBeans.add(prepareIpBean(br, ip));
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

    public SesDataCapBean prepareSesDataCapBean(Branch br, String addr) {
        addr = addr.trim();
        ClassFactory currentFactory = br.getFactory();

       SessionsAPI sesApi = currentFactory.getSesionInstance(br.getId());
        Subnet subnet = sesApi.getNetByName(addr);
        IPaddr ip = sesApi.getIpInstance(addr);

        if (subnet != null) {

            SesDataCapBean bean = new SesDataCapBean();
            SesCaptureInfo sci = currentFactory.getDataSaverInfo(br.getId()).getCatchInfo(subnet);
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
            SesCaptureInfo sci = currentFactory.getDataSaverInfo(br.getId()).getCatchInfo(
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

    public HashSet<SessionBean> translateSessionSet(Collection<Session> sesSet) {
        HashSet<SessionBean> res = new HashSet<SessionBean>();
        if (sesSet == null)
            return res;
        for (Session item : sesSet) {
            SessionBean sb = translateSessionToBean(item);
            res.add(sb);
        }
        return res;
    }

    public List<SessionBean> translateSessionSetToList(Collection<Session> sesSet) {
        List<SessionBean> res = new ArrayList<>();
        if (sesSet == null)
            return res;
        for (Session item : sesSet) {
            SessionBean sb = translateSessionToBean(item);
            res.add(sb);
        }
        return res;
    }

    public SubnetBeanList prepareSubnetBeanList(Branch br) {
        SubnetBeanList bean = new SubnetBeanList();
        Set<Subnet> nets = br.getFactory().getSesionInstance(br.getId())
                .getAllSubnets();
        Set<SubnetBean> beanSet = new HashSet<SubnetBean>();
        for (Subnet net : nets) {
            beanSet.add(prepareSubnetBean(br, net));
        }
        bean.setBrId(br.getId());
        bean.setNets(beanSet);
        return bean;
    }

    public BranchStatBean prepareBrStat(ClassFactory factory, String byUser) {

        BranchStatBean bsb = new BranchStatBean();
        bsb.setByUser(byUser);
        bsb.setBranchProcessed(factory.getBranchRceivedCount());
        bsb.setTotalBranchLen(factory.getTotalReceivedBrLen());
        bsb.setAvailableBranchCount(factory.getAvailableBranchCnt());
        bsb.setAvailableBranchLen(factory.getAvailableBranchLen());
        bsb.setAvailableBranchByUser(factory.getAvailableBranchCntByUser(byUser));
        bsb.setAvailableBranchByUserLen(factory.getAvailableBranchByUserLen(byUser));
        return bsb;

    }

    public BranchInfoBean prepareBranchInfoBean(String user, Branch br) {

        ClassFactory cf = br.getFactory();
        LoadLimits ll = cf.getLimits();
        SecPolicy sp = cf.getLimits().getPolicyByUser(user);
        Branch b = cf.getBranch(br.getId());
        if (b == null)
            return null;
        if (ll.checkBranchAvailable(sp, b) == false) {
            return null;
        }

        return prepareBranchInfoBean(b);
    }

    public BranchInfoBean prepareBranchInfoBean(Branch br) {

        ClassFactory currentFactory = br.getFactory();
        SessionsAPI sApi = currentFactory.getSesionInstance(br.getId());
        LoadInfo lInfo = new LoadInfo(currentFactory);
        LoadLimits ll = currentFactory.getLimits();

        BranchInfoBean bib = new BranchInfoBean();
        bib.setId(br.getId());
        bib.setNodeId(ll.getUnid());
        bib.setFileName(br.getFileName());
        bib.setDesc(br.getDesc());
        bib.setIface(br.getIface());
        bib.setTime(br.getTime());
        bib.setUploadSize(br.getDumpLen());
        bib.setUserName(br.getUserName());
        bib.setWebIP(br.getWebIP());
        bib.setNodeName("capricornus@" + lInfo.getNodeName());
        bib.setBranchName(br.getName());
        bib.setState(br.getState());
        bib.setSubnetCount(sApi.getSubnetCount());
        bib.setHostsCount(sApi.getIPsCount());
        bib.setSessionsCount(sApi.getSessionsCount());
        if (br.getLastRecovered() != null)
            bib.setRecoveredDataPath(currentFactory.getRecoveredDataPath(br.getId()));
        return bib;
    }

    public Set<BranchInfoBean> prepareBranchInfoSet(ClassFactory cf, String userName) {
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

    public SessionDataBean prepareSessionDataBean(Branch br, String net,
                                                   Long sesID) {
        SessionsAPI sesAPI = br.getFactory().getSesionInstance(br.getId());
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


    public InfoBean prepareInfoBean(ClassFactory cf) {
        LoadInfo li = new LoadInfo(cf);
        LoadLimits ll = cf.getLimits();

        InfoBean ib = new InfoBean();
        ib.setNodeName(ll.getNodeName());
        ib.setUnid(ll.getUnid());
        ib.setOwner(WebConnection.getUserName());
        ib.setAvailableMem(li.getAvailableMem());
        ib.setDesc(li.getDesc());
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

    public String getRawDataByRFileName(String name, String recoveredHome) {
        File audit = new File(recoveredHome + "/" + "audit.txt");
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

    public SessionBean getSessionByRawDataFile(SessionService service,BranchInfoBean bib, String fileName) {



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
        List<SessionBean> resultList = service.getByTaskAndSrcHostAndTime(bib.getDbid(),ip,new Date(initTime));
        if (resultList.size()>1) {
            System.err.println("Received session array on single session search...");

        }
        if (resultList.size() == 1) {
            return resultList.get(0);
        }
        return null;



    }

    public Session getSessionByRawDataFile(Branch br, String fileName) {

        SessionsAPI sesApi = br.getFactory().getSesionInstance(br.getId());

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
        IPaddr ipaddr = sesApi.getIpInstance(ip);

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
