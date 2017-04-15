package com.jubaka.sors.appserver.service;

import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.appserver.dao.BranchDao;
import com.jubaka.sors.appserver.entities.*;
import com.jubaka.sors.appserver.managed.LoginBean;
import com.jubaka.sors.desktop.protocol.application.HTTP;
import com.jubaka.sors.desktop.protocol.application.HTTPRequest;
import com.jubaka.sors.desktop.protocol.application.HTTPResponse;
import com.jubaka.sors.desktop.protocol.tcp.TCP;
import com.jubaka.sors.desktop.sessions.InMemoryPayload;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by root on 03.11.16.
 */

@Named
@ApplicationScoped
public class BranchService {

    @Inject
    private BranchDao branchDao;

    @Inject
    private SubnetService subnetService;

    @Inject
    private NodeService nodeService;

    @Inject
    private UserService userService;
    @Inject
    private LoginBean loginBean;

    @Transactional
    public Branch insert(Branch br) {
        return branchDao.insert(br);
    }

    public Branch eagerAllSelectById(Long id) {
        return branchDao.eagerAllSelectById(id);
    }

    public Branch eagerSelectById(Long id) {
        return branchDao.eagerSelectById(id);
    }

    public Branch selectById(Long id) {
        return branchDao.selectById(id);
    }

    public Branch update(Branch br) {
        return branchDao.update(br);
    }

    public void delete(Branch br) {
        branchDao.delete(br);
    }

    public List<Branch> selectByUser(User user) {

        return branchDao.selectByUser(user);
    }
    public List<Branch> selectByCurrentUser() {

        return branchDao.selectByUser(loginBean.getUser());
    }

    public List<Branch> selectByNode(Node node) {

        return branchDao.selectByNode(node);
    }

    public List<Branch> selectByName(String name) {

        return branchDao.selectByName(name);
    }

    public Branch selectByTimeAndNode(Date createionTime, Node node) {
       return branchDao.selectBranchByTimeAndNode(createionTime,node);
    }


    public void deleteIfExist(Date createionTime, Node node) {
        branchDao.deleteIfExist(createionTime,node);
    }


    public Branch persistBranch(BranchBean bb) {

        SessionEntitiesCreator sessionCreator = new SessionEntitiesCreator();
        Timestamp creationTime = new Timestamp(bb.getBib().getTime().getTime());
        Node node = nodeService.getNodeByUnid(bb.getBib().getNodeId());
        User user = userService.getUserByNick(bb.getBib().getUserName());

        deleteIfExist(bb.getBib().getTime(),node);

        Branch b = new Branch();
        b.setBranchName(bb.getBib().getBranchName());
        b.setCreatetime(creationTime);
        b.setDescription(bb.getBib().getDesc());
        b.setIface(bb.getBib().getIface());
        b.setFileName(bb.getBib().getFileName());
        b.setFileSize(bb.getBib().getUploadSize());
        b.setSubnet_count(bb.getBib().getSubnetCount());
        b.setHosts_count(bb.getBib().getHostsCount());
        b.setSessions_count(bb.getBib().getSessionsCount());

        b.setNode(node);
        b.setUser(user);

        Set<Subnet> subnets = new HashSet<>();
        for (SubnetBean sBean : bb.getSubnets()) {
            subnets.add(subnetService.prepareSubnetToPrsist(sBean,b,sessionCreator));
        }
        b.setSubntes(subnets);
        branchDao.insert(b);
        return b;
    }

    public BranchInfoBean castToInfoBean(Branch b) {
        BranchInfoBean bib = new BranchInfoBean();
        bib.setDbid(b.getDbid());
        bib.setId(null);
        bib.setNodeId(b.getNode().getUnid());
        bib.setUserName(b.getUser().getNickName());
        bib.setBranchName(b.getBranchName());
        bib.setDesc(b.getDescription());
        bib.setFileName(b.getFileName());
        bib.setIface(b.getIface());
        bib.setTime(b.getCreatetime());
        bib.setUploadSize(b.getFileSize());
        bib.setState(b.getState());
        bib.setSubnetCount(b.getSubnet_count());
        bib.setHostsCount(b.getHosts_count());
        bib.setSessionsCount(b.getSessions_count());

        return bib;
    }

