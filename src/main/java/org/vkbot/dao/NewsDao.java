package org.vkbot.dao;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.vkbot.models.News;
import org.vkbot.utils.Hibernate;

import java.util.List;

public class NewsDao {
    public News findById(int id) {
        return Hibernate.getSession().get(News.class, id);
    }

    public News findByHead(String head) {
        var session = Hibernate.getSession();
        return (News) session.createQuery("FROM News WHERE head=:head")
                .setParameter("head", head)
                .uniqueResult();
    }

    public void save(News news) {
        Session session = Hibernate.getSession();
        Transaction tx1 = session.beginTransaction();
        session.save(news);
        tx1.commit();
        session.close();
    }

    public void update(News news) {
        Session session = Hibernate.getSession();
        Transaction tx1 = session.beginTransaction();
        session.update(news);
        tx1.commit();
        session.close();
    }

    public void delete(News news) {
        Session session = Hibernate.getSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(news);
        tx1.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public List<News> findAll() {
        return (List<News>) Hibernate.getSession()
                .createQuery("From News")
                .list();
    }
}
