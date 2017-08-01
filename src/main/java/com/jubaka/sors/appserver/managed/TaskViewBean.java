package com.jubaka.sors.appserver.managed;

import com.jubaka.sors.appserver.entities.Host;
import com.jubaka.sors.appserver.entities.Subnet;
import com.jubaka.sors.appserver.service.*;
import com.jubaka.sors.appserver.servlet.Statistic;
import com.jubaka.sors.beans.Category;
import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.appserver.entities.Branch;
import com.jubaka.sors.appserver.serverSide.*;
import com.jubaka.sors.desktop.protocol.application.HTTP;
import com.jubaka.sors.desktop.protocol.tcp.TCP;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.websocket.Session;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.Normalizer;
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
    private SubnetService subnetService;

    @Inject
    private HostService hostService;

    @Inject private SessionService sessionService;

    @Inject
    private PortServiceService portService;
    @Inject
    private CreateTaskBean createTaskBean;

    private BranchLightBean blb = null;
    private SubnetLightBean sbl = null;
    private IPItemBean ipBean = null;
    private List<?> onlineIps = Collections.synchronizedList(new ArrayList<>());
    private List<?> allIps = Collections.synchronizedList(new ArrayList<>());
    private List<SessionBean> sessionList = Collections.synchronizedList(new ArrayList<>());
    private List<HTTP> httpList = Collections.synchronizedList(new ArrayList<>());
    private ItemPager sessionListPager;
    private ItemPager httpListPager;
    private List<SessionBean> sessionListPage = Collections.synchronizedList(new ArrayList<>());
    private List<HTTP> httpListPage = Collections.synchronizedList(new ArrayList<>());


    private Integer itemsPerPage = 50;


    private List<Category> newCategories = new ArrayList<>();
  //  private List<SessionBean> newSessions = new ArrayList<>();

    private SessionBean selectedSesBean = null;
    private HTTP selectedHttpPacket = null;

    private String selectedSesSrcData = "";
    private String selectedSesDstData = "";
    private String selectedPacSessionData="";
    private String selectedPacketData = "";



    private List<Category> categories= Collections.synchronizedList(new ArrayList<>());
    private List<SmartFilter> filters = new ArrayList<>();
 //   private Map<Category,List<SessionBean>> categoriesData;
    private Category selectedCat = null;
 //   private int categoryType = 0;
  //  private boolean categorised = true;

    private NodeServerEndpoint nodeServerEndpoint = null;

    private boolean sessionInFilter = true;
    private boolean sessionOutFilter = true;
    private boolean sessionActiveFilter = true;
    private boolean sessionSavedFilter = true;


            /// VIEW MODE
    private boolean httpViewMode = true;
    private boolean sessionViewMode = false;
