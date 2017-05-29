package com.jubaka.sors.appserver.managed;

import com.jubaka.sors.appserver.entities.Branch;
import com.jubaka.sors.appserver.service.BeanEntityConverter;
import com.jubaka.sors.appserver.service.BranchService;
import com.jubaka.sors.beans.branch.BranchLightBean;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by root on 28.05.17.
 */

@Named
@SessionScoped
public class RecoverViewBean implements Serializable {

    @Inject
    private BranchService branchService;

    private String path;


    public void init() {
        ExternalContext externalContext =  FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> params = externalContext.getRequestParameterMap();
        String dbIdStr = params.get("dbid");
        String path = params.get("path");
        long dbId = Long.parseLong(dbIdStr);
        Branch brEntity = branchService.selectById(dbId);
        BranchLightBean blb = BeanEntityConverter.castToLightBean(null,brEntity);
        String basePath = blb.getBib().getRecoveredDataPath();
        if (path == null) path = "/";


    }
}
