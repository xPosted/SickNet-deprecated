package com.jubaka.sors.appserver.managed;

import com.jubaka.sors.appserver.beans.InfoBean;
import com.jubaka.sors.appserver.beans.SecPolicy;
import com.jubaka.sors.appserver.beans.SecPolicyBean;
import com.jubaka.sors.appserver.beans.branch.BranchStatBean;
import com.jubaka.sors.appserver.serverSide.ConnectionHandler;
import com.jubaka.sors.appserver.serverSide.NodeServerEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by root on 28.08.16.
 */
@Named
@RequestScoped
public class NodeInfoBean implements Serializable   {

    @Inject
    private ConnectionHandler ch;
    @Inject
    private LoginBean loginBean;
    private NodeServerEndpoint nodeServerEndpoint;
    private InfoBean infoBean;
    private SecPolicyBean securityBean;
    private SecPolicy userSecurityBean;
    private BranchStatBean nodeUserBranchStat;

    @PostConstruct
    public void init() {
        ExternalContext externalContext =  FacesContext.getCurrentInstance().getExternalContext();
        String userNick = loginBean.getUser().getNickName();
        Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
        String nodeIDStr =  parameterMap.get("nodeId");
        Long nodeID = Long.parseLong(nodeIDStr);
        nodeServerEndpoint = ch.getNodeServerEndPoint(nodeID);
        infoBean = nodeServerEndpoint.getInfo();
        securityBean = nodeServerEndpoint.getSecPolicyBean();
        userSecurityBean = securityBean.getUserPolicy().get(userNick);
        nodeUserBranchStat = nodeServerEndpoint.getBranchStat(userNick);
        /// nodeServerEndpoint = magic;
        //  infoBean = magic;
    }

    public InfoBean getInfoBean() {
        return infoBean;
    }

    public void setInfoBean(InfoBean infoBean) {
        this.infoBean = infoBean;
    }

    public SecPolicyBean getSecurityBean() {
        return securityBean;
    }

    public void setSecurityBean(SecPolicyBean securityBean) {
        this.securityBean = securityBean;
    }

    public String getMaxDumpSize() {
        return ConnectionHandler.processSize(userSecurityBean.getDumpSize(),2);
    }

    public SecPolicy getUserSecurityBean() {
        return userSecurityBean;
    }

    public void setUserSecurityBean(SecPolicy userSecurityBean) {
        this.userSecurityBean = userSecurityBean;
    }
    public BranchStatBean getNodeUserBranchStat() {
        return nodeUserBranchStat;
    }

    public void setNodeUserBranchStat(BranchStatBean nodeUserBranchStat) {
        this.nodeUserBranchStat = nodeUserBranchStat;
    }


    public String sizeToStr(double size,Integer afterDot) {
        return ConnectionHandler.processSize(size,afterDot);

    }


}
