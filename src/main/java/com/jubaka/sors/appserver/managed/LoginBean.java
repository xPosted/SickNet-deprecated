package com.jubaka.sors.appserver.managed;


import com.jubaka.sors.appserver.entities.User;
import com.jubaka.sors.appserver.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
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

    @PostConstruct
    public void testConstruct() {
        System.out.println("test postconstruct in loginBean");
    }

    public void loginAction() throws IOException {
        if (user != null)  {
            FacesContext.getCurrentInstance().getExternalContext().redirect("TaskList_tmp.xhtml");
            return;
        }
        if (pass!=null & login!= null) {
            pass = PassEncoder.encode(pass);
            user = userService.checkUser(login, pass);
        }
        if (user != null) {
            linked = true;
            FacesContext.getCurrentInstance().getExternalContext().redirect("TaskList_tmp.xhtml");
        }

    }




    public void logoutAction() throws IOException{
        linked = false;
        user = null;
        login = "";
        pass = "";
        FacesContext.getCurrentInstance().getExternalContext().redirect("landing.xhtml");

    }

}
