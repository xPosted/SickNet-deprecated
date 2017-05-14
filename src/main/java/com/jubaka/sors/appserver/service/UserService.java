package com.jubaka.sors.appserver.service;

import com.jubaka.sors.appserver.dao.UserDao;
import com.jubaka.sors.appserver.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * Created by root on 02.09.16.
 */

@Named
@ApplicationScoped
public class UserService {
    @Inject
    private UserDao userDao;




    @Transactional
    public void addUser(User u) {
        userDao.insert(u);
    }

    public void deleteUser(Long id) {

    }
/*
    public User getUserByNick(String nick) {

    }

    public User getUserById(Long id) {

    }

    public User getUserByEmail() {

    }


*/


    public void deleteUser(User u) {
        userDao.deleteUser(u);

    }

    public User updateUser(User u) {
       return userDao.update(u);
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }
    public User getUserByNick(String nick) {
        return userDao.getUserByNick(nick);
    }
    public User checkUser(String nickName, String pass) {

        User user = userDao.getUserByNick(nickName);
        if (user != null) {
            if (user.getPass().equals(pass)) return user;
        }
        return null;
        /*
        UserBase ub = UserBase.getInstance();
        User uObj = ub.getUser(nickName);
        if (uObj.getPass().equals(pass)) {
           return uObj;
        }
        return null;
        */
    }

}
