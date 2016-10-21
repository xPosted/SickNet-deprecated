package com.jubaka.sors.managed;

import com.jubaka.sors.beans.Bean;
import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.serverSide.ConnectionHandler;
import com.jubaka.sors.serverSide.Node;
import com.jubaka.sors.serverSide.SecurityVisor;
import org.jfree.data.time.TimeSeries;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.websocket.Session;
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

    @Inject
    private WebSocketHandler webSocketHandler;

    private BranchLightBean blb = null;
    private SubnetLightBean sbl = null;
    private IPItemBean ipBean = null;
    private List<IPItemLightBean> onlineIps = null;
    private List<IPItemLightBean> allIps = null;
    private List<SessionBean> sessionList = new ArrayList<>();

    private boolean sessionInFilter = false;
    private boolean sessionOutFilter = true;
    private boolean sessionActiveFilter = true;
    private boolean sessionSavedFilter = false;

    private String nodeIdStr = null;
    private String taskIdStr = null;
    private String subnet = null;
    private String ipStr = null;


    private Long sessionId = null;
    private Session webSession = null;

    @PostConstruct
    public void postContruct() {
        Random r = new Random();
        sessionId = r.nextLong();
        webSocketHandler.putBean(sessionId,this);
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
            taskIdStr = null;
        }
        if (blb!=null & subnet!=null) {
            if (sbl != null) {
                node.removeObserver(blb.getBib().getId(),sbl.getSubnet().getHostAddress());
            }
                sbl = node.getSubnetLight(blb.getBib().getId(),subnet,true);
                onlineIps = new ArrayList<>(sbl.getLightLiveIps());
                allIps = new ArrayList<>(sbl.getLightIps());
                node.addObserver(this);
                subnet = null;
        }
        if (sbl!=null & ipStr!=null) {
            if (ipBean!=null) {
                node.removeObserver(blb.getBib().getId(),ipBean.getIp());
            }
            ipBean =  node.getIpItemBean(blb.getBib().getId(),ipStr,true);
            updateCurrentSessionList();
            System.out.println("initBeans.ipBean="+ipBean.getIp());
            node.addObserver(this);
            ipStr= null;
        }

    }

    public String getCurrentBranchId() {
        return blb.getBib().getId().toString();
    }

    public String getChartStrValues() {
        Node node = null;
        Long nodeId;
        String result = "0";
        if (nodeIdStr != null) {
            nodeId = Long.parseLong(nodeIdStr);
            node = ConnectionHandler.getInstance().getNode(nodeId);

        }
        if (node != null & blb != null) {
            Date timeFrom = new Date(new Date().getTime()-11000);
            Date timeTo = new Date();
            TimeSeries ts = node.getDataOutChart(blb.getBib().getId(),timeFrom.getTime(),timeTo.getTime());

            for (int item=0;item<ts.getItemCount();item++) {
                Integer val = (Integer) ts.getValue(item);
                result = result+","+val.toString();
            }


        }
        return result;
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
        System.out.println("subnet = "+this.subnet);
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
        System.out.println("getSubnetLight");
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
    public void changeActiveFilter() {
        this.sessionActiveFilter = !sessionActiveFilter;
        updateCurrentSessionList();

    }

    public boolean isSessionInFilter() {
        return sessionInFilter;
    }

    public void setSessionInFilter(boolean sessionInFilter) {
        this.sessionInFilter = sessionInFilter;
    }

    public void changeInFilter() {
        this.sessionInFilter = !sessionInFilter;
        updateCurrentSessionList();
    }

    public boolean isSessionOutFilter() {
        return sessionOutFilter;
    }

    public void changeOutFilter() {
        this.sessionOutFilter = !sessionOutFilter;
        updateCurrentSessionList();
    }

    public boolean isSessionSavedFilter() {
        return sessionSavedFilter;
    }

    public void setSessionSavedFilter(boolean sessionSavedFilter) {
        this.sessionSavedFilter = sessionSavedFilter;
    }

    public void changeSavedFilter() {
        this.sessionSavedFilter = !sessionSavedFilter;
        updateCurrentSessionList();
    }

    public String sizeToStr(double size,Integer afterDot) {
        return ConnectionHandler.processSize(size,afterDot);

    }
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Session getWebSession() {
        return webSession;
    }

    public void setWebSession(Session webSession) {
        System.out.println("Set web Session");
        this.webSession = webSession;
    }

    public List<IPItemLightBean> getAllIps() {
        return allIps;
    }

    public void setAllIps(List<IPItemLightBean> allIps) {
        this.allIps = allIps;
    }

    public List<IPItemLightBean> getOnlineIps() {
        return onlineIps;
    }

    public void setOnlineIps(List<IPItemLightBean> onlineIps) {
        this.onlineIps = onlineIps;
    }

    public List<SessionBean> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<SessionBean> sessionList) {
        this.sessionList = sessionList;
    }

    public void updateCurrentSessionList() {
        sessionList.clear();
        if (sessionActiveFilter & sessionInFilter) sessionList.addAll(ipBean.getActiveInSes());
        if (sessionActiveFilter & sessionOutFilter) sessionList.addAll(ipBean.getActiveOutSes());
        if (sessionSavedFilter & sessionInFilter) sessionList.addAll(ipBean.getStoredInSes());
        if (sessionSavedFilter & sessionOutFilter) sessionList.addAll(ipBean.getStoredOutSes());

    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Node) {
            if (arg instanceof SubnetLightBean) {
                SubnetLightBean receivedBean = (SubnetLightBean) arg;
                if (receivedBean.getSubnet() == sbl.getSubnet() & receivedBean.getBrId() == sbl.getBrId()) {
                    sbl=receivedBean;
                    webSocketHandler.updateSubnetInfo(webSession);
                    // web socket notify
                }
            }
            if (arg instanceof IPItemLightBean) {
                IPItemLightBean receivedBean = (IPItemLightBean) arg;
                if (receivedBean.getIp().equals(ipBean.getIp()) & receivedBean.getBrId() == ipBean.getBrId()) {
                    // web socket notify
                    ipBean.update(receivedBean);
                    System.out.println("initBeans.ipBean="+ipBean.getIp());
                    webSocketHandler.updateIpInfo(webSession);
                }
            }
        }
    }
}
