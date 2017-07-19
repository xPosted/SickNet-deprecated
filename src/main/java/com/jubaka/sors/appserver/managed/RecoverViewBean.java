package com.jubaka.sors.appserver.managed;

import com.jubaka.sors.appserver.entities.Branch;
import com.jubaka.sors.appserver.serverSide.ConnectionHandler;
import com.jubaka.sors.appserver.service.BeanEntityConverter;
import com.jubaka.sors.appserver.service.BranchService;
import com.jubaka.sors.appserver.service.SessionService;
import com.jubaka.sors.beans.DirectoryBean;
import com.jubaka.sors.beans.FileBean;
import com.jubaka.sors.beans.FileListBean;
import com.jubaka.sors.beans.branch.BranchLightBean;
import com.jubaka.sors.desktop.remote.BeanConstructor;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Map;

/**
 * Created by root on 28.05.17.
 */

@Named
@SessionScoped
public class RecoverViewBean implements Serializable {

    @Inject
    private BranchService branchService;
    @Inject
    private SessionService sessionService;
    @Inject
    private LoginBean loginBean;


    private DirectoryBean mainDir;

    private BranchLightBean currentBranchBean;

    private long currentDbId;


    private String path;


    public void init() {
        if (loginBean.getUser() == null) {
            loginBean.redirectToLogIn();
        }


        ExternalContext externalContext =  FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> params = externalContext.getRequestParameterMap();
        String dbIdStr = params.get("dbid");
        String path = params.get("path");
        long dbId = Long.parseLong(dbIdStr);
        currentDbId = dbId;
        Branch brEntity = branchService.selectByIdWithNets(dbId);
        currentBranchBean = BeanEntityConverter.castToLightBean(null,brEntity);
      //  String basePath = blb.getBib().getRecoveredDataPath();
        if (path == null) path = "/";
        BeanConstructor constructor = new BeanConstructor();
        FileListBean result = constructor.prepareFileListBean(currentBranchBean.getBib(),path,sessionService);
        mainDir = result.getMainDir();
    }
    public void fileDownload(FileBean fBean) {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();

            ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
            ec.setResponseContentType("multipart/form-data"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
            ec.setResponseContentLength(fBean.getSize().intValue()); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fBean.getName() + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

            OutputStream output = ec.getResponseOutputStream();
            // Now you can write the InputStream of the file to the above OutputStream the usual way.
            // ...
            File file = new File(currentBranchBean.getBib().getRecoveredDataPath()+fBean.getFullPath());
            String fileName = file.getName();
            String contentType = ec.getMimeType(fileName); // JSF 1.x: ((ServletContext) ec.getContext()).getMimeType(fileName);
            int contentLength = (int) file.length();

// ...

            Files.copy(file.toPath(), output);
            fc.responseComplete(); // Important! Otherwise JSF will att


        } catch (IOException io) {
            io.printStackTrace();
        }

    }


    public DirectoryBean getMainDir() {
        return mainDir;
    }

    public void setMainDir(DirectoryBean mainDir) {
        this.mainDir = mainDir;
    }

    public long getCurrentDbId() {
        return currentDbId;
    }

    public void setCurrentDbId(long currentDbId) {
        this.currentDbId = currentDbId;
    }

    public String longToStr(Long size) {
        return ConnectionHandler.processSize(size,1);
    }

}
