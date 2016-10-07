package com.jubaka.sors.managed;

import com.jubaka.sors.beans.Bean;
import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.serverSide.ConnectionHandler;
import com.jubaka.sors.serverSide.Node;
import com.jubaka.sors.serverSide.SecurityVisor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.websocket.server.ServerEndpoint;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by root on 28.08.16.
 */

@Named
@SessionScoped

public class TaskViewBean implements Serializable, Observer {
    @Inject
    private LoginBean loginBean;


    private BranchLightBean blb = null;
    private SubnetLightBean sbl = null;
    private IPItemBean ipBean = null;

    private boolean sessionInFilter = false;
    private boolean sessionOutFilter = true;
    private boolean sessionActiveFilter = true;
    private boolean sessionSavedFilter = false;

    String nodeIdStr = null;
    String taskIdStr = null;
    String subnet = null;
    String ipStr = null;

    String sessionId = null;

    @PostConstruct
    public void postContruct() {
        Random r = new Random();
        Long l = r.nextLong();
        sessionId = l.toString();
    }

    // this method is calling from xhtml page becouse request params are not available in postConstruct
    public void init() {
        ExternalContext externalContext =  FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> params = externalContext.getRequestParameterMap();
        if (params.get("nodeId")!=null) nodeIdStr = params.get("nodeId");
        if (params.get("taskId")!=null) taskIdStr = params.get("taskId");
        if (params.get("net")!=null)    subnet = params.get("net");
        ipStr = params.get("host");

        initBeans();

    }

    public void initBeans() {
        Long nodeId = null;
        Integer taskId = null;
        Node node = null;
        if (nodeIdStr != null) {
            nodeId = Long.parseLong(nodeIdStr);
            node = ConnectionHandler.getInstance().getNode(nodeId);

        }
        if (node!=null & taskIdStr != null) {
            taskId = Integer.valueOf(taskIdStr);
            blb = node.getBranchLight(taskId);
        }
        if (blb!=null & subnet!=null) {
            try {
                InetAddress subnetAddr = InetAddress.getByName(subnet);
                sbl = node.getSubnetLight(taskId,subnet);

            } catch (UnknownHostException une) {
                une.printStackTrace();
            }
        }
        if (sbl!=null & ipStr!=null) {
            ipBean =  node.getIpItemBean(taskId,ipStr);
        }

    }

    public String getIpStr() {
        return ipStr;
    }

    public void setIpStr(String ipStr) {
        this.ipStr = ipStr;
        initBeans();
    }

    public String getNodeIdStr() {
        return nodeIdStr;
    }

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
        initBeans();
    }

    public String getTaskIdStr() {
        return taskIdStr;
    }

    public void setTaskIdStr(String taskIdStr) {
        this.taskIdStr = taskIdStr;
    }




    public BranchLightBean getBb() {
        return blb;
    }

    public void setBb(BranchLightBean bb) {
        this.blb = bb;
    }

    public IPItemBean getIpBean() {
        return ipBean;
    }

    public void setIpBean(IPItemBean ipBean) {
        this.ipBean = ipBean;
    }

    public SubnetLightBean getSb() {
        return sbl;
    }

    public void setSb(SubnetBean sb) {
        this.sbl = sb;
    }

    public List<?> setToList(Set<?> set) {
        return new ArrayList<>(set);

    }
    public String getSelectedSubnetAddr() {
        if (sbl !=null) return sbl.getSubnet().getHostName();
        else return "--/--";
    }

    public String getSelectedNetDataDown() {
        if (sbl != null) {
            return ConnectionHandler.processSize(sbl.getDataReceive(),2);
        } else
            return "0";
    }

    public String getSelectedNetDataUp() {
        if (sbl != null) {
            return ConnectionHandler.processSize(sbl.getDataSend(),2);
        } else
            return "0";
    }

    public String getSessionsActiveCount() {
        if (sbl != null) {
            return sbl.getActiveSesCnt().toString();
        } else return "0";
    }

    public String getSessionsAllCount() {
        if (sbl != null) {
            return sbl.getSesCnt().toString();
        } else return "0";
    }

    public String getAddrsAllCount() {
        if (sbl !=null ) {
            return sbl.getAddrCnt().toString();
        } else return "0";
    }

    public String getAddrsActiveCount() {
        if (sbl !=null ) {
            return sbl.getActiveAddrCnt().toString();
        } else return "0";
    }

    public String getSelectedIp() {
        if (ipBean != null) {
            return ipBean.getIp();
        } else return "Host Name";
    }

    public String getSelectedIpDataDown() {
        if (ipBean != null) {
            return ConnectionHandler.processSize(ipBean.getDataDown(),2);
        } else return "- KB";
    }

    public String getSelectedIpDataUp() {
        if (ipBean != null) {
            return ConnectionHandler.processSize(ipBean.getDataUp(),2);
        } else return "- KB";
    }

    public String getSelectedIpSesActive() {
        if (ipBean !=null) {
            return ipBean.getActiveCount().toString();
        } else return "-";
    }

    public String getSelectedIpSesAll() {
        if (ipBean !=null) {
            return ipBean.getSavedCount().toString();
        } else return "-";
    }

    public String getSelectedIpConIn() {
        if (ipBean != null) {
            return ipBean.getInputCount().toString();
        } else return "-";
    }

    public String getSelectedIpConOut() {
        if (ipBean != null) {
            return ipBean.getOutputCount().toString();
        } else return "-";
    }

    public boolean isSessionActiveFilter() {
        return sessionActiveFilter;
    }

    public void setSessionActiveFilter(boolean sessionActiveFilter) {
        this.sessionActiveFilter = sessionActiveFilter;

    }

    public boolean isSessionInFilter() {
        return sessionInFilter;
    }

    public void setSessionInFilter(boolean sessionInFilter) {
        this.sessionInFilter = sessionInFilter;
    }

    public boolean isSessionOutFilter() {
        return sessionOutFilter;
    }

    public void setSessionOutFilter(boolean sessionOutFilter) {
        this.sessionOutFilter = sessionOutFilter;
    }

    public boolean isSessionSavedFilter() {
        return sessionSavedFilter;
    }

    public void setSessionSavedFilter(boolean sessionSavedFilter) {
        this.sessionSavedFilter = sessionSavedFilter;
    }

    public String sizeToStr(double size,Integer afterDot) {
        return ConnectionHandler.processSize(size,afterDot);

    }
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Node) {
            if (arg instanceof SubnetLightBean) {
                SubnetLightBean receivedBean = (SubnetLightBean) arg;
                if (receivedBean.getSubnet() == sbl.getSubnet() & receivedBean.getBrId() == sbl.getBrId()) {
                    // web socket notify
                }
            }
            if (arg instanceof IPItemBean) {
                IPItemBean receivedBean = (IPItemBean) arg;
                if (receivedBean.getIp() == ipBean.getIp() & receivedBean.getBrId() == ipBean.getBrId()) {
                    // web socket notify
                }
            }
        }
    }
}
