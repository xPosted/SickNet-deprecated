package com.jubaka.sors.appserver.beans;

import java.io.Serializable;

/**
 * Created by root on 01.09.16.
 */
public class Bean implements Serializable {

    private Long requestId = (long)-1;
    private Object object = null;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

}
