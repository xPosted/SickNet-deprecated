package com.jubaka.sors.managed;

import com.jubaka.sors.beans.Category;
import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.entities.Branch;
import com.jubaka.sors.serverSide.ConnectionHandler;
import com.jubaka.sors.serverSide.NodeServerEndpoint;
import com.jubaka.sors.service.BranchService;
import com.jubaka.sors.service.SessionCategoriser;
import org.jfree.data.time.TimeSeries;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.websocket.Session;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by root on 28.08.16.
 */

@Named
@SessionScoped
public class TaskViewBean implements Serializable, Observer {

    @Inject
    private ConnectionHandler ch;
    @Inject
    private LoginBean loginBean;

    @Inject
    private WebSocketHandler webSocketHandler;

    @Inject
    private BranchService branchService;

    private BranchLightBean blb = null;
    private SubnetLightBean sbl = null;
    private IPItemBean ipBean = null;
    private List<?> onlineIps = new ArrayList<>();
    private List<?> allIps = new ArrayList<>();
    private List<SessionBean> sessionList = new ArrayList<>();

    private List<Category> categories;
    private Map<Category,List<SessionBean>> categoriesData;

    private NodeServerEndpoint nodeServerEndpoint = null;

    private boolean sessionInFilter = false;
    private boolean sessionOutFilter = true;
    private boolean sessionActiveFilter = true;
    private boolean sessionSavedFilter = false;

    private String nodeIdStr = null;
    private String taskIdStr = null;
    private String dbTaskIdStr = null;
    private String subnet = null;
    private String ipStr = null;


    private Long sessionId = null;
    private Session webSession = null;

    private boolean categorised = true;

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
        nodeIdStr = params.get("nodeId");
        taskIdStr = params.get("taskId");
        dbTaskIdStr = params.get("dbtaskId");
        subnet = params.get("net");
        ipStr = params.get("host");

        if (nodeIdStr != null & taskIdStr != null) {
            Long nodeUnid = Long.parseLong(nodeIdStr);
            Integer taskId = Integer.parseInt(taskIdStr);
            initTask(nodeUnid,taskId);
        }
        if (dbTaskIdStr != null) {
            Long dbTaskId = Long.parseLong(dbTaskIdStr);
            initTask(dbTaskId);
        }



