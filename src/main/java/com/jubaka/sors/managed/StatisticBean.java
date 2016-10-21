package com.jubaka.sors.managed;

import com.jubaka.sors.serverSide.ConnectionHandler;
import com.jubaka.sors.serverSide.Node;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
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

    private String nodeIdStr = null;

    private String taskIdStr = null;
    private String subnet = null;
    private String ipStr = null;

    private String chartDataStr = "";
    private String humanValues = "";

    private String subView = "";


    private List<String> header = new ArrayList<>();
    private List<List<String>> tableData = new ArrayList<>();




    public void init() {
        Long nodeId = null;
        Integer taskId = null;
        Node node = null;

        ExternalContext externalContext =  FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> params = externalContext.getRequestParameterMap();
        if (params.get("nodeId")!=null) nodeIdStr = params.get("nodeId");
        if (params.get("taskId")!=null) taskIdStr = params.get("taskId");
        if (params.get("net")!=null)    subnet = params.get("net");
        ipStr = params.get("host");


        if (nodeIdStr != null) {
            nodeId = Long.parseLong(nodeIdStr);
            node = ConnectionHandler.getInstance().getNode(nodeId);

        }
        if (ipStr!=null & taskIdStr!=null) {
            taskId = Integer.parseInt(taskIdStr);
            TimeSeries tsOut = node.getIpDataOutChart(taskId,ipStr);

            DefaultTableModel model = node.getIpTModel(taskId,ipStr);

            handleTableModel(model);
            handleTimeSeries(tsOut);
            subView = "host";

            return;
        }
        if (subnet != null & taskIdStr != null) {
            taskId = Integer.parseInt(taskIdStr);
            TimeSeries tsOut = node.getNetworkDataOutChart(taskId,subnet);

            DefaultTableModel model = node.getSubnetTModel(taskId,subnet);

            handleTableModel(model);
            handleTimeSeries(tsOut);
            subView = "host";

            return;
        }
        if (node!=null & taskIdStr != null) {
            taskId = Integer.parseInt(taskIdStr);
            TimeSeries tsOut = node.getDataOutChart(taskId,(long)-1,(long)-1);

            DefaultTableModel model = node.getBaseTModel(taskId);

            handleTableModel(model);
            handleTimeSeries(tsOut);
            subView = "net";
            return;
        }
        return;
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

}
