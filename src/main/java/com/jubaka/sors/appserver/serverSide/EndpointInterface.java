package com.jubaka.sors.appserver.serverSide;

import com.jubaka.sors.beans.FileListBean;
import com.jubaka.sors.beans.InfoBean;
import com.jubaka.sors.beans.SecPolicyBean;
import com.jubaka.sors.beans.SesDataCapBean;
import com.jubaka.sors.beans.branch.*;
import org.jfree.data.time.TimeSeries;
import org.primefaces.model.UploadedFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.table.DefaultTableModel;
import java.net.InetAddress;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 16.03.17.
 */
public interface EndpointInterface {
    SecPolicyBean getSecPolicyBean();

    boolean createLiveBranch(String ifsName, String byUser, String branchName, String ip);

    Integer createBranch(String pathToFile, String byUser, String fileName, String branchName);

//    Integer createBranch(String byUser, String pcapPath, String branchName);

    void startBranch(Integer brId);

    void removeObserver(Integer brId, String obj);


    void stopBranch(Integer brId);

    FileListBean getDir(Integer brId, String sorsPath);

    void delete(Integer brId, String sorsPath);

    DefaultTableModel getBaseTModel(Integer brId);

    TimeSeries getDataInChart(Integer brId);

    TimeSeries getDataOutChart(Integer brId, Long timeFrom, Long timeTo);

    TimeSeries getNetworkDataInChart(Integer brId, String netStr, Long timeFrom, Long timeTo);

    TimeSeries getNetworkDataOutChart(Integer brId, String netStr);

    TimeSeries getIpDataInChart(Integer brId, String ipStr);

    TimeSeries getIpDataOutChart(Integer brId, String ipStr);

    TimeSeries getSesDstDataChart(Integer brId, String network, Long sesId);

    TimeSeries getSesSrcDataChart(Integer brId, String network, Long sesId);

    DefaultTableModel getSubnetTModel(Integer brId, String subnet);

    DefaultTableModel getIpTModel(Integer brId, String ip);

    void updateUnid(Long unid);

    List<String> getIfsList(String byUser);

    SessionDataBean getSessionData(Integer brId, String net, Long tm);

    void getFile(Integer brId, String sorsPath, HttpServletResponse response);

    SubnetBeanList getSubnetBeanList(Integer brId);

    SesDataCapBean getSesDataCaptureInfo(String target, Integer brId);

    InfoBean getInfo();

    boolean recoverSessionData(Integer brID);

    boolean setCapture(Integer brId, String obj, String in, String out);

    BranchLightBean getBranchLight(Integer id);

    IPItemBean getIpItemBean(Integer id, String ip, boolean observe);

    SubnetLightBean getSubnetLight(Integer id, String subnet, boolean observe);

    BranchBean getBranch(Integer id);

    void loginIncorrect();

    Set<BranchInfoBean> getBranchInfoSet(String byUser);

    BranchInfoBean getBranchInfo(String byUser, Integer brid);

    BranchStatBean getBranchStat(String byUser);

    void setInfo(InfoBean info);

    String getOwner();

    void setOwner(String owner);

    String getNodeName();

    void setNodeName(String nodeName);

    Long getUnid();

    void setUnid(Long unid);

    InetAddress getAddr();

    void setAddr(InetAddress addr);

    ConnectionHandler getCh();

    void setCh(ConnectionHandler ch);
}
