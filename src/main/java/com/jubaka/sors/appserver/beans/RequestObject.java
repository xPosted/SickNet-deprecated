package com.jubaka.sors.appserver.beans;

import java.io.Serializable;

/**
 * Created by root on 01.09.16.
 */
public class RequestObject extends Bean implements Serializable {

    public String[] getRequestStr() {
        return requestStr;
    }

    public void setRequestStr(String[] requestStr) {
        this.requestStr = requestStr;
    }

    private String[] requestStr;
}
