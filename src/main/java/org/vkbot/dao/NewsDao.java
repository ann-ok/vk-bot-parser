package org.vkbot.dao;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.vkbot.models.News;
import org.vkbot.utils.Hibernate;

import java.util.List;

public class NewsDao extends AbstractDao<News, Integer> {
    public News findById(Integer id) {
        return Hibernate.getSession().get(News.class, id);
    }

    public News findByLink(String link) {
        return (News) Hibernate.getSession()
                .createQuery("FROM News WHERE link=:link")
                .setParameter("link", link)
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<News> findDESCLimit(int n) {
        return (List<News>) Hibernate.getSession()
                .createQuery("FROM News ORDER BY date")
                .setMaxResults(n)
                .list();
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

    @SuppressWarnings("unchecked")
    public List<News> findAllASCDate() {
        return (List<News>) Hibernate.getSession()
                .createQuery("From News ORDER BY date")
                .list();
    }
}
