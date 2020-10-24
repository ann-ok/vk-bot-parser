package org.vkbot.dao;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.vkbot.models.Tag;
import org.vkbot.utils.Hibernate;

import java.util.List;

public class TagDao {
    public Tag findById(int id) {
        return Hibernate.getSession().get(Tag.class, id);
    }

    public Tag findByName(String name, boolean caseSensitive) {
        var session = Hibernate.getSession();
        return (Tag) session.createQuery(getFindQuery(caseSensitive))
                .setParameter("name", name)
                .uniqueResult();
    }

    private String getFindQuery(boolean caseSensitive) {
        return caseSensitive ? "FROM Tag WHERE name=:name"
                : "FROM Tag WHERE LOWER(name)=LOWER(:name)";
    }

    public void save(Tag tag) {
        Session session = Hibernate.getSession();
        Transaction tx1 = session.beginTransaction();
        session.save(tag);
        tx1.commit();
        session.close();
    }

    public void update(Tag tag) {
        Session session = Hibernate.getSession();
        Transaction tx1 = session.beginTransaction();
        session.update(tag);
        tx1.commit();
        session.close();
    }

    public void delete(Tag tag) {
        Session session = Hibernate.getSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(tag);
        tx1.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public List<Tag> findAll() {
        return (List<Tag>) Hibernate.getSession()
                .createQuery("From Tag")
                .list();
    }
}
