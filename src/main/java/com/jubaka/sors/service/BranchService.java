package com.jubaka.sors.service;

import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.dao.BranchDao;
import com.jubaka.sors.entities.*;
import com.jubaka.sors.managed.LoginBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;
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




}