    public BranchLightBean castToLightBean(BranchLightBean preCreated, Branch b) {
        BranchLightBean lightBean;
        if (preCreated == null) {
            lightBean = new BranchLightBean();
        } else {
            lightBean = preCreated;
        }
        lightBean.setBib(castToInfoBean(b));
        List<SubnetLightBean> subnets = new ArrayList<>();
        SubnetLightBean sb = null;

        for (Subnet sEntity : b.getSubntes()) {

            sb =  castToLightBeanNoHost(null,sEntity);
            subnets.add(sb);
        }

        lightBean.setSubnetsLight(subnets);
        return lightBean;

    }

    public BranchBean castToBean(Branch b) {
        BranchBean bb = new BranchBean();

        bb.setBib(castToInfoBean(b));
        List<SubnetBean> subnets = new ArrayList<>();
        SubnetBean sb = null;

        for (Subnet sEntity : b.getSubntes()) {
            sb =  castToBean(sEntity);
            subnets.add(sb);
        }

        bb.setSubnets(subnets);
        return bb;

    }

    public SubnetLightBean castToLightBean(SubnetLightBean preCreated, Subnet ent) {
        SubnetLightBean lightBean;
        if (preCreated == null) {
            lightBean = new SubnetLightBean();
        } else
            lightBean = preCreated;
        lightBean = castToLightBeanNoHost(lightBean,ent);

        List<IPItemLightBean> hosts = new ArrayList<>();
        List<IPItemLightBean> liveHosts = new ArrayList<>();
        for (Host host : ent.getHosts()) {
            IPItemLightBean ipBean;
            ipBean =  castToLightBean(null,host);
            hosts.add(ipBean);
            if (ipBean.getActiveCount() > 0) {
                liveHosts.add(ipBean);
            }
        }

        lightBean.setLightIps(hosts);
        lightBean.setLightLiveIps(liveHosts);
        return lightBean;

    }

    public SubnetLightBean castToLightBeanNoHost(SubnetLightBean preCreated, Subnet ent) {
        SubnetLightBean lightBean;
        if (preCreated == null) {
            lightBean = new SubnetLightBean();
        } else
            lightBean = preCreated;

        try {
            lightBean.setSubnet(InetAddress.getByName (ent.getSubnet()));
            lightBean.setSubnetMask(ent.getSubnetMask());
            lightBean.setSesCnt(ent.getSesCnt());
            lightBean.setAddrCnt(ent.getAddrCnt());
            lightBean.setActiveAddrCnt(ent.getActiveAddrCnt());
            lightBean.setActiveSesCnt(ent.getActiveSesCnt());
            lightBean.setActiveInSesCnt(ent.getActiveInSesCnt());
            lightBean.setInSesCnt(ent.getInSesCnt());
            lightBean.setActiveOutSesCnt(ent.getActiveOutSesCnt());
            lightBean.setOutSesCnt(ent.getOutSesCnt());
            lightBean.setDataSend(ent.getDataSend());
            lightBean.setDataReceive(ent.getDataReceive());
            lightBean.setDbId(ent.getId());

        } catch(UnknownHostException un) {
            un.printStackTrace();

        }
        return lightBean;

    }

    public SubnetBean castToBean(Subnet ent) {
        SubnetBean sb = new SubnetBean();


            sb = (SubnetBean) castToLightBean(sb,ent);

            List<IPItemBean> hosts = new ArrayList<>();
            List<IPItemBean> liveHosts = new ArrayList<>();
            for (Host host : ent.getHosts()) {
                IPItemBean ipBean;
                ipBean = castToBean(host);
                hosts.add(ipBean);
                if (ipBean.getActiveCount() > 0) {
                    liveHosts.add(ipBean);
                }
            }

            sb.setIps(hosts);
            sb.setLiveIps(liveHosts);

        return sb;


    }

    public SessionBean castToBean(Session ses) {
        SessionBean sesBean = new SessionBean();
        sesBean.setInitSeq(ses.getInitSeq());
        sesBean.setSrcIP(ses.getSrcHost().getIp());
        sesBean.setDstIP(ses.getDstHost().getIp());
        sesBean.setSrcP(ses.getSrcP());
        sesBean.setDstP(ses.getDstP());
        sesBean.setSrcDataLen(ses.getSrcDataLen());
        sesBean.setDstDataLen(ses.getDstDataLen());
        sesBean.setEstablished(ses.getEstablished());
        sesBean.setClosed(ses.getClosed());
        sesBean.setSrcDataTimeBinding(ses.getChartData().getSrcDataTime());
        sesBean.setDstDataTimeBinding(ses.getChartData().getDstDataTime());
     //   List<HTTP> httpList = combine(ses.getRequestList(),ses.getResponseList());
    //    sesBean.setHttpBuf(httpList);
        preparePacketsBeans(sesBean,ses);

        if (sesBean.getHttpBuf().size()>0) sesBean.setHttp(true);
        return sesBean;

    }

