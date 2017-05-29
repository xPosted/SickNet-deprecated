package com.jubaka.sors.appserver.service;

import com.jubaka.sors.appserver.entities.*;
import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.desktop.protocol.application.HTTP;
import com.jubaka.sors.desktop.protocol.application.HTTPRequest;
import com.jubaka.sors.desktop.protocol.application.HTTPResponse;
import com.jubaka.sors.desktop.protocol.tcp.TCP;
import com.jubaka.sors.desktop.sessions.InMemoryPayload;
import sun.reflect.generics.tree.Tree;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by root on 19.04.17.
 */
public class BeanEntityConverter {

    public static BranchInfoBean castToInfoBean(Branch b) {
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
        bib.setRecoveredDataPath(b.getRecoveredDataPath());

        return bib;
    }

    public static BranchLightBean castToLightBeanWithHosts(BranchLightBean preCreated, Branch b) {
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

            sb =  castToLightBean(null,sEntity);
            subnets.add(sb);
        }

        lightBean.setSubnetsLight(subnets);
        return lightBean;

    }

    public static BranchLightBean castToLightBean(BranchLightBean preCreated, Branch b) {
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

    public static BranchBean castToBean(Branch b,boolean withData) {
        BranchBean bb = new BranchBean();

        bb.setBib(castToInfoBean(b));
        List<SubnetBean> subnets = new ArrayList<>();
        SubnetBean sb = null;

        for (Subnet sEntity : b.getSubntes()) {
            sb =  castToBean(sEntity,withData);
            subnets.add(sb);
        }

        bb.setSubnets(subnets);
        return bb;

    }

    public static SubnetLightBean castToLightBean(SubnetLightBean preCreated, Subnet ent) {
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

    public static SubnetLightBean castToLightBeanNoHost(SubnetLightBean preCreated, Subnet ent) {
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

    public static SubnetBean castToBean(Subnet ent,boolean withPayload) {
        SubnetBean sb = new SubnetBean();


        sb = (SubnetBean) castToLightBean(sb,ent);

        List<IPItemBean> hosts = new ArrayList<>();
        List<IPItemBean> liveHosts = new ArrayList<>();
        for (Host host : ent.getHosts()) {
            IPItemBean ipBean;
            ipBean = castToBean(host,withPayload);
            hosts.add(ipBean);
            if (ipBean.getActiveCount() > 0) {
                liveHosts.add(ipBean);
            }
        }

        sb.setIps(hosts);
        sb.setLiveIps(liveHosts);

        return sb;


    }

    public static SessionBean castToBean(Session ses,boolean withPayload) {
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
        sesBean.setDbId(ses.getId());
        //   List<HTTP> httpList = combine(ses.getRequestList(),ses.getResponseList());
        //    sesBean.setHttpBuf(httpList);
        preparePacketsBeans(sesBean,ses,withPayload);

        if (sesBean.getHttpBuf().size()>0) sesBean.setHttp(true);
        return sesBean;

    }


    public static void preparePacketsBeans(SessionBean sesBean, Session entity,boolean withPayload) {

       TreeMap<Integer,TCP> packets = getHttpRequestsBeans(entity.getRequestList(),null,withPayload);
                            packets = getHttpResponsesBeans(entity.getResponseList(),packets,withPayload);
       Collection<HTTP> o = (Collection) packets.values();
       sesBean.setHttpBuf(new ArrayList<>(o));

       packets = getTcpBeans(entity.getTcps(),packets,withPayload);
       sesBean.setTcpBuf(new ArrayList<>(packets.values()));
    }

public static TreeMap<Integer,TCP> getHttpRequestsBeans(List<HttpRequest> input, TreeMap<Integer,TCP> tree, boolean withPayload) {
    TreeMap<Integer,TCP> sortMap = tree;
if (sortMap == null) sortMap = new TreeMap<>();
    int seq=-1;
    for (HttpRequest req : input) {
        HTTP http = castToBean(req,withPayload);
        seq = req.getTcpP().getSequence();
        sortMap.put(seq,http);
    }
    return sortMap;

}

    public static TreeMap<Integer,TCP> getHttpResponsesBeans(List<HttpResponse> input, TreeMap<Integer,TCP> tree, boolean withPayload) {
        TreeMap<Integer,TCP> sortMap = tree;
        if (sortMap == null) sortMap = new TreeMap<>();
        int seq=-1;
        for (HttpResponse resp : input) {
            HTTP http = castToBean(resp,withPayload);
            seq = resp.getTcpP().getSequence();
            sortMap.put(seq,http);
        }
        return sortMap;

    }

    public static TreeMap<Integer,TCP> getTcpBeans(List<TcpPacket> input, TreeMap<Integer,TCP> tree, boolean withPayload) {
        TreeMap<Integer,TCP> sortMap = tree;
        if (sortMap == null) sortMap = new TreeMap<>();
        int seq=-1;
        TCP tcpBean = null;
        for (TcpPacket tcp : input) {
            seq = tcp.getSequence();
            if (sortMap.containsKey(seq) == false) {
                if (withPayload) tcpBean = buildTcpBeanWithPayload(null,tcp);
                else tcpBean = buildTcpBeanNoPayload(null,tcp);
                sortMap.put(seq,tcpBean);
            }
        }
        return sortMap;
    }

    public static IPItemLightBean castToLightBean(IPItemLightBean preCreated, Host host) {
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


    public static IPItemBean castToBean(Host host,boolean withPayload) {
        IPItemBean bean = new IPItemBean();
        bean = (IPItemBean) castToLightBean(bean,host);

        HashSet<SessionBean> activeInSes = new HashSet<>();
        HashSet<SessionBean> activeOutSes = new HashSet<>();
        HashSet<SessionBean> storedInSes = new HashSet<>();
        HashSet<SessionBean> storedOutSes = new HashSet<>();

        for (Session sEnity : host.getSessionsInput()) {
            SessionBean sesBean = castToBean(sEnity,withPayload);
            if (sesBean.getClosed() == null)
                activeInSes.add(sesBean);
            else
                storedInSes.add(sesBean);

        }

        for (Session sEnity : host.getSessionsOutput()) {
            SessionBean sesBean = castToBean(sEnity,withPayload);
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

    public static TCP buildTcpBeanNoPayload(TCP tcp, TcpPacket entity) {
        TCP tcpBeanRes = tcp;
        if (tcpBeanRes == null)
            tcpBeanRes = new TCP();

        tcpBeanRes.setSrcIP(entity.getSrcHost().getIp());
        tcpBeanRes.setDstIP(entity.getDstHost().getIp());
        tcpBeanRes.setSrcPort(entity.getSrcPort());
        tcpBeanRes.setDstPort(entity.getDstPort());
        tcpBeanRes.setTimestamp(entity.getTimestamp());
        tcpBeanRes.setPayloadAcquirer(null);
        tcpBeanRes.setSessionId(entity.getSession().getId());
        tcpBeanRes.setSequence(entity.getSequence());
        return tcpBeanRes;
    }

    public static TCP buildTcpBeanWithPayload(TCP tcp, TcpPacket entity) {
        TCP result = buildTcpBeanNoPayload(tcp,entity);
        result.setPayloadAcquirer(new InMemoryPayload(entity.getPayload().getPayload()));
        return result;
    }

    public static HTTPRequest castToBean(HttpRequest req, boolean withPaload) {
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
        if (withPaload)
            buildTcpBeanWithPayload(bean,tcpLayer);
        else buildTcpBeanNoPayload(bean,tcpLayer);
        return bean;

    }


    public static HTTPResponse castToBean(HttpResponse resp, boolean withPaload) {
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

        if (withPaload)
            buildTcpBeanWithPayload(bean,tcpLayer);
        else buildTcpBeanNoPayload(bean,tcpLayer);
        return bean;

    }
}
