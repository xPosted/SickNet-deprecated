package com.jubaka.sors.appserver.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by root on 28.08.16.
 */

@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;
    private String branchName;
    private String fileName;
    private Long fileSize;
    private String iface;
    private Timestamp createtime;
    private Timestamp modifyTime;
    private String description;
    private Integer state= 0;
    private Integer subnet_count =0;
    private Integer hosts_count = 0;
    private Integer sessions_count =0;
    private String  recoveredDataPath;

    @ManyToOne
    @JoinColumn(name = "nodeId")
    private Node node;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Subnet> subntes;

    // make subnet list here ... security(node history view)

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getDbid() {
        return dbid;
    }

    public void setId(Long dbid) {
        this.dbid = dbid;
    }

    public String getIface() {
        return iface;
    }

    public void setIface(String iface) {
        this.iface = iface;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Subnet> getSubntes() {
        return subntes;
    }

    public void setSubntes(Set<Subnet> subntes) {
        this.subntes = subntes;
    }

    public Integer getHosts_count() {
        return hosts_count;
    }

    public void setHosts_count(Integer hosts_count) {
        this.hosts_count = hosts_count;
    }

    public Integer getSessions_count() {
        return sessions_count;
    }

    public void setSessions_count(Integer sessions_count) {
        this.sessions_count = sessions_count;
    }

    public Integer getSubnet_count() {
        return subnet_count;
    }

    public void setSubnet_count(Integer subnet_count) {
        this.subnet_count = subnet_count;
    }


    public String getRecoveredDataPath() {
        return recoveredDataPath;
    }

    public void setRecoveredDataPath(String recoveredDataPath) {
        this.recoveredDataPath = recoveredDataPath;
    }



}
