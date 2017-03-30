package com.jubaka.sors.appserver.managed;

import com.jubaka.sors.beans.Category;
import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.appserver.entities.Branch;
import com.jubaka.sors.appserver.serverSide.*;
import com.jubaka.sors.appserver.service.BranchService;
import com.jubaka.sors.appserver.service.PortServiceService;
import org.jfree.data.time.RegularTimePeriod;
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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
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

    @Inject
    private PortServiceService portService;

    private BranchLightBean blb = null;
    private SubnetLightBean sbl = null;
    private IPItemBean ipBean = null;
    private List<?> onlineIps = new ArrayList<>();
    private List<?> allIps = new ArrayList<>();
    private List<SessionBean> sessionList = new ArrayList<>();
    private List<Category> newCategories = new ArrayList<>();
    private List<SessionBean> newSessions = new ArrayList<>();

    private SessionBean selectedSesBean = null;

    private List<Category> categories= Collections.synchronizedList(new ArrayList<>());
    private List<SmartFilter> filters = new ArrayList<>();
 //   private Map<Category,List<SessionBean>> categoriesData;
    private Category selectedCat = null;
 //   private int categoryType = 0;
  //  private boolean categorised = true;

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

    private String chartDataStr="";


    private String selectedCategoryId = "";



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

        filters.add(new HostSessionFilter());
        filters.add(new PortSessionFilter(portService));
          //  initBeans();

    }


