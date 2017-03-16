package com.jubaka.sors.appserver.dao;

import com.jubaka.sors.appserver.entities.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by root on 23.10.16.
 */

@Named
@ApplicationScoped
public class UserDao extends AbstractGenericDao<User> {

    @PersistenceContext(unitName = "SorsPersistence", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @PostConstruct
    public void init() {

    }


    @Transactional
   public void test() {


       User u = new User();
       u.setEmail("maildfdf@mail.ru");
       u.setFirstName("ndfdfiga");
       u.setNickName("nidfdfga");
        u.setImage("dsfgs");
        u.setPass("sdfgsdfg");
        u.setJoinDate(new Timestamp(new Date().getTime()));

       entityManager.persist( u );

   }


    public User getUserByNick(String nick) {
        Query query = entityManager.
                createQuery("select user from User user where user.nickName = :nick");
        query.setParameter("nick",nick.toLowerCase());

        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (NoResultException e){
            //pair is wrong
            return null;
        }

    }

    public User getUserByEmail(String email) {
        Query query = entityManager.
                createQuery("select user from User user where user.email = :email");
        query.setParameter("email",email.toLowerCase());

        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (NoResultException e){
            //pair is wrong
            return null;
        }

    }

    @Transactional
    public void deleteUser(User user) {

        Query query = entityManager.createQuery("delete from User user where user.id = :id");
        query.setParameter("id",user.getId());
        query.executeUpdate();
    }

}
