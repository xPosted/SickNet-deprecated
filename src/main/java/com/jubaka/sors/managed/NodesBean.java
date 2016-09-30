package com.jubaka.sors.managed;

import com.jubaka.sors.beans.InfoBean;
import com.jubaka.sors.entities.User;
import com.jubaka.sors.serverSide.ConnectionHandler;
import com.jubaka.sors.serverSide.Node;
import com.jubaka.sors.serverSide.SecurityVisor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
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
    private Set<Node> nodes = new HashSet<>();

    private List<InfoBean> infoBeans = new ArrayList<>();


    @PostConstruct
    public void init() {
        System.out.println("NodesBean init");
        ExternalContext externalContext =  FacesContext.getCurrentInstance().getExternalContext();
        SecurityVisor sv =  ConnectionHandler.getInstance().getSv();
        Map<String,String> params = externalContext.getRequestParameterMap();
        String pubStr = params.get("pub");
        if (pubStr == null) pubStr="false";
        boolean shared = Boolean.getBoolean(params.get("pub"));
        if (shared == true) nodes = sv.getNodes("*");
        else
            nodes = sv.getNodes(loginBean.getUser().getNickName());
        for (Node n : nodes) {
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
