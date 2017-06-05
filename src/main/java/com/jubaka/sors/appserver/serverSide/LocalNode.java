package com.jubaka.sors.appserver.serverSide;

import com.jubaka.sors.beans.*;
import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.desktop.factories.ClassFactory;
import com.jubaka.sors.desktop.remote.BeanConstructor;
import com.jubaka.sors.desktop.sessions.API;
import com.jubaka.sors.desktop.sessions.Branch;
import com.jubaka.sors.desktop.sessions.SessionsAPI;
import org.jfree.data.time.TimeSeries;
import org.primefaces.model.UploadedFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 16.03.17.
 */

public class LocalNode implements EndpointInterface {
    private ClassFactory localFactory;
    private BeanConstructor constructor = new BeanConstructor();
    private Date creationTime = new Date();

    public LocalNode(AuthorisationBean authorisation, String home,String desc) {
        localFactory = ClassFactory.getStandaloneInstance(home, authorisation.getNodeName(),desc);
    }

    public void waitForCaptureOff(Integer branchId) {
        API api =  localFactory.getAPIinstance(branchId);
        api.waitForCaptureOff();

    }

    public void removeFromMem(Integer brId) {
        localFactory.removeFromMem(brId);
    }

    public void deleteRawData(Integer id) {
        String rawPath = localFactory.getRawDataPath(id);
        deleteDir(new File(rawPath));
    }

    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (item.isDirectory())
                    deleteDir(item);
                if (item.isFile())
                    item.delete();
            }
        }
        dir.delete();
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
        int branchId = localFactory.createBranch(byUser,branchName,pathToFile,null,null);
        Branch newBr = localFactory.getBranch(branchId);
        newBr.startCapture(null);
        return branchId;
    }

    public String preparePcap(UploadedFile upload) {
        String dumpPath = localFactory.getHome()+ File.separator+upload.getFileName();
        try {

            Files.copy(upload.getInputstream(),Paths.get(dumpPath), StandardCopyOption.REPLACE_EXISTING);
            return dumpPath;
        } catch (IOException io) {
            io.printStackTrace();
        }
        return null;

    }
/*
    @Override
    public Integer createBranch(String byUser, String pcapPath, String branchName) {


        int branchId = localFactory.createBranch(byUser,branchName,pcapPath,null,null);
        Branch newBr = localFactory.getBranch(branchId);
        newBr.startCapture(null);
        return branchId;

    }
*/
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

        InfoBean info = constructor.prepareInfoBean(localFactory);
        info.setConnectedDate(creationTime);
        try {
            info.setPubAddr(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return info;
    }

    @Override
    public boolean recoverSessionData(Integer brID) {
        SessionsAPI sApi = localFactory.getSesionInstance(brID);
        sApi.dataRecovering();
        return true;
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
