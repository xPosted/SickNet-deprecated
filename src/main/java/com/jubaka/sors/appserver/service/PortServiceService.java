package com.jubaka.sors.appserver.service;

import com.jubaka.sors.appserver.dao.PortServiceDao;
import com.jubaka.sors.appserver.entities.PortServiceBinding;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by root on 24.12.16.
 */

@Named
@ApplicationScoped
public class PortServiceService  {

    @Inject
    private PortServiceDao portServiceDao;

    public String getServiceName(Integer port) {
        PortServiceBinding portService = portServiceDao.selectById(port);
        if (portService == null) {
            return "unknown service";
        }
        return portService.getServiceName();

    }

}
