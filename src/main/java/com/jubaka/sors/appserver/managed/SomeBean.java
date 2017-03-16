package com.jubaka.sors.appserver.managed;

/**
 * Created by root on 05.02.17.
 */
public class SomeBean {

    private  String name = "unnamed";
    private  Integer count =0;

    SomeBean(String name,Integer count) {
        this.name = name;
        this.count = count;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


}