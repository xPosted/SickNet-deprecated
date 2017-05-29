package com.jubaka.sors.appserver.managed;

import com.jubaka.sors.appserver.serverSide.EndpointInterface;
import com.jubaka.sors.appserver.serverSide.LocalNode;
import com.jubaka.sors.appserver.servlet.Profile;
import com.jubaka.sors.beans.branch.BranchBean;
import com.jubaka.sors.desktop.factories.ClassFactory;
import com.jubaka.sors.desktop.remote.BeanConstructor;
import com.jubaka.sors.appserver.serverSide.ConnectionHandler;
import com.jubaka.sors.appserver.service.BranchService;
import com.jubaka.sors.desktop.sessions.API;
import com.jubaka.sors.desktop.sessions.Branch;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by root on 14.02.17.
 */

@Named
@SessionScoped
public class CreateTaskBean implements Serializable {

    @Inject
    private ServerArgumentsBean args;
    @Inject
    private ConnectionHandler cHandler;
    @Inject
    private LoginBean loginBean;
    @Inject
    private ProfileBaen profileBaen;
    @Inject
    private BranchService branchService;
    private String taskName;
    private String nodeName;
    private Long nodeId;
    private UploadedFile upFilePart;
    private File uploadedPcap = null;

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

    public UploadedFile getUpFilePart() {
        return upFilePart;
    }

    public void setUpFilePart(UploadedFile upFilePart) {

        this.upFilePart = upFilePart;
        LocalNode endpoint =  cHandler.getLocalNode();
        uploadedPcap  = new File(endpoint.preparePcap(upFilePart));

    }

    public void upload() {
        FacesMessage msgCreate = new FacesMessage(FacesMessage.SEVERITY_INFO, "Task Info", "New task will be available in several minutes!");
        FacesContext.getCurrentInstance().addMessage(null, msgCreate);
        createTask();
        FacesMessage msgReady = new FacesMessage(FacesMessage.SEVERITY_INFO, "Task Info", "New Task is available for view!");
        FacesContext.getCurrentInstance().addMessage(null, msgReady);
    }

    public void handleFileUpload(FileUploadEvent event) {
        upFilePart = event.getFile();
        LocalNode endpoint =  cHandler.getLocalNode();
        uploadedPcap = new File(endpoint.preparePcap(upFilePart));
    }
    private void createTask() {

        if (upFilePart == null | taskName == null) return;
        if (cHandler.getLocalNode() == null) return;
        if (uploadedPcap.length() > profileBaen.getAvailableTaskSpace()) return;

        LocalNode endpoint =  cHandler.getLocalNode();
        Integer newBrId =  endpoint.createBranch(uploadedPcap.getAbsolutePath(),loginBean.getLogin(),upFilePart.getFileName(),taskName);
        endpoint.waitForCaptureOff(newBrId);
        endpoint.recoverSessionData(newBrId);
        BranchBean bb = endpoint.getBranch(newBrId);
        branchService.persistBranch(bb);
        endpoint.removeFromMem(newBrId);

        /*
        BeanConstructor beanConstructor = new BeanConstructor();
        String usersHome = args.getUploadPath() +File.separator+ loginBean.getLogin()+File.separator;
        ClassFactory fuckingFactory = ClassFactory.getStandaloneInstance(usersHome);
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
          //  BranchBean bBean = beanConstructor.prepareBranchBean(branchId);
           // com.jubaka.sors.appserver.entities.Branch persistedBranch = branchService.persistBranch(bBean);

        } catch (IOException io) {
            io.printStackTrace();
        }


        //NodeServerEndpoint nodeEndpoint =  cHandler.getNodeServerEndPoint(nodeId);
        //nodeEndpoint.createBranch(loginBean.getLogin(),upFilePart,taskName);
*/

    }

    public boolean isUploaded() {
        if (upFilePart!=null) return true;
        return false;
    }
    public String getUploadedFileName() {
        if (upFilePart != null) {
            return upFilePart.getFileName();
        }
        return "[Uploaded files not found]";
    }
}