/////////////
    private String nodeIdStr = null;
    private String taskIdStr = null;

    private String currentTaskName = "";
    private String dbTaskIdStr = null;
    private String localidStr = null;
    private String subnet = null;
    private String ipStr = null;


    private Long sessionId = null;
    private Session webSession = null;
    private String chartDataStr="";
    private String selectedCategoryId = "";

    private String netAddr="";
    private String mask="";

    private boolean dbMode = false;
    private boolean tmpLocalMode = false;
    private boolean liveNodeMode =  false;
    private String pcapFilter = "";

    @PreDestroy
    public void clean() {

    }

    @PostConstruct
    public void postContruct() {
        Random r = new Random();
        sessionId = r.nextLong();
        webSocketHandler.putBean(sessionId,this);
    }

    // this method is calling from xhtml page becouse request params are not available in postConstruct
    public void init() {
        if (loginBean.getUser() == null) {
            loginBean.redirectToLogIn();
        }


        ExternalContext externalContext =  FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> params = externalContext.getRequestParameterMap();
        String nodeIdStr = params.get("nodeId");
        String taskIdStr = params.get("taskId");
        String dbTaskIdStr = params.get("dbid");
        localidStr = params.get("localid");
        String subnet = params.get("net");
        String ipStr = params.get("host");

        filters.clear();
        if (nodeIdStr != null & taskIdStr != null) {
            if (subnet != null) this.subnet = subnet;
            if (ipStr != null) this.ipStr = ipStr;

            Long nodeUnid = Long.parseLong(nodeIdStr);
            Integer taskId = Integer.parseInt(taskIdStr);
            initTask(nodeUnid,taskId);
        } else
        if (dbTaskIdStr != null) {
            boolean clearData = false;
            if (this.dbTaskIdStr != null & dbTaskIdStr != null)
                if  ( ! this.dbTaskIdStr.equals(dbTaskIdStr))
                    clearData = true;
            this.dbTaskIdStr = dbTaskIdStr;
            Long dbTaskId = Long.parseLong(dbTaskIdStr);
            initTask(dbTaskId,clearData);
        } else
        if (localidStr!=null) {
            Integer localid = Integer.parseInt(localidStr);
            init(localid);

        }

        filters.add(new HostSessionFilter());
        filters.add(new PortSessionFilter(portService));
          //  initBeans();

    }

    public void init(Integer localid) {
        dbMode = false;
        tmpLocalMode = true;
        liveNodeMode = false;

        blb = createTaskBean.viewNotReadyPersistedTask(localid);
        ipBean = null;
        sbl = null;

        onlineIps.clear();
        allIps.clear();

    }

    public void initTask(Long dbtaskId, boolean clearData) {
        dbMode = true;
        tmpLocalMode = false;
        liveNodeMode = false;


        Branch b =  branchService.selectByIdWithNets(dbtaskId);
        blb = BeanEntityConverter.castToLightBean(null,b);
        currentTaskName = blb.getBib().getBranchName();
        if (clearData) {
            categories.clear();
            httpList.clear();
           // httpListPage.clear();     subList should not be cleared
            sessionList.clear();
          //  sessionListPage.clear();   subList should not be cleared


            ipBean = null;
            sbl = null;
            onlineIps.clear();
            allIps.clear();
        }

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
      setSessionList(selectedCat.getSessionList());
      setHttpList(selectedCat.getHttpList());
        System.out.println("select cat - " + selectedCat.getName());
        updatePcapFilter();
       /// / refreshFilter(selectedCat);
    }

   /* public void setSelectedCat(String listId) {
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
*/

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
        tmpLocalMode = false;
        liveNodeMode = true;
        dbMode = false;
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

        if (liveNodeMode) {
            if (sbl != null) {
                nodeServerEndpoint.removeObserver(blb.getBib().getId(),sbl.getSubnet().getHostAddress());
            }
            sbl = nodeServerEndpoint.getSubnetLight(blb.getBib().getId(),subnet,true);
            onlineIps = new ArrayList<>(sbl.getLightLiveIps());
            allIps = new ArrayList<>(sbl.getLightIps());
            nodeServerEndpoint.addObserver(this);
        } else
        if (dbMode) {
                if (blb instanceof BranchBean){
                    BranchBean bb = (BranchBean) blb;
                    SubnetBean sb = bb.getSubnetByName(subnet);
                    sbl = sb;
                    onlineIps = sb.getLiveIps();
                    allIps = sb.getIps();
                } else {
                    SubnetLightBean subnetLightBean = blb.getSubnetByName(subnet);
                    sbl = subnetLightBean;
                    if (sbl.getAllIpList().size() == 0) {
                        Subnet subEnt = subnetService.eagerSelectById(sbl.getDbId());
                        sbl = BeanEntityConverter.castToLightBean(null,subEnt);
                    }
                    onlineIps = sbl.getLightLiveIps();
                    allIps = sbl.getLightIps();
                }
        } else
            if (tmpLocalMode) {
                if (blb instanceof BranchBean){
                    BranchBean bb = (BranchBean) blb;
                    SubnetBean sb = bb.getSubnetByName(subnet);
                    sbl = sb;
                    onlineIps = sb.getLiveIps();
                    allIps = sb.getIps();
                }
            }



       // if (sbl == null) return;
    }

    public void selectIp(String address) {
        if (liveNodeMode)  {
            if (ipBean!=null) {
                nodeServerEndpoint.removeObserver(blb.getBib().getId(),ipBean.getIp());
            }
            ipBean =  nodeServerEndpoint.getIpItemBean(blb.getBib().getId(),address,true);
            nodeServerEndpoint.addObserver(this);
        } else
            if (dbMode) {
                if (sbl instanceof SubnetBean) {
                    SubnetBean sb = (SubnetBean) sbl;
                    IPItemBean fullBean = sb.getIpByName(address);
                    ipBean = fullBean;
                } else {
                    IPItemLightBean ipBeanLight = sbl.getIpByName(address);
                    Host h = hostService.selectByIdWithSesWithHttpv2(ipBeanLight.getDbId());    ////////////////////////////////////////////////
                    //  Host h = hostService.selectByIdWithSesWithHttp(ipBeanLight.getDbId());
                    ipBean = BeanEntityConverter.castToBean(h,false);
                }
            } else
                if (tmpLocalMode) {
                    if (sbl instanceof SubnetBean) {
                        SubnetBean sb = (SubnetBean) sbl;
                        IPItemBean fullBean = sb.getIpByName(address);
                        ipBean = fullBean;
                    }
                }

        refreshFilters();
        updatePcapFilter();
    }

    public void refreshFilters() {
        if (filters.size()>0)
            refreshFiltersNew(getSelectedIp(),null,filters.get(0));
    }



    public boolean isTaskViewReady() {
        if (dbMode) {
            if (dbTaskIdStr != null)
                if (Long.parseLong(dbTaskIdStr)>=0) return true;
        }
        return false;
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

    public void prepareExpandedData(SessionBean fullBean,int selectedHttpSeq) {
        setSelectedSesBean(fullBean);
        ByteArrayOutputStream baosPacket = new ByteArrayOutputStream();
        ByteArrayOutputStream baosSrc = new ByteArrayOutputStream();
        ByteArrayOutputStream baosDst = new ByteArrayOutputStream();
        ByteArrayOutputStream baosAll = new ByteArrayOutputStream();
        byte[] targetPacketByte = null;

        for (TCP tcp : fullBean.getTcpBuf()) {
            try {
                baosAll.write(tcp.getPayload());
                if (tcp.getSrcIP().equals(fullBean.getSrcIP()))
                    baosSrc.write(tcp.getPayload());
                if (tcp.getSrcIP().equals((fullBean.getDstIP())))
                    baosDst.write(tcp.getPayload());
                if (tcp.getSequence() == selectedHttpSeq)
                    baosPacket.write(tcp.getPayload());
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        try {
            baosDst.flush();
            baosSrc.flush();
            baosPacket.flush();
            baosAll.flush();
            targetPacketByte = baosPacket.toByteArray();
            selectedPacketData = decodeData(targetPacketByte);
            selectedSesSrcData = decodeData(baosSrc.toByteArray());
            selectedSesDstData = decodeData(baosDst.toByteArray());
            selectedPacSessionData = decodeData(baosAll.toByteArray());
            baosDst.close();
            baosSrc.close();
            baosAll.close();
            baosPacket.close();

        }   catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void prepareExpandedData(long sessionId,int selectedHttpSeq) {

        com.jubaka.sors.appserver.entities.Session sesEntity = sessionService.selectByIdWithData(sessionId);
        SessionBean sesBean = BeanEntityConverter.castToBean(sesEntity,true);
        prepareExpandedData(sesBean,selectedHttpSeq);

    }

    public void expandedViewOn(SessionBean ses) {

        if (tmpLocalMode) {
            prepareExpandedData(ses,-1);
        }
        if (dbMode) {
            prepareExpandedData(ses.getDbId(),-1);
        }

    }

    public void expandedViewOn(HTTP packet) {
        if (tmpLocalMode) {
            for (SessionBean ses : sessionList) {
                if (ses.getHttpBuf().contains(packet)){
                    prepareExpandedData(ses,packet.getSequence());
                }
            }
        }
        if (dbMode)
            prepareExpandedData(packet.getSessionId(),packet.getSequence());
    }

    public static String decodeData (byte[] buf) {
        StringBuilder strBuilder = new StringBuilder();
        if (buf==null) return strBuilder.toString();
        if (buf.length != 0) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buf)));
            try {
                while (reader.ready())
                    strBuilder.append(reader.readLine()+"\n");
            } catch (IOException io) {
                io.printStackTrace();
            }


        }
        String result = strBuilder.toString();

      //  result = Normalizer.normalize(result, Normalizer.Form.NFD);
      //  result = result.replaceAll("[^\\x20-\\x7e]", "");

        result = result.replaceAll("[[^\\p{Print}]&&[^\\n]&&[^\\r]]", ".");
        result = result.replace('<','_');
        result = result.replace('>','_');
        result = result.replace('&','_');
       // result = result.replace("\\","\\\\");
        return result;
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

    public boolean isHttpViewMode() {
        return httpViewMode;
    }

    public boolean isSessionViewMode() {
        return sessionViewMode;
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

    public String getDbTaskIdStr() {
        return dbTaskIdStr;
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

    public synchronized List<HTTP> getHttpListPage() {
        return httpListPage;
    }

    public synchronized List<SessionBean> getSessionListPage() {
        return sessionListPage;
    }

    public void setSessionListPage(List<SessionBean> sessionListPage) {
        this.sessionListPage = sessionListPage;
    }

    public void setHttpListPage(List<HTTP> httpListPage) {
        this.httpListPage = httpListPage;
    }

    public  List<HTTP> getHttpList() {
        return httpList;
    }

    public  void setHttpList(List<HTTP> httpList) {
      this.httpList.clear();
        this.httpList = new ArrayList<>(httpList);
        httpListPager = new ItemPager(itemsPerPage,this.httpList);
        this.httpListPage = httpListPager.loadMore(0);
    }

    public String getNetAddr() {
        return netAddr;
    }

    public void setNetAddr(String netAddr) {
        this.netAddr = netAddr;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public HTTP getSelectedHttpPacket() {
        return selectedHttpPacket;
    }

    public void setSelectedHttpPacket(HTTP selectedHttpPacket) {
        this.selectedHttpPacket = selectedHttpPacket;
    }

    public List<?> setToList(Set<?> set) {
        return new ArrayList<>(set);

    }
    public String getCurrentTaskName() {
        return currentTaskName;
    }

    public void setCurrentTaskName(String currentTaskName) {
        this.currentTaskName = currentTaskName;
    }

    public String getSelectedSesSrcData() {
        return selectedSesSrcData;
    }

    public void setSelectedSesSrcData(String selectedSesSrcData) {
        this.selectedSesSrcData = selectedSesSrcData;
    }

    public String getSelectedSesDstData() {
        return selectedSesDstData;
    }

    public void setSelectedSesDstData(String selectedSesDstData) {
        this.selectedSesDstData = selectedSesDstData;
    }

    public String getSelectedPacketData() {
        return selectedPacketData;
    }

    public void setSelectedPacketData(String selectedPacketData) {
        this.selectedPacketData = selectedPacketData;
    }

    public String getSelectedPacSessionData() {
        return selectedPacSessionData;
    }

    public String getPcapFilter() {
        return pcapFilter;
    }

    public void setPcapFilter(String pcapFilter) {
        this.pcapFilter = pcapFilter;
    }

    public  void prepareNexPage() {
      if (sessionViewMode) {
          sessionListPage = sessionListPager.nextPage();
      }
      if (httpViewMode) {
          httpListPage = httpListPager.nextPage();
      }
    }

    public  void loadMore() {
        if (sessionViewMode) {
            sessionListPage = sessionListPager.loadMore(sessionListPage.size());
        }
        if (httpViewMode) {
            httpListPage = httpListPager.loadMore(httpListPage.size());
        }
    }

    public void preparePreviousPage() {
        if (sessionViewMode) {
            sessionListPage = sessionListPager.previousPage();
        }
        if (httpViewMode) {
            httpListPage = httpListPager.previousPage();
        }
    }

    public void setSelectedPacSessionData(String selectedPacSessionData) {
        this.selectedPacSessionData = selectedPacSessionData;
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
        refreshFilters();
  //      refreshFilter(null);

    }
    public void setHttpViewMode() {
      httpViewMode = true;
      sessionViewMode = false;

    }

    public void setSessionViewMode() {
      sessionViewMode = true;
      httpViewMode = false;

    }

    public String getChartDataStr() {

        System.out.println("chartStrVal = "+chartDataStr);
        return chartDataStr;
    }

    public void setChartDataStr(String chartDataStr) {
        this.chartDataStr = chartDataStr;
    }


    public String buildSessionChartStr() {

        TimeSeries tsDst;
        TimeSeries tsSrc;
        if (selectedSesBean==null) return "";
if (dbMode || tmpLocalMode) {
    tsDst = StatisticLogic.createSessionDstTS(selectedSesBean);
    tsSrc = StatisticLogic.createSessionSrcTS(selectedSesBean);
} else {
     tsDst = nodeServerEndpoint.getSesDstDataChart(blb.getBib().getId(),sbl.getSubnet().getHostAddress(),selectedSesBean.getEstablished().getTime());
     tsSrc = nodeServerEndpoint.getSesSrcDataChart(blb.getBib().getId(),sbl.getSubnet().getHostAddress(),selectedSesBean.getEstablished().getTime());
}

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
        refreshFilters();
    //    refreshFilter(null);
    }

    public boolean isSessionOutFilter() {
        return sessionOutFilter;
    }

    public void changeOutFilter() {
        this.sessionOutFilter = !sessionOutFilter;
        refreshFilters();
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
        refreshFilters();
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

    public  List<SessionBean> getSessionList() {
        return sessionList;
    }

    public  void setSessionList(List<SessionBean> sessionList) {
      this.sessionList.clear();
      this.sessionList =  new ArrayList<>(sessionList);

      sessionListPager = new ItemPager(itemsPerPage,this.sessionList);
      this.sessionListPage = sessionListPager.loadMore(0);
    }

    public String longToStr(Long size) {
        return ConnectionHandler.processSize(size,1);
    }

    public void printArgs(final String id) {
        System.out.println(" printArgs "+id);

    }

    public  void refreshFiltersNew(String selectedIp, List<Category> categories, SmartFilter filter) {
      List<SessionBean> sessions = Collections.synchronizedList(new ArrayList<>());
      List<HTTP> https = Collections.synchronizedList(new ArrayList<>());

        this.categories.clear();
        Integer currentIndexofFilter = filters.indexOf(filter);
        if (categories == null) {

            if (sessionActiveFilter & sessionInFilter) sessions.addAll(ipBean.getActiveInSes());
            if (sessionActiveFilter & sessionOutFilter) sessions.addAll(ipBean.getActiveOutSes());
            if (sessionSavedFilter & sessionInFilter) sessions.addAll(ipBean.getStoredInSes());
            if (sessionSavedFilter & sessionOutFilter) sessions.addAll(ipBean.getStoredOutSes());
            /// gather protocol from sessions

            for (SessionBean sesBean : sessions) {
                https.addAll(sesBean.getHttpBuf());
            }
            ///
            categories = filter.sort(selectedIp,sessions);
            if (currentIndexofFilter < (filters.size()-1)) {
                SmartFilter nextFilter = filters.get(currentIndexofFilter+1);
                refreshFiltersNew(selectedIp,categories,nextFilter);
            }
            this.categories.addAll(categories);

            setHttpList(https);
            setSessionList(sessions);


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


    public void addNetwork() {
        System.out.println(netAddr + " " + mask);
        BranchBean bb = null;
        if (dbMode){
            Branch bEntity = branchService.selectByIdFullBranchNoPayloadv2(blb.getBib().getDbid());
            bb = BeanEntityConverter.castToBean(bEntity,false);
        }
        try {
            InetAddress addr = InetAddress.getByName(netAddr);
            int m = Integer.parseInt(mask);

            branchService.addNet(addr,m,bb.getBib().getDbid());

        }   catch(UnknownHostException ex) {
            ex.printStackTrace();
        }
        Long dbTaskId = Long.parseLong(dbTaskIdStr);
        initTask(dbTaskId,false);
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

    class ItemPager<T> {

        private Integer pointer = 0;
        private Integer itemCount = 100;
        private List<T> items = null;

        public ItemPager(Integer itemCount, List<T> items) {
            this.itemCount = itemCount;
            this.items = items;
        }



        public List<T> previousPage() {
            List<T> result;


            int newPointer  = pointer-itemCount;
            if (newPointer<0) newPointer = 0;
            int beginIndex = newPointer - itemCount;
            if (beginIndex<0) beginIndex=0;

            result = items.subList(beginIndex,newPointer);
            pointer = newPointer;
            return result;
        }

        public List<T> nextPage() {
            List<T> result;
            int newPointer = pointer + itemCount;
            if (newPointer > items.size()) newPointer = items.size();
            result = Collections.synchronizedList(items.subList(pointer, newPointer));
            pointer=newPointer;
            return result;
        }

        public List<T> loadMore(int currentSize) {
            List<T> result;
            int endPointer = pointer + currentSize + itemCount;
            if (endPointer > items.size()) endPointer = items.size();
            result = Collections.synchronizedList(items.subList(pointer, endPointer));
            return result;
        }
    }

    public void updatePcapFilter() {

        String filter = "";
        if (ipBean != null) {
            if (sessionInFilter & !sessionOutFilter) filter = filter + "dst ";
            if (sessionOutFilter & ! sessionInFilter) filter = filter + "src ";
            filter = "host "+ipBean.getIp();
            if (selectedCat != null) {
                if (selectedCat.getParent() != null) {
                    Category parent = selectedCat.getParent();
                    if (parent.getParentType() instanceof PortSessionFilter) filter = filter+ " and tcp port "+parent.getName();
                    if (parent.getParentType() instanceof HostSessionFilter) filter = filter+ " and host "+parent.getName();
                }
                if (selectedCat.getParentType() instanceof PortSessionFilter) filter = filter+ " and tcp port "+selectedCat.getName();
                if (selectedCat.getParentType() instanceof HostSessionFilter) filter = filter+ " and host "+selectedCat.getName();

            }
        }
        setPcapFilter(filter);


    }
}
