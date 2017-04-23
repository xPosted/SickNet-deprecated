package com.jubaka.sors.appserver.managed;

import com.jubaka.sors.appserver.service.BeanEntityConverter;
import com.jubaka.sors.beans.branch.BranchBean;
import com.jubaka.sors.beans.branch.IPItemBean;
import com.jubaka.sors.beans.branch.SubnetBean;
import com.jubaka.sors.appserver.entities.Branch;
import com.jubaka.sors.appserver.serverSide.ConnectionHandler;
import com.jubaka.sors.appserver.serverSide.NodeServerEndpoint;
import com.jubaka.sors.appserver.serverSide.StatisticLogic;
import com.jubaka.sors.appserver.service.BranchService;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 18.10.16.
 */
@Named
@RequestScoped
public class StatisticBean {

    @Inject
    private ConnectionHandler ch;
    @Inject
    private BranchService branchService;

    private String nodeIdStr = null;

    private String taskIdStr = null;
    private String subnet = null;
    private String ipStr = null;


    private String dbidStr = null;

    private String chartDataStr = "";
    private String humanValues = "";

    private String subView = "";


    private List<String> header = new ArrayList<>();
    private List<List<String>> tableData = new ArrayList<>();




    public void init() {
        Long nodeId = null;
        Integer taskId = null;
        NodeServerEndpoint nodeServerEndpoint = null;

        ExternalContext externalContext =  FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> params = externalContext.getRequestParameterMap();
        nodeIdStr = params.get("nodeId");
        taskIdStr = params.get("taskId");
        dbidStr = params.get("dbid");
        subnet = params.get("net");
        ipStr = params.get("host");


        if (dbidStr != null) {
            Long dbId = Long.parseLong(dbidStr);
            readStatFromDB(dbId);
            return;
        }

        if (nodeIdStr != null) {
            nodeId = Long.parseLong(nodeIdStr);
            nodeServerEndpoint = ch.getNodeServerEndPoint(nodeId);

        }
        if (ipStr!=null & taskIdStr!=null) {
            taskId = Integer.parseInt(taskIdStr);
            TimeSeries tsOut = nodeServerEndpoint.getIpDataOutChart(taskId,ipStr);

            DefaultTableModel model = nodeServerEndpoint.getIpTModel(taskId,ipStr);

            handleTableModel(model);
            handleTimeSeries(tsOut);
            subView = "host";

            return;
        }
        if (subnet != null & taskIdStr != null) {
            taskId = Integer.parseInt(taskIdStr);
            TimeSeries tsOut = nodeServerEndpoint.getNetworkDataOutChart(taskId,subnet);

            DefaultTableModel model = nodeServerEndpoint.getSubnetTModel(taskId,subnet);

            handleTableModel(model);
            handleTimeSeries(tsOut);
            subView = "host";

            return;
        }
        if (nodeServerEndpoint !=null & taskIdStr != null) {
            taskId = Integer.parseInt(taskIdStr);
            TimeSeries tsOut = nodeServerEndpoint.getDataOutChart(taskId,(long)-1,(long)-1);

            DefaultTableModel model = nodeServerEndpoint.getBaseTModel(taskId);

            handleTableModel(model);
            handleTimeSeries(tsOut);
            subView = "net";
            return;
        }
        return;
    }

    private void readStatFromDB(Long brId) {
        Branch b = branchService.selectByIdFullBranchNoPayloadv2(brId);
        BranchBean bb = BeanEntityConverter.castToBean(b,false);
        DefaultTableModel model = null;
        TimeSeries ts = null;

        if (ipStr != null) {
            IPItemBean ipBean =  bb.getIPbyName(ipStr);
            model = StatisticLogic.getIPTableModel(ipBean);
            ts = StatisticLogic.createIpDataOutSeries(null,ipBean,null,null);
            subView = "host";
        } else

        if (subnet != null) {
            SubnetBean sb = bb.getSubnetByName(subnet);
            model = StatisticLogic.getNetTableModel(sb);
            ts = StatisticLogic.createNetworkDataOutSeries(null,sb,null,null);
            subView = "host";
        } else {
            model = StatisticLogic.getBaseTableModel(bb);
            ts = StatisticLogic.createDataOutSeries(bb,null,null);
            subView = "net";
        }


        handleTableModel(model);
        handleTimeSeries(ts);



    }

    private void handleTimeSeries(TimeSeries ts) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd.MM");
        for (Object rtpO : ts.getTimePeriods()) {
            RegularTimePeriod rtp = (RegularTimePeriod) rtpO;
            Number val = ts.getValue(rtp);
            chartDataStr=chartDataStr+"['"+ sdf.format(rtp.getEnd())+"',"+ts.getValue(rtp)+",0],";
            humanValues = humanValues+"{v:"+val+", f:'"+ConnectionHandler.processSize(val.doubleValue(),2)+"'},";

        }
    }

    private void handleTableModel(DefaultTableModel model) {
        Integer modelColLen = model.getColumnCount();

        for (Integer i =0; i < modelColLen;i++) {
            header.add(model.getColumnName(i));
        }
        for (Integer i =0; i < model.getRowCount(); i++) {
            List<String> row = new ArrayList<>();
            for (Integer j = 0; j <  modelColLen;j++) {
                row.add(model.getValueAt(i,j).toString());
            }
            tableData.add(row);

        }

    }

    public String getHumanValues() {
        return humanValues;
    }

    public void setHumanValues(String humanValues) {
        this.humanValues = humanValues;
    }

    public List<List<String>> getTableData() {
        return tableData;
    }

    public void setTableData(List<List<String>> tableData) {
        this.tableData = tableData;
    }

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public String getChartDataStr() {
        return chartDataStr;
    }

    public void setChartDataStr(String resultString) {
        this.chartDataStr = resultString;
    }

    public String getTaskIdStr() {
        return taskIdStr;
    }

    public void setTaskIdStr(String taskIdStr) {
        this.taskIdStr = taskIdStr;
    }

    public String getNodeIdStr() {
        return nodeIdStr;
    }

    public void setNodeIdStr(String nodeIdStr) {
        this.nodeIdStr = nodeIdStr;
    }

    public String getSubView() {
        return subView;
    }

    public void setSubView(String subView) {
        this.subView = subView;
    }

    public boolean isDbManaged() {
        if (dbidStr != null) return true;
        return false;
    }

    public String getDbidStr() {
        return dbidStr;
    }

    public void setDbidStr(String dbidStr) {
        this.dbidStr = dbidStr;
    }


}
