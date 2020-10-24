package org.vkbot.dao;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.vkbot.models.User;
import org.vkbot.utils.Hibernate;

import java.util.List;

public class UserDao
{
    public User findById(int id) {
        return Hibernate.getSession().get(User.class, id);
    }

    public void save(User user) {
        Session session = Hibernate.getSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public void update(User user) {
        Session session = Hibernate.getSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(User user) {
        Session session = Hibernate.getSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        return (List<User>) Hibernate.getSession()
                .createQuery("From User")
                .list();
    }
}