    public void preparePacketsBeans(SessionBean sesBean, Session entity) {

        List<TCP> resultTcp = new ArrayList<>();
        List<HTTP> httpList;
        TreeMap<Integer,HTTP> httpMap = new TreeMap<>();

        for (HttpRequest req : entity.getRequestList()) {
            httpMap.put(req.getSequence(),castToBean(sesBean,req));
            entity.getTcps().remove(req.getTcpP());
        }
        for (HttpResponse resp : entity.getResponseList()) {
            httpMap.put(resp.getSequence(),castToBean(sesBean,resp));
            entity.getTcps().remove(resp.getTcpP());
        }

        httpList = new ArrayList<>(httpMap.values());

        for (TcpPacket tcp : entity.getTcps()) {
            resultTcp.add(buildTcpBean(sesBean,null,tcp));
        }
        resultTcp.addAll(httpList);

        sesBean.setTcpBuf(resultTcp);
        sesBean.setHttpBuf(httpList);
    }




    public IPItemLightBean castToLightBean(IPItemLightBean preCreated, Host host) {
        IPItemLightBean lightBean;
        if (preCreated == null) {
            lightBean = new IPItemLightBean();
        } else {
            lightBean = preCreated;
        }

        lightBean.setIp(host.getIp());
        lightBean.setDnsName(host.getDnsName());
        lightBean.setActiveCount(host.getActiveCount());
        lightBean.setSavedCount(host.getSavedCount());
        lightBean.setDataDown(host.getDataDown());
        lightBean.setDataUp(host.getDataUp());


        lightBean.setInputActiveCount(host.getInputActiveCount());
        lightBean.setOutputActiveCount(host.getOutputActiveCount());
        lightBean.setInputCount(host.getInputCount());
        lightBean.setOutputCount(host.getOutputCount());
        lightBean.setDbId(host.getId());
        return lightBean;

    }


    public IPItemBean castToBean(Host host) {
        IPItemBean bean = new IPItemBean();
        bean = (IPItemBean) castToLightBean(bean,host);

        HashSet<SessionBean> activeInSes = new HashSet<>();
        HashSet<SessionBean> activeOutSes = new HashSet<>();
        HashSet<SessionBean> storedInSes = new HashSet<>();
        HashSet<SessionBean> storedOutSes = new HashSet<>();

        for (Session sEnity : host.getSessionsInput()) {
            SessionBean sesBean = castToBean(sEnity);
            if (sesBean.getClosed() == null)
                activeInSes.add(sesBean);
            else
                storedInSes.add(sesBean);

        }

        for (Session sEnity : host.getSessionsOutput()) {
            SessionBean sesBean = castToBean(sEnity);
            if (sesBean.getClosed() == null)
                activeOutSes.add(sesBean);
            else
                storedOutSes.add(sesBean);

        }


        bean.setActiveInSes(activeInSes);
        bean.setStoredInSes(storedInSes);
        bean.setActiveOutSes(activeOutSes);
        bean.setStoredOutSes(storedOutSes);


        return bean;


    }

    public TCP buildTcpBean(SessionBean sessionBean,TCP tcp, TcpPacket entity) {
        TCP tcpBeanRes = tcp;
        if (tcpBeanRes == null)
                tcpBeanRes = new TCP();

        tcpBeanRes.setSrcIP(entity.getSrcHost().getIp());
        tcpBeanRes.setDstIP(entity.getDstHost().getIp());
        tcpBeanRes.setSrcPort(entity.getSrcPort());
        tcpBeanRes.setDstPort(entity.getDstPort());
        tcpBeanRes.setTimestamp(entity.getTimestamp());
        tcpBeanRes.setPayloadAcquirer(new InMemoryPayload(entity.getPayload()));
        tcpBeanRes.setSessionBean(sessionBean);
        return tcpBeanRes;
    }

