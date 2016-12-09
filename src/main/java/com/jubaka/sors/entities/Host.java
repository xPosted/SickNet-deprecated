package com.jubaka.sors.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 28.08.16.
 */

@Entity
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ip;
    private String dnsName;
    private Long dataDown;
    private Long dataUp;
    private Integer inputCount=0;
    private Integer outputCount=0;
    private Integer activeCount=0;
    private Integer savedCount=0;
    private Integer inputActiveCount=0;
    private Integer outputActiveCount=0;

    @ManyToOne
    @JoinColumn(name="subnetId")
    private Subnet subnet;

    @OneToMany(mappedBy = "dstHost", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Session> sessionsInput;

    @OneToMany(mappedBy = "srcHost", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Session> sessionsOutput;


    public Integer getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(Integer activeCount) {
        this.activeCount = activeCount;
    }


    public Long getDataDown() {
        return dataDown;
    }

    public void setDataDown(Long dataDown) {
        this.dataDown = dataDown;
    }

    public Long getDataUp() {
        return dataUp;
    }

    public void setDataUp(Long dataUp) {
        this.dataUp = dataUp;
    }

    public String getDnsName() {
        return dnsName;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInputCount() {
        return inputCount;
    }

    public void setInputCount(Integer inputCount) {
        this.inputCount = inputCount;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(Integer outputCount) {
        this.outputCount = outputCount;
    }

    public Integer getSavedCount() {
        return savedCount;
    }

    public void setSavedCount(Integer savedCount) {
        this.savedCount = savedCount;
    }

    public Subnet getSubnet() {
        return subnet;
    }

    public void setSubnet(Subnet subnet) {
        this.subnet = subnet;
    }

    public List<Session> getSessionsInput() {
        return sessionsInput;
    }

    public void setSessionsInput(List<Session> sessionsInput) {
        this.sessionsInput = sessionsInput;
    }

    public List<Session> getSessionsOutput() {
        return sessionsOutput;
    }

    public void setSessionsOutput(List<Session> sessionsOutput) {
        this.sessionsOutput = sessionsOutput;
    }

    public Integer getInputActiveCount() {
        return inputActiveCount;
    }

    public void setInputActiveCount(Integer inputActiveCount) {
        this.inputActiveCount = inputActiveCount;
    }

    public Integer getOutputActiveCount() {
        return outputActiveCount;
    }

    public void setOutputActiveCount(Integer outputActiveCount) {
        this.outputActiveCount = outputActiveCount;
    }






}
