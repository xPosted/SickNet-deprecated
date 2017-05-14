package com.jubaka.sors.appserver.managed;

import com.jubaka.sors.appserver.entities.Branch;
import com.jubaka.sors.appserver.entities.User;
import com.jubaka.sors.appserver.serverSide.ConnectionHandler;
import com.jubaka.sors.appserver.service.BranchService;
import com.jubaka.sors.beans.branch.BranchBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by root on 15.04.17.
 */

@Named
@RequestScoped
public class ProfileBaen {
    @Inject
    private LoginBean loginBean;

    @Inject
    private BranchService branchService;

        // this values left here becouse of request scoped bean makes smaller overload on db
    private int totalTasksCount = 0;
    private long totalSize = 0;
    private int totalNetCount = 0;
    private int totalHostCount = 0;
    private int totalSesCount = 0;

    @PostConstruct
    public void init() {
        List<Branch> branchs = branchService.selectByCurrentUser();
        for (Branch b : branchs) {
            totalTasksCount++;
            totalSize += b.getFileSize();
            totalNetCount+=b.getSubnet_count();
            totalHostCount+=b.getHosts_count();
            totalSesCount+=b.getSessions_count();
        }

    }

    public int getTotalTasksCount() {
        return totalTasksCount;
    }

    public void setTotalTasksCount(int totalTasks) {
        this.totalTasksCount = totalTasks;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalNetCount() {
        return totalNetCount;
    }

    public void setTotalNetCount(int totalNetCount) {
        this.totalNetCount = totalNetCount;
    }

    public int getTotalHostCount() {
        return totalHostCount;
    }

    public void setTotalHostCount(int totalHostCount) {
        this.totalHostCount = totalHostCount;
    }

    public int getTotalSesCount() {
        return totalSesCount;
    }

    public void setTotalSesCount(int totalSesCount) {
        this.totalSesCount = totalSesCount;
    }

    public String getTotalHumanSize() {
        return ConnectionHandler.processSize(totalSize,1);
    }

    public String getTaskSizeUserLimitInHuman() {
        return ConnectionHandler.processSize(loginBean.getUser().getLimits().getTotalTasksLen(),1);
    }

    public long getAvailableTaskSpace() {
        long maxTasksLen = loginBean.getUser().getLimits().getTotalTasksLen();
        maxTasksLen = maxTasksLen - getTotalSize();
        if (maxTasksLen<0) maxTasksLen = 0;
        return maxTasksLen;

    }

    public String getAvailableSpaceInHumanSize() {
        return ConnectionHandler.processSize(getAvailableTaskSpace(),0);
    }

    public String getAvailableSpaceInMb() {
       Double sizeInMb = ConnectionHandler.processSize(getAvailableTaskSpace(),"b","mb");
       return sizeInMb.toString();
    }

    public String getTotalTaskLenInMb() {
        Double sizeInMb = ConnectionHandler.processSize(getTotalSize(),"b","mb");
        return sizeInMb.toString();
    }
}
