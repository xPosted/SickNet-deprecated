package com.jubaka.sors.entities;


import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by root on 28.08.16.
 */
@Entity
public class Node {

    @Id
    private Long unid;
    private String nodeName;
    private String owner;
    private String description;
    private String osArch;
    private Integer procCount;
    private Double maxMem;
    private Double homeMax;

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public Double getHomeMax() {
        return homeMax;
    }

    public void setHomeMax(Double homeMax) {
        this.homeMax = homeMax;
    }

    public Double getMaxMem() {
        return maxMem;
    }

    public void setMaxMem(Double maxMem) {
        this.maxMem = maxMem;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getProcCount() {
        return procCount;
    }

    public void setProcCount(Integer procCount) {
        this.procCount = procCount;
    }

    public Long getUnid() {
        return unid;
    }

    public void setUnid(Long unid) {
        this.unid = unid;
    }


}
