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

    @Inject HostService hostService;

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

    public Branch eagerSelectByIdWithWithSubsHostsSess(Long id) {
        return branchDao.eagerSelectByIdWithWithSubsHostsSess(id);
    }

    public Branch selectByIdWithNets(Long id) {
        return branchDao.selectByIdWithNets(id);
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

    public Branch eagerSelectByIdWithWithSubsHosts(Long id) {
        return branchDao.eagerSelectByIdWithWithSubsHosts(id);
    }

    public Branch selectByTimeAndNode(Date createionTime, Node node) {
       return branchDao.selectBranchByTimeAndNode(createionTime,node);
    }


    public void deleteIfExist(Date createionTime, Node node) {
        branchDao.deleteIfExist(createionTime,node);
    }


    public Branch persistBranch(BranchBean bb) {

        Branch b = prepareEntity(bb);
        branchDao.insert(b);
        return b;
    }

    public Branch prepareEntity(BranchBean bb) {
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
        return b;
    }

    public Branch prepareEntity(BranchLightBean bb) {
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
        for (SubnetLightBean sBean : bb.getSubnetsLight()) {
            subnets.add(subnetService.prepareSubnetToPrsist(sBean,b,sessionCreator));
        }
        b.setSubntes(subnets);
        return b;
    }


/*
    public List<HTTP>  combine(SessionBean sessionBean, List<HttpRequest> reqs, List<HttpResponse> resps) {

        TreeMap<Integer,HTTP> httpMap = new TreeMap<>();

        for (HttpRequest req : reqs) {
            httpMap.put(req.getTcpP().getSequence(),castToBean(sessionBean,req));
        }
        for (HttpResponse resp : resps) {
            httpMap.put(resp.getTcpP().getSequence(),castToBean(sessionBean,resp));
        }

        List<HTTP> httpList = new ArrayList<>(httpMap.values());
        return httpList;

    }
*/
    public SubnetLightBean addNet(InetAddress addr, int mask,Long id) {
        Branch be = eagerSelectByIdWithWithSubsHosts(id);
        BranchLightBean blb = BeanEntityConverter.castToLightBeanWithHosts(null, be);


        SubnetLightBean notKnown =  blb.getSubnetByName("0.0.0.0");
        SubnetLightBean res = new SubnetLightBean();
        res.setSubnet(addr);
        res.setSubnetMask(mask);

        Set<IPItemLightBean> ipMoveSet = new HashSet<>();
        List<Long> ipIdsMoveSet = new ArrayList<>();

        try {
            for (IPItemLightBean item : notKnown.getAllIpList()) {
                InetAddress addrItem = InetAddress.getByName(item.getIp());
                if (res.inSubnet(addrItem)) {
                    res.addIPmanualy(item);
                    ipMoveSet.add(item);
                    ipIdsMoveSet.add(item.getDbId());

                }

            }
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }

        for (IPItemLightBean removeItem : ipMoveSet)
            notKnown.deleteIP(removeItem);



         //   update netKnown !!!
        be.setSubnet_count(be.getSubnet_count()+1);
        updateStatData(be);
        Subnet notKnownEntity = subnetService.prepareSubnetEmptyHostsToPrsist(notKnown,be);
        subnetService.updateStatData(notKnownEntity);
        Subnet newSubnetEntity = subnetService.prepareSubnetEmptyHostsToPrsist(res,be);
        newSubnetEntity = subnetService.insert(newSubnetEntity);
        hostService.moveToSubnet(ipIdsMoveSet,newSubnetEntity);

        //blb.addSubnet(res);
       // Node node = nodeService.getNodeByUnid(blb.getBib().getNodeId());
        //Branch b = prepareEntity(blb);
        //update(b);
       // deleteIfExist(blb.getBib().getTime(),node);
       // persistBranch(blb);
        //	System.out.println("Net added");
        return res;
    }

    public Branch selectByIdFullBranchNoPayload(Long id) {
       return branchDao.selectByIdFullBranchNoPayload(id);
    }

    public Branch selectByIdFullBranchNoPayloadv2(Long id) {
        return branchDao.selectByIdFullBranchNoPayloadv2(id);
    }

    public void updateStatData(Branch b) {
        branchDao.updateStatData(b);
    }


}
