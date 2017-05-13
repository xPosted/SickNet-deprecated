package com.jubaka.sors.appserver.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by root on 12.05.17.
 */

@Entity
public class UserLimits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public long totalTasksLen;
    public long totalDataLen;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTotalTasksLen() {
        return totalTasksLen;
    }

    public void setTotalTasksLen(long totalTasksLen) {
        this.totalTasksLen = totalTasksLen;
    }

    public long getTotalDataLen() {
        return totalDataLen;
    }

    public void setTotalDataLen(long totalDataLen) {
        this.totalDataLen = totalDataLen;
    }
}
