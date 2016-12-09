package com.jubaka.sors.beans;

import com.jubaka.sors.serverSide.ConnectionHandler;

/**
 * Created by root on 07.12.16.
 */
public class Category {


    private Integer type;
    private String name;
    private String categoryDesc;
    private Long receivedFrom = (long) 0;
    private Long sendTo = (long) 0;
    private Integer sesFrom = 0;
    private Integer sesTo = 0;
    private Integer activeSes = 0;
    private String selectedHost = null;

    public String getSelectedHost() {
        return selectedHost;
    }

    public void setSelectedHost(String selectedHost) {
        this.selectedHost = selectedHost;
    }

    public Integer getActiveSes() {
        return activeSes;
    }

    public void setActiveSes(Integer activeSes) {
        this.activeSes = activeSes;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getReceivedFrom() {
        return receivedFrom;
    }

    public void setReceivedFrom(Long receivedFrom) {
        this.receivedFrom = receivedFrom;
    }

    public Long getSendTo() {
        return sendTo;
    }

    public void setSendTo(Long sendTo) {
        this.sendTo = sendTo;
    }

    public Integer getSesFrom() {
        return sesFrom;
    }

    public void setSesFrom(Integer sesFrom) {
        this.sesFrom = sesFrom;
    }

    public Integer getSesTo() {
        return sesTo;
    }

    public void setSesTo(Integer sesTo) {
        this.sesTo = sesTo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReceivedFromStr() {
        return ConnectionHandler.processSize(receivedFrom,2);
    }

    public String getSendToStr() {
        return ConnectionHandler.processSize(sendTo,2);
    }




}
