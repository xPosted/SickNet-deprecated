package com.jubaka.sors.managed;

import com.jubaka.sors.entities.User;
import com.jubaka.sors.serverSide.UserBase;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
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

    private int key = -1;
    private String nickName;
    private String firstName;
    private String secondName;
    private String phone;
    private String email;
    private String pass;
    private String pass2;
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

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
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
        u.setPass(pass);
        u.setFirstName(firstName);
        u.setSecondName(secondName);
        u.setEmail(email);
        u.setPhone(phone);
        u.setJoinDate(new Date());
        //u.setLastLogin(new Date());
      //  u.setImage(request.getParameter("imageff"));
        boolean res = UserBase.getInstance().addUser(u);

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("landing.xhtml");
        } catch (IOException io) {
            io.printStackTrace();
            return false;
        }

        return true;
    }

}
