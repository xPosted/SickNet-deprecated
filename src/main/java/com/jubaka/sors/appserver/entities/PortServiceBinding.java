package com.jubaka.sors.appserver.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by root on 24.12.16.
 */

@Entity
public class PortServiceBinding {

    @Id
    public Integer portNumber;
    public String serviceName;

    public Integer getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(Integer portNumber) {
        this.portNumber = portNumber;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


}