    public HTTPRequest castToBean(SessionBean sessionBean, HttpRequest req) {
        HTTPRequest bean = new HTTPRequest();
        bean.setAccept(req.getAccept());
        bean.setAccept_Charset(req.getAccept_Charset());
        bean.setAccept_Encoding(req.getAccept_Encoding());
        bean.setAccept_Language(req.getAccept_Language());
        bean.setAccept_Ranges(req.getAccept_Ranges());
        bean.setAuthorization(req.getAuthorization());
        bean.setCache_Control(req.getCache_Control());
        bean.setConnection(req.getConnection());
        bean.setContent_Length(req.getContent_Length());
        bean.setContent_Type(req.getContent_Type());
        bean.setCookie(req.getCookie());
        bean.setDate(req.getDate());
        bean.setHost(req.getHost());
        bean.setIf_Modified_Since(req.getIf_Modified_Since());
        bean.setUser_Agent(req.getUser_Agent());
        bean.setUA_CPU(req.getUA_CPU());
        bean.setProxy_Connection(req.getProxy_Connection());
        bean.setReferer(req.getReferer());
        bean.setRequestMethod(req.getRequestMethod());
        bean.setRequestUrl(req.getRequestUrl());
        bean.setRequestVersion(req.getRequestVersion());
        bean.setPayloadOffset(req.getPayloadOffset());

        TcpPacket tcpLayer = req.getTcpP();
        buildTcpBean(sessionBean, bean,tcpLayer);

        return bean;

    }


    public HTTPResponse castToBean(SessionBean sessionBean, HttpResponse resp) {
        HTTPResponse bean = new HTTPResponse();
        bean.setRequestVersion(resp.getRequestVersion());
        bean.setRequestUrl(resp.getRequestUrl());
        bean.setAccept_Ranges(resp.getAccept_Ranges());
        bean.setAge(resp.getAge());
        bean.setAllow(resp.getAllow());
        bean.setCache_Control(resp.getCache_Control());
        bean.setContent_Disposition(resp.getContent_Disposition());
        bean.setContent_Encoding(resp.getContent_Encoding());
        bean.setContent_Length(resp.getContent_Length());
        bean.setContent_Location(resp.getContent_Location());
        bean.setContent_MD5(resp.getContent_MD5());
        bean.setContent_Range(resp.getContent_Range());
        bean.setContent_Type(resp.getContent_Type());
        bean.setExpires(resp.getExpires());
        bean.setResponseCode(resp.getResponseCode());
        bean.setResponseCodeMsg(resp.getResponseCodeMsg());
        bean.setServer(resp.getServer());
        bean.setSet_Cookie(resp.getSet_Cookie());
        bean.setPayloadOffset(resp.getPayloadOffset());

        TcpPacket tcpLayer = resp.getTcpP();

        buildTcpBean(sessionBean, bean,tcpLayer);
        return bean;

    }

    public List<HTTP>  combine(SessionBean sessionBean, List<HttpRequest> reqs, List<HttpResponse> resps) {

        TreeMap<Integer,HTTP> httpMap = new TreeMap<>();

        for (HttpRequest req : reqs) {
            httpMap.put(req.getSequence(),castToBean(sessionBean,req));
        }
        for (HttpResponse resp : resps) {
            httpMap.put(resp.getSequence(),castToBean(sessionBean,resp));
        }

        List<HTTP> httpList = new ArrayList<>(httpMap.values());
        return httpList;

    }

    public SubnetBean addNet(InetAddress addr, int mask, BranchBean bb) {

        SubnetBean notKnown =  bb.getSubnetByName("0.0.0.0");
        SubnetBean res = new SubnetBean();
        res.setSubnet(addr);
        res.setSubnetMask(mask);

        Set<IPItemBean> ipToRemoveSet = new HashSet<IPItemBean>();
        try {
            for (IPItemBean item : notKnown.getIps()) {
                InetAddress addrItem = InetAddress.getByName(item.getIp());
                if (res.inSubnet(addrItem)) {
                    res.addIPmanualy(item);
                    ipToRemoveSet.add(item);

                }

            }
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }

        for (IPItemBean removeItem : ipToRemoveSet)
            notKnown.deleteIP(removeItem);

        bb.addSubnet(res);
        Node node = nodeService.getNodeByUnid(bb.getBib().getNodeId());
        deleteIfExist(bb.getBib().getTime(),node);
        persistBranch(bb);
        //	System.out.println("Net added");
        return res;
    }




}
