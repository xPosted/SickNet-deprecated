package com.jubaka.sors.appserver.managed;

import com.jubaka.sors.appserver.serverSide.EndpointInterface;
import com.jubaka.sors.appserver.serverSide.LocalNode;
import com.jubaka.sors.appserver.servlet.Profile;
import com.jubaka.sors.beans.branch.BranchBean;
import com.jubaka.sors.beans.branch.BranchInfoBean;
import com.jubaka.sors.desktop.factories.ClassFactory;
import com.jubaka.sors.desktop.remote.BeanConstructor;
import com.jubaka.sors.appserver.serverSide.ConnectionHandler;
import com.jubaka.sors.appserver.service.BranchService;
import com.jubaka.sors.desktop.sessions.API;
import com.jubaka.sors.desktop.sessions.Branch;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PreDestroy;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Inject
    private TaskViewBean viewBean;


    private String taskName;
    private String nodeName;
    private Long nodeId;
    private UploadedFile upFilePart;
    private File uploadedPcap = null;
    private Set<BranchBean> onPersistingTask = new HashSet();
    private Set<Integer> localClearOnDestroy= new HashSet<>();




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
        FacesMessage msgReady = new FacesMessage(FacesMessage.SEVERITY_INFO, "Task Info", "New Task is available for view!");
        FacesContext.getCurrentInstance().addMessage(null, msgReady);
        BranchBean bb = createTask();
        onPersistingTask.add(bb);
        Thread persistThread = new Thread(new Runnable() {
            @Override
            public void run() {
                persistTask(bb);
                onPersistingTask.remove(bb);
                localClearOnDestroy.add(bb.getBib().getId());
            }
        });
        persistThread.start();

    }

    public void handleFileUpload(FileUploadEvent event) {
        upFilePart = event.getFile();
        LocalNode endpoint =  cHandler.getLocalNode();
        uploadedPcap = new File(endpoint.preparePcap(upFilePart));
    }

    public BranchBean viewNotReadyPersistedTask(Integer id) {
        for (BranchBean bb : onPersistingTask) {
            if (bb.getBib().getId() == id) return bb;
        }
        return null;
    }

    public List<BranchInfoBean> listNotAlreadyPersistedTask() {
        List<BranchInfoBean> result = new ArrayList<>();
        for (BranchBean bb : onPersistingTask) {
            result.add(bb.getBib());
        }
        return result;
    }

    private BranchBean createTask() {

        if (upFilePart == null | taskName == null) return null;
        if (cHandler.getLocalNode() == null) return null;
        if (uploadedPcap.length() > profileBaen.getAvailableTaskSpace()) return null;

        LocalNode endpoint =  cHandler.getLocalNode();
        Integer newBrId =  endpoint.createBranch(uploadedPcap.getAbsolutePath(),loginBean.getUser().getNickName(),upFilePart.getFileName(),taskName);
        endpoint.waitForCaptureOff(newBrId);
        endpoint.recoverSessionData(newBrId);
        BranchBean bb = endpoint.getBranch(newBrId);
        return bb;


        //endpoint.removeFromMem(newBrId); !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1

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

    public void persistTask(BranchBean bb) {
        branchService.persistBranch(bb);
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

    @PreDestroy
    public void onDestroy() {
        LocalNode endpoint =  cHandler.getLocalNode();
        for (Integer brId : localClearOnDestroy) {
           endpoint.deleteRawData(brId);
           endpoint.removeFromMem(brId);
        }
    }
}
