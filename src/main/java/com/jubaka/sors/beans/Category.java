package com.jubaka.sors.beans;

import com.jubaka.sors.beans.branch.SessionBean;
import com.jubaka.sors.serverSide.ConnectionHandler;
import com.jubaka.sors.serverSide.SmartFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private SmartFilter parentType;
    private List<Category> subCategories = new ArrayList<>();
    private List<SessionBean> sessionList;

    public Category(SmartFilter parentType) {
        this.parentType = parentType;
    }


    public SmartFilter getParentType() {
        return parentType;
    }
    public List<SessionBean> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<SessionBean> sessionList) {
        this.sessionList = Collections.synchronizedList(sessionList);
    }


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

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = Collections.synchronizedList(subCategories);
    }

    public boolean checkForEquals(Category cat) {
        if (cat.getName().equals(this.name)){
            sessionList.addAll(cat.getSessionList());
            synchronized (subCategories) {
                for (Category subCat : subCategories) {
                    for (Category subNewCat : cat.subCategories) {
                        subCat.checkForEquals(subNewCat);
                    }
                }
                concat(cat);
            }

            return true;
        }
        return false;

    }

    private void concat(Category someCat) {
        this.activeSes = this.activeSes + someCat.activeSes;
        this.sesTo = this.sesTo + someCat.getSesTo();
        this.sesFrom = this.sesFrom + someCat.getSesFrom();
        this.sendTo = this.sendTo + someCat.getSendTo();
        this.receivedFrom = this.receivedFrom + someCat.getReceivedFrom();
    }







}
