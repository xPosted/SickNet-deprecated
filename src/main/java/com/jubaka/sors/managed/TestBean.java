package com.jubaka.sors.managed;


import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Created by root on 28.08.16.
 */
@Named
@RequestScoped
public class TestBean {
    public String getHello() {
        System.out.println("niiiiiga");
        return "Hello";
    }

}