          //  initBeans();

    }




    public void initBeans() {
        Long nodeId = null;
        Integer taskId = null;
        NodeServerEndpoint nodeServerEndpoint = null;

        if (nodeIdStr != null) {
            nodeId = Long.parseLong(nodeIdStr);
            nodeServerEndpoint = ch.getNodeServerEndPoint(nodeId);

        }
        if (nodeServerEndpoint !=null & taskIdStr != null) {
            taskId = Integer.valueOf(taskIdStr);
            blb = nodeServerEndpoint.getBranchLight(taskId);
            taskIdStr = null;
        }
        if (blb!=null & subnet!=null) {
            if (sbl != null) {
                nodeServerEndpoint.removeObserver(blb.getBib().getId(),sbl.getSubnet().getHostAddress());
            }
                sbl = nodeServerEndpoint.getSubnetLight(blb.getBib().getId(),subnet,true);
                onlineIps = new ArrayList<>(sbl.getLightLiveIps());
                allIps = new ArrayList<>(sbl.getLightIps());
                nodeServerEndpoint.addObserver(this);
                subnet = null;
        }
        if (sbl!=null & ipStr!=null) {
            if (ipBean!=null) {
                nodeServerEndpoint.removeObserver(blb.getBib().getId(),ipBean.getIp());
            }
            ipBean =  nodeServerEndpoint.getIpItemBean(blb.getBib().getId(),ipStr,true);
            updateCurrentSessionList();
            System.out.println("initBeans.ipBean="+ipBean.getIp());
            nodeServerEndpoint.addObserver(this);
            ipStr= null;
        }

    }
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public boolean isDbManagedTask() {
        if (blb instanceof BranchBean) {
            if ((blb.getBib().getDbid())!= null)
                return true;
        }
        return false;
    }
   public void initTask(Long nodeUnid, Integer taskId) {

       nodeServerEndpoint = ch.getNodeServerEndPoint(nodeUnid);


       if (nodeServerEndpoint !=null) {
           taskId = Integer.valueOf(taskIdStr);
           blb = nodeServerEndpoint.getBranchLight(taskId);
       }

       ipBean = null;
       sbl = null;
       onlineIps.clear();
       allIps.clear();

   }


    public void selectSubnet(String subnet) {

        if (blb instanceof BranchBean){
            BranchBean bb = (BranchBean) blb;
            SubnetBean sb = bb.getSubnetByName(subnet);
            sbl = sb;
            onlineIps = sb.getLiveIps();
            allIps = sb.getIps();
        } else {
            if (sbl != null) {
                nodeServerEndpoint.removeObserver(blb.getBib().getId(),sbl.getSubnet().getHostAddress());
            }
            sbl = nodeServerEndpoint.getSubnetLight(blb.getBib().getId(),subnet,true);
            onlineIps = new ArrayList<>(sbl.getLightLiveIps());
            allIps = new ArrayList<>(sbl.getLightIps());
            nodeServerEndpoint.addObserver(this);
        }
        if (sbl == null) return;



    }

    public void selectIp(String address) {
        if (sbl instanceof SubnetBean) {
            SubnetBean sb = (SubnetBean) sbl;
            ipBean = sb.getIpByName(address);
        } else {
            if (ipBean!=null) {
                nodeServerEndpoint.removeObserver(blb.getBib().getId(),ipBean.getIp());
            }
            ipBean =  nodeServerEndpoint.getIpItemBean(blb.getBib().getId(),address,true);
            nodeServerEndpoint.addObserver(this);
        }
        updateCurrentSessionList();
    }

    public void initTask(Long dbtaskId) {
        Branch b =  branchService.selectById(dbtaskId);
        blb = branchService.castToBean(b);

        ipBean = null;
        sbl = null;
        onlineIps.clear();
        allIps.clear();
    }

    public List<?> getSubnetList() {
        if (blb instanceof BranchBean) {
            BranchBean bb = (BranchBean) blb;
            return bb.getSubnets();
        } else {
            return blb.getSubnetsLight();
        }

    }


    public String getCurrentBranchId() {
        return blb.getBib().getId().toString();
    }

    public String getChartStrValues() {
        if (blb instanceof BranchBean) return "0";
        String result = "0";

        if (nodeServerEndpoint != null & blb != null) {
            Date timeFrom = new Date(new Date().getTime()-11000);
            Date timeTo = new Date();
            TimeSeries ts = nodeServerEndpoint.getDataOutChart(blb.getBib().getId(),timeFrom.getTime(),timeTo.getTime());

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
       // initBeans();
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
       // initBeans();
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

    public String getNetworkSessionsActiveCount() {
        if (sbl != null) {
            return sbl.getActiveSesCnt().toString();
        } else return "0";
    }

    public String getNetworkSessionsAllCount() {
        if (sbl != null) {
            return sbl.getSesCnt().toString();
        } else return "0";
    }

    public String getNetworkAddrsAllCount() {
        if (sbl !=null ) {
            return sbl.getAddrCnt().toString();
        } else return "0";
    }

    public String getNetworkAddrsActiveCount() {
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

    public boolean isCategorised() {
        return categorised;
    }

    public void setCategorised(boolean categorised) {
        this.categorised = categorised;
    }


    public List<?> getAllIps() {
        return allIps;
    }

    public void setAllIps(List<?> allIps) {
        this.allIps = allIps;
    }

    public List<?> getOnlineIps() {
        return onlineIps;
    }

    public void setOnlineIps(List<?> onlineIps) {
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

        if (isCategorised()) {
            SessionCategoriser categoriser = new SessionCategoriser();
            categoriesData = categoriser.sortByHost(getSelectedIp(),sessionList);
            categories = new ArrayList<>(categoriesData.keySet());
        }

    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof NodeServerEndpoint & webSession != null) {
            if (arg instanceof SubnetLightBean) {
                SubnetLightBean receivedBean = (SubnetLightBean) arg;
                if (receivedBean.getSubnet() == sbl.getSubnet() & receivedBean.getBrId() == sbl.getBrId()) {
                    sbl=receivedBean;
                    try {
                        webSocketHandler.updateSubnetInfo(webSession);
                    } catch(IOException io) {
                        webSession = null;
                    }

                    // web socket notify
                }
            }
            if (arg instanceof IPItemLightBean & webSession != null) {
                IPItemLightBean receivedBean = (IPItemLightBean) arg;
                if (receivedBean.getIp().equals(ipBean.getIp()) & receivedBean.getBrId() == ipBean.getBrId()) {
                    // web socket notify
                    ipBean.update(receivedBean);
                    System.out.println("initBeans.ipBean="+ipBean.getIp());
                    try {
                        webSocketHandler.updateIpInfo(webSession);

                    } catch(IOException io) {
                        webSession = null;
                    }
                }
            }
        }
    }
}
