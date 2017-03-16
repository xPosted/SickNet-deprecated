package com.jubaka.sors.appserver.managed;

import com.jubaka.sors.beans.InfoBean;
import com.jubaka.sors.appserver.serverSide.NodeServerEndpoint;
import com.jubaka.sors.appserver.service.NodeActiveCheckPointService;
import com.jubaka.sors.appserver.service.NodeService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * Created by root on 28.08.16.
 */

@Named
@RequestScoped
public class NodesBean implements Serializable {

    @Inject
    private LoginBean loginBean;
    @Inject
    private NodeActiveCheckPointService activeCheckPointService;
    @Inject
    private NodeService nodeService;
    private List<NodeServerEndpoint> nodeServerEndpoints = new ArrayList<>();

    private List<InfoBean> infoBeans = new ArrayList<>();


    @PostConstruct
    public void init() {
        System.out.println("NodesBean init");
        ExternalContext externalContext =  FacesContext.getCurrentInstance().getExternalContext();
        Map<String,String> params = externalContext.getRequestParameterMap();
        String pubStr = params.get("pub");

      //  boolean shared = Boolean.getBoolean(params.get("pub"));
      //  if (shared == true) nodeServerEndpoints = sv.getNodes("*");

        nodeServerEndpoints = nodeService.getConnectedNodeEndPointsByUser();
        for (NodeServerEndpoint n : nodeServerEndpoints) {
            infoBeans.add(n.getInfo());
        }

    }

    public List<InfoBean> getInfoBeans() {
        return infoBeans;
    }

    public void setInfoBeans(List<InfoBean> infoBeans) {
        this.infoBeans = infoBeans;
    }
}
