package com.jubaka.sors.managed;

import com.jubaka.sors.serverSide.User;
import com.jubaka.sors.service.UserService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by root on 28.08.16.
 */


@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String login;
    private String pass;
    private boolean linked;
    private User user;



    @Inject
    private UserService userService;


    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean loginAction() {

        user = userService.checkUser(login,pass);
        if (user != null) return true;
        else return false;
    }

}
