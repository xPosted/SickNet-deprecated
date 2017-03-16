package com.jubaka.sors.appserver.managed;

import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.appserver.entities.Branch;
import com.jubaka.sors.appserver.serverSide.ConnectionHandler;
import com.jubaka.sors.appserver.serverSide.NodeServerEndpoint;
import com.jubaka.sors.appserver.service.BranchService;
import com.jubaka.sors.appserver.service.NodeService;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 22.10.16.
 */

@Named
@SessionScoped
public class TaskOptionsBean implements Serializable {

    @Inject
    private ConnectionHandler ch;
    @Inject
    private BranchService branchService;
    @Inject
    private NodeService nodeService;

    private BranchLightBean blb;
    private BranchInfoBean bib;
    private NodeServerEndpoint nodeServerEndpoint;
    private Integer taskId;


    public void init() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> params =  context.getRequestParameterMap();
        String nodeIdStr = params.get("nodeId");
        String taskIdStr = params.get("taskId");
        String dbidStr = params.get("dbid");

        if (dbidStr != null) {
            Long dbid = Long.parseLong(dbidStr);
            Branch bEnt = branchService.selectById(dbid);

        }

        if (nodeIdStr != null) {
            Long nodeId = Long.parseLong(nodeIdStr);
            nodeServerEndpoint = ch.getNodeServerEndPoint(nodeId);
        }

        if (taskIdStr != null) {
           taskId = Integer.parseInt(taskIdStr);
        }

        if (taskId != null & nodeServerEndpoint != null) {
            blb = nodeServerEndpoint.getBranchLight(taskId);
            bib = blb.getBib();
        }

    }

    public List<IPItemLightBean> getIpsOfSubnet(SubnetLightBean subnet) {
        ArrayList<IPItemLightBean> ips = new ArrayList<>(subnet.getLightIps());
        for (IPItemLightBean ip : ips) System.out.println(ip.getIp());
        return new ArrayList<>(subnet.getLightIps());
    }

    public void saveBranchInDataBase() {
        BranchBean bb = nodeServerEndpoint.getBranch(bib.getId());
        branchService.persistBranch(bb);

    }

    public String getIp(IPItemLightBean ipBean){
        return ipBean.getIp();
    }


    public BranchLightBean getBlb() {
        return blb;
    }

    public void setBlb(BranchLightBean blb) {
        this.blb = blb;
    }

    public BranchInfoBean getBib() {
        return bib;
    }

    public void setBib(BranchInfoBean bib) {
        this.bib = bib;
    }
}
