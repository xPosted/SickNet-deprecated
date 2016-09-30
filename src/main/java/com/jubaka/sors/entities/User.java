package com.jubaka.sors.entities;

import com.jubaka.sors.serverSide.*;
import com.jubaka.sors.serverSide.Node;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 28.08.16.
 */
public class User {

    private int key = -1;
    private String nickName;
    private String firstName;
    private String secondName;
    private String phone;
    private String email;
    private String pass;
    private Timestamp joinDate;
    private String image;

    private Set<com.jubaka.sors.serverSide.Node> availableNodes = new HashSet<Node>();
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public Date getJoinDate() {
        return joinDate;
    }
    public void setJoinDate(Date joinDate) {
        this.joinDate = new Timestamp(joinDate.getTime());
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }


}
