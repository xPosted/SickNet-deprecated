package com.jubaka.sors.entities;

import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 28.08.16.
 */

@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "srcHostId")
    private Host srcHost;

    @ManyToOne
    @JoinColumn(name = "dstHostId")
    private Host dstHost;

    private Long srcDataLen = (long) 0;
    private Long dstDataLen = (long) 0;
    private Date established;
    private Date closed;
    private Long initSeq;
    private Integer srcP;
    private Integer dstP;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chartId")
    private SessionChart chartData;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<HttpRequest> requestList;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<HttpResponse> responseList;

    public Date getClosed() {
        return closed;
    }

    public void setClosed(Date closed) {
        this.closed = closed;
    }

    public Long getDstDataLen() {
        return dstDataLen;
    }

    public void setDstDataLen(Long dstDataLen) {
        this.dstDataLen = dstDataLen;
    }

    public Integer getDstP() {
        return dstP;
    }

    public void setDstP(Integer dstP) {
        this.dstP = dstP;
    }

    public Date getEstablished() {
        return established;
    }

    public void setEstablished(Date established) {
        this.established = established;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInitSeq() {
        return initSeq;
    }

    public void setInitSeq(Long initSeq) {
        this.initSeq = initSeq;
    }

    public Long getSrcDataLen() {
        return srcDataLen;
    }

    public void setSrcDataLen(Long srcDataLen) {
        this.srcDataLen = srcDataLen;
    }


    public Integer getSrcP() {
        return srcP;
    }

    public void setSrcP(Integer srcP) {
        this.srcP = srcP;
    }


    public Host getDstHost() {
        return dstHost;
    }

    public void setDstHost(Host dstHost) {
        this.dstHost = dstHost;
    }

    public Host getSrcHost() {
        return srcHost;
    }

    public void setSrcHost(Host srcHost) {
        this.srcHost = srcHost;
    }

    public SessionChart getChartData() {
        return chartData;
    }

    public void setChartData(SessionChart chartData) {
        this.chartData = chartData;
    }

    public List<HttpRequest> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<HttpRequest> requestList) {
        this.requestList = requestList;
    }

    public List<HttpResponse> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<HttpResponse> responseList) {
        this.responseList = responseList;
    }


}
