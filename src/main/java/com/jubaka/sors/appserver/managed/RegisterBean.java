package com.jubaka.sors.appserver.managed;

import com.jubaka.sors.appserver.entities.User;
import com.jubaka.sors.appserver.service.UserLimitsService;
import com.jubaka.sors.appserver.service.UserService;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by root on 28.08.16.
 */

@Named
@SessionScoped
public class RegisterBean implements Serializable {

    @Inject
    private UserService userService;

    @Inject
    private UserLimitsService userLimitsService;

    private int key = -1;
    private String nickName;
    private String firstName;
    private String secondName;
    private String phone;
    private String email;
    private String pass;
 //   private String image;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public boolean registerAction(){

        User u = new User();
        u.setNickName(nickName);
        u.setPass(PassEncoder.encode(pass));
        u.setFirstName(firstName);
        u.setSecondName(secondName);
        u.setEmail(email);
        u.setPhone(phone);
        u.setJoinDate(new Date());
        u.setLimits(userLimitsService.getDefaultLims());
        //u.setLastLogin(new Date());
      //  u.setImage(request.getParameter("imageff"));
        userService.addUser(u);


        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/app/jsf/inspinia/content/landing.xhtml");

        } catch (IOException io) {
            io.printStackTrace();
            return false;
        }

        return true;
    }

}
