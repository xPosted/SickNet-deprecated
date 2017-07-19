package com.jubaka.sors.appserver.managed;


import com.jubaka.sors.appserver.entities.User;
import com.jubaka.sors.appserver.service.UserService;
import org.apache.commons.mail.HtmlEmail;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by root on 28.08.16.
 */


@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String login;
    private String pass;

    private String changePass;

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

    public String getChangePass() {
        return changePass;
    }

    public void setChangePass(String changePass) {
        this.changePass = changePass;
    }

    @PostConstruct
    public void testConstruct() {
        System.out.println("test postconstruct in loginBean");
    }


    public void redirectToLogIn() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        linked = false;
        user = null;
        login = "";
        pass = "";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/app/jsf/inspinia/content/login_v2.xhtml");
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    public void loginAction() throws IOException {
        if (user != null)  {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/app/jsf/inspinia/content/TaskList_tmp.xhtml");
            return;
        }
        if (pass!=null & login!= null) {
            pass = PassEncoder.encode(pass);
            user = userService.checkUser(login, pass);
            login = null;
            pass = null;
            if (user != null) {
                linked = true;
                FacesContext.getCurrentInstance().getExternalContext().redirect("/app/jsf/inspinia/content/TaskList_tmp.xhtml");
            } else {
                FacesContext fcontext = FacesContext.getCurrentInstance();
                fcontext.addMessage("loginForm:passField",new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid login or password",""));
            }
        }

    }


    public void updateUser() {

        user = userService.updateUser(user);
       // FacesContext.getCurrentInstance().addMessage("Update user info:", new FacesMessage("Update Success!"));
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Account Info", "Your personal data had been updated!");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void test() {
        System.out.println("niga");
    }


    public void logoutAction() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        linked = false;
        user = null;
        login = "";
        pass = "";
        FacesContext.getCurrentInstance().getExternalContext().redirect("/app/jsf/inspinia/content/landing.xhtml");

    }

    public void resetPassword() {
        //
        if (login == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Security","Please enter login or email");
            FacesContext.getCurrentInstance().addMessage("resetPassForm:loginInput",msg);
            return;
        }
            User u;
            u = userService.getUserByEmail(login);
            if (u == null) u = userService.getUserByNick(login);
        if (u == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Security","User with this login or email not found!");
            FacesContext.getCurrentInstance().addMessage("resetPassForm:loginInput",msg);
            return;
        }
            if (u!=null) {
                String uuid = UUID.randomUUID().toString();
                String newRandomPass = uuid.substring(0,8);
                String encNewRandomPass = PassEncoder.encode(newRandomPass);
                u.setPass(encNewRandomPass);
                userService.updateUser(u);
                sendEmail("This is your new PASSWORD: "+newRandomPass, Arrays.asList(u.getEmail()));
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Security","Your password has been sent to you by email!");
                FacesContext.getCurrentInstance().addMessage(null,msg);
            }

    }

    public void changePassword() {
        if (changePass == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Security","Please enter all values!");
            FacesContext.getCurrentInstance().addMessage("changePassForm:newPass_0",msg);
            return;
        }

        if (user != null) {
            String encPass = PassEncoder.encode(changePass);
            user.setPass(encPass);
            userService.updateUser(user);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Security","Your password is changed!");
            FacesContext.getCurrentInstance().addMessage(null,msg);
        }
    }

    private void sendEmail(String content, List<String> sendTos) {
        try {
            // Create the email message
            HtmlEmail email = new HtmlEmail();
            email.setAuthentication("aleksandrzhupanov@gmail.com", "cogzvcmfryeuqyhw");
            email.setHostName("smtp.gmail.com");
            email.setCharset("UTF8");
            email.setSmtpPort(587);
            email.setStartTLSEnabled(true);
            for (String sendTo : sendTos) {
                email.addTo(sendTo, "");
            }
            email.setFrom("aleksandrzhupanov@gmail.com", "Sors");
            email.setSubject("SickNET notification");

            // set the html message
            email.setHtmlMsg(content);

            // set the alternative message
            email.setTextMsg("Your email client does not support HTML messages");

            // send the email
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
