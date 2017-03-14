package com.jubaka.sors.managed;

import com.jubaka.sors.serverSide.ConnectionHandler;
import com.jubaka.sors.serverSide.NodeServerEndpoint;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        if (nodeId ==  null | upFilePart == null | taskName == null) return;

        NodeServerEndpoint nodeEndpoint =  cHandler.getNodeServerEndPoint(nodeId);
        nodeEndpoint.createBranch(loginBean.getLogin(),upFilePart,taskName);
    }
}
