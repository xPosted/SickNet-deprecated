package com.jubaka.sors.appserver.service;

import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.appserver.dao.BranchDao;
import com.jubaka.sors.appserver.entities.*;
import com.jubaka.sors.appserver.managed.LoginBean;
import com.jubaka.sors.desktop.http.HTTP;
import com.jubaka.sors.desktop.http.HTTPRequest;
import com.jubaka.sors.desktop.http.HTTPResponse;

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

    public BranchBean castToBean(Branch b) {
        BranchBean bb = new BranchBean();

        bb.setBib(castToInfoBean(b));
        List<SubnetBean> subnets = new ArrayList<>();
        SubnetBean sb = null;
        for (Subnet sEntity : b.getSubntes()) {
            sb = castToBean(sEntity);
            subnets.add(sb);
        }

        bb.setSubnets(subnets);
        return bb;

    }

    public SubnetBean castToBean(Subnet ent) {
        SubnetBean sb = new SubnetBean();
        try {


            sb.setSubnet(InetAddress.getByName (ent.getSubnet()));
            sb.setSubnetMask(ent.getSubnetMask());
            sb.setSesCnt(ent.getSesCnt());
            sb.setAddrCnt(ent.getAddrCnt());
            sb.setActiveAddrCnt(ent.getActiveAddrCnt());
            sb.setActiveSesCnt(ent.getActiveSesCnt());
            sb.setActiveInSesCnt(ent.getActiveInSesCnt());
            sb.setInSesCnt(ent.getInSesCnt());
            sb.setActiveOutSesCnt(ent.getActiveOutSesCnt());
            sb.setOutSesCnt(ent.getOutSesCnt());
            sb.setDataSend(ent.getDataSend());
            sb.setDataReceive(ent.getDataReceive());

            List<IPItemBean> hosts = new ArrayList<>();
            List<IPItemBean> liveHosts = new ArrayList<>();
            for (Host host : ent.getHosts()) {
                IPItemBean ipBean = castToBean(host);
                hosts.add(ipBean);
                if (ipBean.getActiveCount() > 0) {
                    liveHosts.add(ipBean);
                }
            }

            sb.setIps(hosts);
            sb.setLiveIps(liveHosts);

        } catch(UnknownHostException un) {
            un.printStackTrace();

        }
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
        List<HTTP> httpList = combine(ses.getRequestList(),ses.getResponseList());
        sesBean.setHttpBuf(httpList);
        if (sesBean.getHttpBuf().size()>0) sesBean.setHttp(true);
        return sesBean;

    }


    public IPItemBean castToBean(Host host) {
        IPItemBean bean = new IPItemBean();
        bean.setIp(host.getIp());
        bean.setDnsName(host.getDnsName());
        bean.setActiveCount(host.getActiveCount());
        bean.setSavedCount(host.getSavedCount());
        bean.setDataDown(host.getDataDown());
        bean.setDataUp(host.getDataUp());


        bean.setInputActiveCount(host.getInputActiveCount());
        bean.setOutputActiveCount(host.getOutputActiveCount());
        bean.setInputCount(host.getInputCount());
        bean.setOutputCount(host.getOutputCount());


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

    public HTTPRequest castToBean(HttpRequest req) {
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
        return bean;

    }

    public HTTPResponse castToBean(HttpResponse resp) {
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
        return bean;

    }

    public List<HTTP>  combine(List<HttpRequest> reqs, List<HttpResponse> resps) {

        TreeMap<Integer,HTTP> httpMap = new TreeMap<>();

        for (HttpRequest req : reqs) {
            httpMap.put(req.getSequence(),castToBean(req));
        }
        for (HttpResponse resp : resps) {
            httpMap.put(resp.getSequence(),castToBean(resp));
        }

        List<HTTP> httpList = new ArrayList<>(httpMap.values());
        return httpList;

    }




}