/*

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
    //        refreshFilter(null);
            nodeServerEndpoint.addObserver(this);
            ipStr= null;
        }

    }
    */

    public Category getSelectedCat() {
        return selectedCat;
    }

    public void setSelectedCat(Category selectedCat) {
        this.selectedCat = selectedCat;
      sessionList = selectedCat.getSessionList();
        System.out.println("select cat - " + selectedCat.getName());
       /// / refreshFilter(selectedCat);
    }

    public void setSelectedCat(String listId) {
        int id = 0;
        try {
            id = Integer.decode(listId);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            return;
        }

        this.selectedCat =  categories.get(id);
        sessionList = selectedCat.getSessionList();
    }


    public List<Category> getCategories() {

        return categories;
    }

  //  public void setCategories(List<Category> categories) {      this.categories = categories;
   // }

    public boolean isDbManagedTask() {
        if (blb instanceof BranchBean) {
            if ((blb.getBib().getDbid())!= null)
                return true;
        }
        return false;
    }
   public void initTask(Long nodeUnid, Integer taskId) {

       if (nodeServerEndpoint != null) {
           if (nodeServerEndpoint.getUnid().equals(nodeUnid)) {
               if (blb.getBib().getId().equals(taskId))
                   return;
               //You have already viewed this branch before
           }
       }
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

    public String getSelectedNetDataDownDirect() {
        if (sbl != null) return sbl.getDataReceive().toString();
        return "0";
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

        refreshFilters();

    }

    public void refreshFilters() {
        if (filters.size()>0)
            refreshFiltersNew(getSelectedIp(),null,filters.get(0));
    }

    public void initTask(Long dbtaskId) {
        Branch b =  branchService.selectById(dbtaskId);
        blb = branchService.castToBean(b);

        categories.clear();
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

    public SessionBean getSelectedSesBean() {
        return selectedSesBean;
    }

    public void setSelectedSesBean(SessionBean selectedSesBean) {
        System.out.println("Session "+selectedSesBean.getDstIP()+" https size "+ selectedSesBean.getHttpBuf().size());
        this.selectedSesBean = selectedSesBean;
        buildSessionChartStr();
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
            if (ts == null) return "0";
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
       // initBeans();
    }

    public String getTaskIdStr() {
        return taskIdStr;
    }

    public void setTaskIdStr(String taskIdStr) {
        this.taskIdStr = taskIdStr;
    }

  //  public int getCategoryType() {
  //      return categoryType;
   // }
/*
    public void setCategoryTypeHost() {
        categoryType = 0;
        selectedCat = null;
        updateCurrentSessionList();
    }
    public void setCategoryTypePort() {
        categoryType = 1;
        selectedCat = null;
        updateCurrentSessionList();
    }
*/
  public String getSelectedCategoryId() {
      return selectedCategoryId;
  }

    public void setSelectedCategoryId(String selectedCategoryId) {
        this.selectedCategoryId = selectedCategoryId;
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

    public List<SmartFilter> getFilters() {
        return filters;
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
  //      refreshFilter(null);

    }

    public String getChartDataStr() {

        System.out.println("chartStrVal = "+chartDataStr);
        return chartDataStr;
    }

    public void setChartDataStr(String chartDataStr) {
        this.chartDataStr = chartDataStr;
    }


    public String buildSessionChartStr() {

        if (selectedSesBean==null) return "";

       TimeSeries tsDst = nodeServerEndpoint.getSesDstDataChart(blb.getBib().getId(),sbl.getSubnet().getHostAddress(),selectedSesBean.getEstablished().getTime());
        TimeSeries tsSrc = nodeServerEndpoint.getSesSrcDataChart(blb.getBib().getId(),sbl.getSubnet().getHostAddress(),selectedSesBean.getEstablished().getTime());
        return handleTimeSeries(tsDst,tsSrc);
    }

    private String handleTimeSeries(TimeSeries tsDst,TimeSeries tsSrc) {

        chartDataStr = "{\n" +
                "  \"cols\": [\n" +
                "    {\"id\":\"\",\"label\":\"Topping\",\"pattern\":\"\",\"type\":\"string\"},\n" +
                "    {\"id\":\"\",\"label\":\"Dst transmitted data\",\"pattern\":\"\",\"type\":\"number\"},\n" +
                "    {\"id\":\"\",\"label\":\"Src transmitted data\",\"pattern\":\"\",\"type\":\"number\"}\n" +
                "  ],\n" +
                "  \"rows\": [";

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd.MM");
        Collection timePeriodCollection  = tsSrc.getTimePeriods();
        timePeriodCollection.addAll(tsDst.getTimePeriods());
        String valDstStr = "";
        String valSrcStr = "";
        for (Object rtpO : timePeriodCollection) {
            RegularTimePeriod rtp = (RegularTimePeriod) rtpO;
            Number valDst = tsDst.getValue(rtp);
            Number valSrc = tsSrc.getValue(rtp);
            if (valDst == null) valDst = 0;
            if (valSrc == null) valSrc = 0;
            valDstStr = ConnectionHandler.processSize(valDst.doubleValue(),2);
            valSrcStr = ConnectionHandler.processSize(valSrc.doubleValue(),2);
            chartDataStr=chartDataStr+"{\"c\":[{\"v\":\""+ sdf.format(rtp.getEnd())+"\",\"f\":null},{\"v\":"+valDst+",\"f\":\""+valDstStr+"\"},{\"v\":"+valSrc+",\"f\":\""+valSrcStr+"\"}]},";

         //   humanValues = humanValues+"{v:"+valDst+", f:'"+ConnectionHandler.processSize(valDst.doubleValue(),2)+"'},";
         //   if (valDst != valSrc) humanValues = humanValues+"{v:"+valSrc+", f:'"+ConnectionHandler.processSize(valSrc.doubleValue(),2)+"'},";

        }
        chartDataStr +="  ]\n}";
        return chartDataStr;
    }



    public boolean isSessionInFilter() {
        return sessionInFilter;
    }

    public void setSessionInFilter(boolean sessionInFilter) {
        this.sessionInFilter = sessionInFilter;
    }

    public void changeInFilter() {
        this.sessionInFilter = !sessionInFilter;
    //    refreshFilter(null);
    }

    public boolean isSessionOutFilter() {
        return sessionOutFilter;
    }

    public void changeOutFilter() {
        this.sessionOutFilter = !sessionOutFilter;
   //     refreshFilter(null);
    }

    public boolean isSessionSavedFilter() {
        return sessionSavedFilter;
    }

    public void setSessionSavedFilter(boolean sessionSavedFilter) {
        this.sessionSavedFilter = sessionSavedFilter;
    }

    public void changeSavedFilter() {
        this.sessionSavedFilter = !sessionSavedFilter;
   //     refreshFilter(null);
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

//    public void setCategorised(boolean categorised) {
 //       this.categorised = categorised;
  //  }


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

    public String longToStr(Long size) {
        return ConnectionHandler.processSize(size,1);
    }

    public void printArgs(final String id) {
        System.out.println(" printArgs "+id);

    }

    public void refreshFiltersNew(String selectedIp, List<Category> categories, SmartFilter filter) {
        this.categories.clear();
        Integer currentIndexofFilter = filters.indexOf(filter);
        if (categories == null) {
            sessionList.clear();
            if (sessionActiveFilter & sessionInFilter) sessionList.addAll(ipBean.getActiveInSes());
            if (sessionActiveFilter & sessionOutFilter) sessionList.addAll(ipBean.getActiveOutSes());
            if (sessionSavedFilter & sessionInFilter) sessionList.addAll(ipBean.getStoredInSes());
            if (sessionSavedFilter & sessionOutFilter) sessionList.addAll(ipBean.getStoredOutSes());
            categories = filter.sort(selectedIp,sessionList);
            if (currentIndexofFilter < (filters.size()-1)) {
                SmartFilter nextFilter = filters.get(currentIndexofFilter+1);
                refreshFiltersNew(selectedIp,categories,nextFilter);
            }
            this.categories.addAll(categories);

        } else {
            for (Category cat : categories) {
                cat.setSubCategories(filter.sort(selectedIp, cat.getSessionList()));
                if (currentIndexofFilter < (filters.size()-1)) {
                    SmartFilter nextFilter = filters.get(currentIndexofFilter+1);
                    refreshFiltersNew(selectedIp,cat.getSubCategories(),nextFilter);

                }
            }
        }


    }

    public List<Category> categorise(String selectedIp, List<SessionBean> sessions, List<SmartFilter> filters, SmartFilter currentFilter) {
        Integer currentIndexofFilter = filters.indexOf(currentFilter);
        List<Category> cats = currentFilter.sort(selectedIp,sessions);
        if ((currentIndexofFilter+1)<filters.size()) {
            for (Category cat : cats) {
                cat.setSubCategories(categorise(selectedIp,cat.getSessionList(),filters,filters.get(currentIndexofFilter+1)));
            }
        }

        return cats;

    }


/*
    public void refreshFilter(Category selectedCat) {
        sessionList.clear();
        if (sessionActiveFilter & sessionInFilter) sessionList.addAll(ipBean.getActiveInSes());
        if (sessionActiveFilter & sessionOutFilter) sessionList.addAll(ipBean.getActiveOutSes());
        if (sessionSavedFilter & sessionInFilter) sessionList.addAll(ipBean.getStoredInSes());
        if (sessionSavedFilter & sessionOutFilter) sessionList.addAll(ipBean.getStoredOutSes());

        if (filters.size()==0) return;
        if (selectedCat == null) {
            filters.get(0).sort(getSelectedIp(),sessionList);
            for (Integer i =1;i<filters.size();i++) {
                filters.get(i).clearState();

            }
            sessionList.clear();
            return;
        }
        if (selectedCat != null) {
            SmartFilter smartFilter = selectedCat.getParentType();
            Integer currentFilterIndex = filters.indexOf(smartFilter);
            if (currentFilterIndex == (filters.size()-1))  sessionList = selectedCat.getSessionList();
            else {
                SmartFilter filter = filters.get(currentFilterIndex+1);
                filter.sort(getSelectedIp(),selectedCat.getSessionList());
                sessionList.clear();
                return;
            }
        }




    }
*/

    public boolean passBasicFilter(SessionLightBean slb) {
        if (sessionInFilter) {
            if (    !   slb.getDstIP().equals(ipBean.getIp())) {
                return false;
            }
        }
        if (sessionOutFilter) {
            if (    !   slb.getSrcIP().equals(ipBean.getIp())) {
                return false;
            }
        }
        if (sessionActiveFilter) {
            if (    !   (slb.getClosed() == null)) {
                return false;
            }
        }
        if (sessionSavedFilter) {
            if (    !   (slb.getClosed() !=null)) {
                return false;
            }
        }
        return true;
    }


    public boolean isHostCategorised() {
        SmartFilter mainFilter = filters.get(0);

        if (mainFilter != null) {
            if (mainFilter instanceof HostSessionFilter) {
                return true;
            }
        }
        return false;
    }

    public boolean isPortCategorised() {
        SmartFilter mainFilter = filters.get(0);

        if (mainFilter != null) {
            if (mainFilter instanceof PortSessionFilter) {
                return true;
            }
        }
        return false;
    }

    public void setHostPortCategorised() {
        filters.clear();
        filters.add(new HostSessionFilter());
        filters.add(new PortSessionFilter(portService));
        refreshFilters();
    }
    public void setPortHostCategorised() {
        filters.clear();
        filters.add(new PortSessionFilter(portService));
        filters.add(new HostSessionFilter());
        refreshFilters();
    }


    public void addHostFilter() {
        filters.add(new HostSessionFilter());
      //  refreshFilter(null);
        System.out.println(" add fileter");
    }

    public void addPortFilter() {
        filters.add(new PortSessionFilter(portService));
     //   refreshFilter(null);
        System.out.println(" add fileter");
    }
    public String getDnsNameIfNotEquals(String ip) {
        try{

        String dns = InetAddress.getByName(ip).getHostName();
        if (!dns.equals(ip)) {
            return dns;
        }
        } catch (UnknownHostException un) {
            un.printStackTrace();
        }
        return "";
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof NodeServerEndpoint & webSession != null) {
            if (o == nodeServerEndpoint){
                if (arg instanceof SubnetLightBean) {
                    SubnetLightBean receivedBean = (SubnetLightBean) arg;
                    if (sbl == null) return;
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
                if (arg instanceof IPItemLightBean) {
                    IPItemLightBean receivedBean = (IPItemLightBean) arg;
                    if (ipBean == null) return;
                    if (receivedBean.getIp().equals(ipBean.getIp()) & receivedBean.getBrId() == ipBean.getBrId()) {
                        // web socket notify
                        ipBean.update(receivedBean);
                        try {
                            webSocketHandler.updateIpInfo(webSession);

                        } catch(IOException io) {
                            webSession = null;
                        }
                        if (receivedBean.getNewSessionsForCC().size()>0) {

                            boolean updateCategories = false;
                            boolean addCategories = false;

                            List<Category> newCats = categorise(ipBean.getIp(),receivedBean.getNewSessionsForCC(),filters,filters.get(0));
                          //  newCategories.addAll(newCats);


                            synchronized (categories) {
                                for (Category cat : categories) {
                                    for (Category newCat : newCats) {
                                        if (cat.checkForEquals(newCat)) {
                                            updateCategories = true;

                                        } else {
                                            addCategories = true;
                                        //    categories.addAll(newCats);


                                        }
                                    }

                                }
                            }

                            if (updateCategories) {
                                try {
                                    webSocketHandler.updateCategoryInfo(webSession);
                                } catch (IOException io) {
                                    webSession=null;
                                    io.printStackTrace();
                                }
                                System.out.println("updateCategories");
                            }
                            if (addCategories) System.out.println("addCategories "+newCategories.size());

                        }

                    }
                }

            }
        }

    }
}
