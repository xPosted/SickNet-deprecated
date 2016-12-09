package com.jubaka.sors.managed;

import com.jubaka.sors.beans.branch.BranchInfoBean;
import com.jubaka.sors.entities.Branch;
import com.jubaka.sors.serverSide.ConnectionHandler;
import com.jubaka.sors.serverSide.NodeServerEndpoint;
import com.jubaka.sors.serverSide.SecurityVisor;
import com.jubaka.sors.service.BranchService;
import com.jubaka.sors.service.NodeService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 28.08.16.
 */

@Named
@RequestScoped
public class TaskListBean {

    @Inject
    private LoginBean loginBean;

    @Inject
    private NodeService nodeService;
    @Inject
    private BranchService branchService;

    private Set<BranchInfoBean> tasks;

    private List<BranchInfoBean> dbTasks;
    private Integer ownCount =0;
    private Integer liveCount=0;
    private Integer nodesCount;
    private Integer inMemoryCount=0;

    private Integer databaseCount=0;

    @PostConstruct
    public void init() {

        tasks = new HashSet<BranchInfoBean>();
        String user = loginBean.getUser().getNickName();

        List<NodeServerEndpoint> nodeServerEndpoints = nodeService.getConnectedNodeEndPointsByUser();
        ownCount = 0;
        liveCount = 0;
        nodesCount = nodeServerEndpoints.size();
        for (NodeServerEndpoint n : nodeServerEndpoints) {
            for (BranchInfoBean bib : n.getBranchInfoSet(user)) {
                tasks.add(bib);
                System.out.println(bib.getUserName()+" "+user);
                if (bib.getUserName().equals(user)) ownCount++;
                if (bib.getIface()!=null) liveCount++;
            }

        }
        dbTasks = new ArrayList<>();
        List<Branch> dbBranchs = branchService.selectByCurrentUser();
        for (Branch b : dbBranchs) {
            BranchInfoBean bib = branchService.castToInfoBean(b);
            dbTasks.add(bib);
        }


        inMemoryCount = tasks.size();
        databaseCount =  dbTasks.size();

    }

    public String getTaskType(BranchInfoBean bib) {
        if (bib.getIface() == null) return "Pcap file";
        else return "Live capture";
    }

    public String sizeToStr(double size,Integer afterDot) {
        return ConnectionHandler.processSize(size,afterDot);

    }


    public List<BranchInfoBean> getTasks() {
        return new ArrayList<>(tasks);
    }


    public void setTasks(Set<BranchInfoBean> tasks) {
        this.tasks = tasks;
    }


    public Integer getOwnCount() {
        return ownCount;
    }

    public void setOwnCount(Integer ownCount) {
        this.ownCount = ownCount;
    }

    public Integer getLiveCount() {
        return liveCount;
    }

    public void setLiveCount(Integer liveCount) {
        this.liveCount = liveCount;
    }

    public Integer getNodesCount() {
        return nodesCount;
    }

    public void setNodesCount(Integer nodesCount) {
        this.nodesCount = nodesCount;
    }

    public Integer getInMemoryCount() {
        return inMemoryCount;
    }

    public void setInMemoryCount(Integer inMemoryCount) {
        this.inMemoryCount = inMemoryCount;
    }

    public List<BranchInfoBean> getDbTasks() {
        return dbTasks;
    }

    public void setDbTasks(List<BranchInfoBean> dbTasks) {
        this.dbTasks = dbTasks;
    }

    public Integer getDatabaseCount() {
        return databaseCount;
    }

    public void setDatabaseCount(Integer databaseCount) {
        this.databaseCount = databaseCount;
    }

}
