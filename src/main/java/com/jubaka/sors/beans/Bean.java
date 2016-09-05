package com.jubaka.sors.beans;

import java.io.Serializable;

/**
 * Created by root on 01.09.16.
 */
public class Bean implements Serializable {

    private Long requestId = (long)-1;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    private Object object = null;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

}
