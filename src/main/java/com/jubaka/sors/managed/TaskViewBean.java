package com.jubaka.sors.managed;

import com.jubaka.sors.beans.branch.BranchBean;
import com.jubaka.sors.beans.branch.IPItemBean;
import com.jubaka.sors.beans.branch.SubnetBean;
import com.jubaka.sors.serverSide.ConnectionHandler;
import com.jubaka.sors.serverSide.Node;
import com.jubaka.sors.serverSide.SecurityVisor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by root on 28.08.16.
 */

@Named
@SessionScoped
public class TaskViewBean implements Serializable {

    private BranchBean bb = null;
    private SubnetBean sb = null;
    private IPItemBean ipBean = null;


    public void init() {
        ExternalContext externalContext =  FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> params = externalContext.getRequestParameterMap();
        String nodeIdStr = params.get("nodeId");
        String taskIdStr = params.get("taskId");
        String subnet = params.get("net");
        String ipStr = params.get("host");
        Long nodeId = null;
        Integer taskId = null;
        Node node = null;
        if (nodeIdStr != null) {
            nodeId = Long.parseLong(nodeIdStr);
            node = ConnectionHandler.getInstance().getNode(nodeId);

        }
        if (node!=null & taskIdStr != null) {
            taskId = Integer.valueOf(taskIdStr);
            bb = node.getBranch(taskId);
        }
        if (bb!=null & subnet!=null) {
            try {
                InetAddress subnetAddr = InetAddress.getByName(subnet);
                sb = bb.getSubnetByAddr(subnetAddr);

            } catch (UnknownHostException une) {
                une.printStackTrace();
            }
        }
        if (sb!=null & ipStr!=null) {
            ipBean =  sb.getIpByStr(ipStr);
        }



    }

    public BranchBean getBb() {
        return bb;
    }

    public void setBb(BranchBean bb) {
        this.bb = bb;
    }

    public IPItemBean getIpBean() {
        return ipBean;
    }

    public void setIpBean(IPItemBean ipBean) {
        this.ipBean = ipBean;
    }

    public SubnetBean getSb() {
        return sb;
    }

    public void setSb(SubnetBean sb) {
        this.sb = sb;
    }

    public List<?> setToList(Set<?> set) {
        return new ArrayList<>(set);

    }

}
