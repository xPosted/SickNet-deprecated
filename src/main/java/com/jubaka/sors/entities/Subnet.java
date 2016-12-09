package com.jubaka.sors.entities;

import javax.persistence.*;
import java.net.InetAddress;
import java.util.List;

/**
 * Created by root on 26.10.16.
 */

@Entity
public class Subnet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long dataSend = (long) 0;
    private Long dataReceive = (long) 0;
    private Integer activeSesCnt=0;
    private Integer activeAddrCnt=0;
    private Integer activeInSesCnt = 0;
    private Integer activeOutSesCnt = 0;
    private Integer inSesCnt = 0;
    private Integer outSesCnt = 0;

    private Integer sesCnt=0;
    private Integer addrCnt=0;
    private String subnet;
    private int subnetMask;

    @ManyToOne
    @JoinColumn(name = "branchId")
    private Branch branch;

    @OneToMany(mappedBy = "subnet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Host> hosts;



    public Integer getActiveAddrCnt() {
        return activeAddrCnt;
    }

    public void setActiveAddrCnt(Integer activeAddrCnt) {
        this.activeAddrCnt = activeAddrCnt;
    }

    public Integer getActiveSesCnt() {
        return activeSesCnt;
    }

    public void setActiveSesCnt(Integer activeSesCnt) {
        this.activeSesCnt = activeSesCnt;
    }

    public Integer getAddrCnt() {
        return addrCnt;
    }

    public void setAddrCnt(Integer addrCnt) {
        this.addrCnt = addrCnt;
    }


    public Long getDataReceive() {
        return dataReceive;
    }

    public void setDataReceive(Long dataReceive) {
        this.dataReceive = dataReceive;
    }

    public Long getDataSend() {
        return dataSend;
    }

    public void setDataSend(Long dataSend) {
        this.dataSend = dataSend;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSesCnt() {
        return sesCnt;
    }

    public void setSesCnt(Integer sesCnt) {
        this.sesCnt = sesCnt;
    }

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    public int getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(int subnetMask) {
        this.subnetMask = subnetMask;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public Integer getActiveInSesCnt() {
        return activeInSesCnt;
    }

    public void setActiveInSesCnt(Integer activeInSesCnt) {
        this.activeInSesCnt = activeInSesCnt;
    }

    public Integer getActiveOutSesCnt() {
        return activeOutSesCnt;
    }

    public void setActiveOutSesCnt(Integer activeOutSesCnt) {
        this.activeOutSesCnt = activeOutSesCnt;
    }

    public Integer getInSesCnt() {
        return inSesCnt;
    }

    public void setInSesCnt(Integer inSesCnt) {
        this.inSesCnt = inSesCnt;
    }

    public Integer getOutSesCnt() {
        return outSesCnt;
    }

    public void setOutSesCnt(Integer outSesCnt) {
        this.outSesCnt = outSesCnt;
    }




}
