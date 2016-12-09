package com.jubaka.sors.service;

import com.jubaka.sors.beans.branch.IPItemBean;
import com.jubaka.sors.beans.branch.SubnetBean;
import com.jubaka.sors.dao.SubnetDao;
import com.jubaka.sors.entities.Branch;
import com.jubaka.sors.entities.Host;
import com.jubaka.sors.entities.Subnet;
import com.jubaka.sors.sessions.IPaddr;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 03.11.16.
 */

@Named
@ApplicationScoped
public class SubnetService {

    @Inject
    private SubnetDao subnetDao;

    @Inject
    private HostService hostService;

    @Transactional
    public Subnet insert(Subnet br) {
        return subnetDao.insert(br);
    }

    public Subnet selectById(Long id) {
        return subnetDao.selectById(id);
    }

    public Subnet update(Subnet br) {
        return subnetDao.update(br);
    }

    public Subnet prepareSubnetToPrsist(SubnetBean sbean, Branch br, SessionEntitiesCreator sesCreator) {
        Subnet subnet = new Subnet();
        subnet.setSubnet(sbean.getSubnet().getHostAddress());
        subnet.setDataReceive(sbean.getDataReceive());
        subnet.setDataSend(sbean.getDataSend());
        subnet.setActiveAddrCnt(sbean.getActiveAddrCnt());
        subnet.setAddrCnt(sbean.getAddrCnt());
        subnet.setActiveSesCnt(sbean.getActiveSesCnt());
        subnet.setSesCnt(sbean.getSesCnt());
        subnet.setSubnetMask(sbean.getSubnetMask());
        subnet.setBranch(br);
        subnet.setInSesCnt(sbean.getInSesCnt());
        subnet.setActiveInSesCnt(sbean.getActiveInSesCnt());
        subnet.setOutSesCnt(sbean.getOutSesCnt());
        subnet.setActiveOutSesCnt(sbean.getActiveOutSesCnt());

        List<Host> hosts = new ArrayList<>();
        for (IPItemBean ipBean : sbean.getIps()) {
            hosts.add(hostService.prepareHostToPersist(ipBean,subnet, sesCreator));
        }
      /*  for (IPItemBean ipBean : sbean.getLiveIps()) {
            hosts.add(hostService.prepareHostToPersist(ipBean,subnet, sesCreator));
        }
*/
        subnet.setHosts(hosts);
        return  subnet;
    }


}
