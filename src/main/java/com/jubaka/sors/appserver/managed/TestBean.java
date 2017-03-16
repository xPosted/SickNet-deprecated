package com.jubaka.sors.appserver.managed;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 28.08.16.
 */
@Named
@SessionScoped
public class TestBean implements Serializable {
    private List<SomeBean> strs = new ArrayList<>();

    public TestBean() {
        buildList();
    }
    public SomeBean getSelected() {
        return selected;
    }

    public void setSelected(SomeBean selected) {
        this.selected = selected;
    }

    private SomeBean selected = new SomeBean("undefined",0);

    public List<SomeBean> getStrs() {
        return strs;
    }

    public void setStrs(List<SomeBean> strs) {
        this.strs = strs;
    }

    public void buildList() {
        strs.add(new SomeBean("Alex", 1));
        strs.add(new SomeBean("Jack", 2));
        strs.add(new SomeBean("pedro", 3));
    }

    public void add() {
        strs.add(new SomeBean("asddsa",8));
    }
    public void modify() {
        strs.get(0).setName("modified name");
    }

    public String getHello() {
        System.out.println("niiiiiga");
        return "Hello";
    }


}
