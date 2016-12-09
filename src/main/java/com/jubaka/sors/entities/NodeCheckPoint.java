package com.jubaka.sors.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by root on 25.10.16.
 */
@Entity
public class NodeCheckPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "nodeId")
    private Node node;
    private String ip;
    private Timestamp date;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }


}
