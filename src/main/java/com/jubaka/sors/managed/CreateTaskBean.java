package com.jubaka.sors.managed;

import com.jubaka.sors.beans.branch.BranchBean;
import com.jubaka.sors.factories.ClassFactory;
import com.jubaka.sors.remote.BeanConstructor;
import com.jubaka.sors.serverSide.ConnectionHandler;
import com.jubaka.sors.serverSide.NodeServerEndpoint;
import com.jubaka.sors.service.BranchService;
import com.jubaka.sors.sessions.API;
import com.jubaka.sors.sessions.Branch;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/**
 * Created by root on 14.02.17.
 */

@Named
@RequestScoped
public class CreateTaskBean {

    @Inject
    private ServerArgumentsBean args;
    @Inject
    private ConnectionHandler cHandler;
    @Inject
    private LoginBean loginBean;
    @Inject
    private BranchService branchService;
    private String taskName;
    private String nodeName;
    private Long nodeId;
    private Part upFilePart;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Part getUpFilePart() {
        return upFilePart;
    }

    public void setUpFilePart(Part upFilePart) {
        this.upFilePart = upFilePart;
    }

    public void upload() {
        createTask();
    }

    private void createTask() {
        if (upFilePart == null | taskName == null) return;

        BeanConstructor beanConstructor = new BeanConstructor();
        String usersHome = args.getUploadPath() +File.separator+ loginBean.getLogin()+File.separator;
        ClassFactory fuckingFactory = ClassFactory.getStandaloneInstance(usersHome,true);
        String usersPcapPath = usersHome+"pcaps"+File.separator;
        String dumpPath = usersPcapPath + upFilePart.getSubmittedFileName();

        try {
            Files.createDirectories(Paths.get(usersPcapPath));
            Files.copy(upFilePart.getInputStream(),Paths.get(dumpPath), StandardCopyOption.REPLACE_EXISTING);
            int branchId = fuckingFactory.createBranch(loginBean.getLogin(),taskName,dumpPath,null,null);
            Branch newBr = fuckingFactory.getBranch(branchId);
            newBr.startCapture(null);
            API api =  fuckingFactory.getAPIinstance(branchId);
            api.waitForCaptureOff();
            BranchBean bBean = beanConstructor.prepareBranchBean(branchId);
            com.jubaka.sors.entities.Branch persistedBranch = branchService.persistBranch(bBean);

        } catch (IOException io) {
            io.printStackTrace();
        }


        //NodeServerEndpoint nodeEndpoint =  cHandler.getNodeServerEndPoint(nodeId);
        //nodeEndpoint.createBranch(loginBean.getLogin(),upFilePart,taskName);


    }
}
