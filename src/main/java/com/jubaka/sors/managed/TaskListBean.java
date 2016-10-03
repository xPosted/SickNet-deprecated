package com.jubaka.sors.managed;

import com.jubaka.sors.beans.branch.BranchInfoBean;
import com.jubaka.sors.serverSide.ConnectionHandler;
import com.jubaka.sors.serverSide.Node;
import com.jubaka.sors.serverSide.SecurityVisor;

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
    private Set<BranchInfoBean> tasks;
    private Integer ownCount =0;
    private Integer liveCount=0;
    private Integer nodesCount;
    private Integer totalCount;

    @PostConstruct
    public void init() {
        tasks = new HashSet<BranchInfoBean>();
        String user = loginBean.getUser().getNickName();
        SecurityVisor sv = ConnectionHandler.getInstance().getSv();
        Set<Node> nodes  = sv.getNodes(user);
        ownCount = 0;
        liveCount = 0;
        nodesCount = nodes.size();
        for (Node n : nodes) {
            for (BranchInfoBean bib : n.getBranchInfoSet(user)) {
                tasks.add(bib);
                System.out.println(bib.getUserName()+" "+user);
                if (bib.getUserName().equals(user)) ownCount++;
                if (bib.getIface()!=null) liveCount++;
            }

        }
        totalCount = tasks.size();

    }

    public String getTaskType(BranchInfoBean bib) {
        if (bib.getIface() == null) return "Pcap file";
        else return "Live";
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

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }



}
