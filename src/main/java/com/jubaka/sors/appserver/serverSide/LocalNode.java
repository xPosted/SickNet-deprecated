package com.jubaka.sors.appserver.serverSide;

import com.jubaka.sors.beans.*;
import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.desktop.factories.ClassFactory;
import com.jubaka.sors.desktop.remote.BeanConstructor;
import com.jubaka.sors.desktop.sessions.API;
import com.jubaka.sors.desktop.sessions.Branch;
import org.jfree.data.time.TimeSeries;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 16.03.17.
 */

public class LocalNode implements EndpointInterface {
    private ClassFactory localFactory;
    private BeanConstructor constructor;

    public LocalNode(AuthorisationBean authorisation, String home,String desc) {
        localFactory = ClassFactory.getStandaloneInstance(home, authorisation.getNodeName(),desc);
    }

    public void waitForCaptureOff(Integer branchId) {
        API api =  localFactory.getAPIinstance(branchId);
        api.waitForCaptureOff();

    }

    @Override
    public SecPolicyBean getSecPolicyBean() {
        return null;
    }

    @Override
    public boolean createLiveBranch(String ifsName, String byUser, String branchName, String ip) {
        return false;
    }

    @Override
    public Integer createBranch(String pathToFile, String byUser, String fileName, String branchName) {
        return -1;
    }

    @Override
    public Integer createBranch(String byUser, Part filePart, String branchName) {
        String dumpPath = localFactory.getHome()+ File.separator+filePart.getSubmittedFileName();
        try {

        Files.copy(filePart.getInputStream(),Paths.get(dumpPath), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException io) {
        io.printStackTrace();
    }


        int branchId = localFactory.createBranch(byUser,branchName,dumpPath,null,null);
        Branch newBr = localFactory.getBranch(branchId);
        newBr.startCapture(null);
        return branchId;

    }

    @Override
    public void startBranch(Integer brId) {

    }

    @Override
    public void removeObserver(Integer brId, String obj) {

    }

    @Override
    public void stopBranch(Integer brId) {

    }

    @Override
    public FileListBean getDir(Integer brId, String sorsPath) {
        return null;
    }

    @Override
    public void delete(Integer brId, String sorsPath) {

    }

    @Override
    public DefaultTableModel getBaseTModel(Integer brId) {
        return null;
    }

    @Override
    public TimeSeries getDataInChart(Integer brId) {
        return null;
    }

    @Override
    public TimeSeries getDataOutChart(Integer brId, Long timeFrom, Long timeTo) {
        return null;
    }

    @Override
    public TimeSeries getNetworkDataInChart(Integer brId, String netStr, Long timeFrom, Long timeTo) {
        return null;
    }

    @Override
    public TimeSeries getNetworkDataOutChart(Integer brId, String netStr) {
        return null;
    }

    @Override
    public TimeSeries getIpDataInChart(Integer brId, String ipStr) {
        return null;
    }

    @Override
    public TimeSeries getIpDataOutChart(Integer brId, String ipStr) {
        return null;
    }

    @Override
    public TimeSeries getSesDstDataChart(Integer brId, String network, Long sesId) {
        return null;
    }

    @Override
    public TimeSeries getSesSrcDataChart(Integer brId, String network, Long sesId) {
        return null;
    }

    @Override
    public DefaultTableModel getSubnetTModel(Integer brId, String subnet) {
        return null;
    }

    @Override
    public DefaultTableModel getIpTModel(Integer brId, String ip) {
        return null;
    }

    @Override
    public void updateUnid(Long unid) {

    }

    @Override
    public List<String> getIfsList(String byUser) {
        return null;
    }

    @Override
    public SessionDataBean getSessionData(Integer brId, String net, Long tm) {
        return null;
    }

    @Override
    public void getFile(Integer brId, String sorsPath, HttpServletResponse response) {

    }

    @Override
    public SubnetBeanList getSubnetBeanList(Integer brId) {
        return null;
    }

    @Override
    public SesDataCapBean getSesDataCaptureInfo(String target, Integer brId) {
        return null;
    }

    @Override
    public InfoBean getInfo() {

        return constructor.prepareInfoBean(localFactory);
    }

    @Override
    public boolean recoverSessionData(Integer brID) {
        return false;
    }

    @Override
    public boolean setCapture(Integer brId, String obj, String in, String out) {
        return false;
    }

    @Override
    public BranchLightBean getBranchLight(Integer id) {
        return null;
    }

    @Override
    public IPItemBean getIpItemBean(Integer id, String ip, boolean observe) {
        return null;
    }

    @Override
    public SubnetLightBean getSubnetLight(Integer id, String subnet, boolean observe) {
        return null;
    }

    @Override
    public BranchBean getBranch(Integer id) {

        Branch br = localFactory.getBranch(id);
        BeanConstructor constructor = new BeanConstructor();
        BranchBean brBean =  constructor.prepareBranchBean(br);
        return brBean;
    }

    @Override
    public void loginIncorrect() {

    }

    @Override
    public Set<BranchInfoBean> getBranchInfoSet(String byUser) {
        return null;
    }

    @Override
    public BranchInfoBean getBranchInfo(String byUser, Integer brid) {
        return null;
    }

    @Override
    public BranchStatBean getBranchStat(String byUser) {
        return null;
    }

    @Override
    public void setInfo(InfoBean info) {

    }

    @Override
    public String getOwner() {
        return null;
    }

    @Override
    public void setOwner(String owner) {

    }

    @Override
    public String getNodeName() {
        return null;
    }

    @Override
    public void setNodeName(String nodeName) {

    }

    @Override
    public Long getUnid() {
        return null;
    }

    @Override
    public void setUnid(Long unid) {

    }

    @Override
    public InetAddress getAddr() {
        return null;
    }

    @Override
    public void setAddr(InetAddress addr) {

    }

    @Override
    public ConnectionHandler getCh() {
        return null;
    }

    @Override
    public void setCh(ConnectionHandler ch) {

    }
}
