package org.vkbot.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.vkbot.models.News;
import org.vkbot.models.Tag;
import org.vkbot.models.User;

import java.util.concurrent.locks.ReentrantLock;

public class Hibernate {
    private static final Logger logger = LogManager.getLogger();
    private static SessionFactory sessionFactory;
    private static Session session;

    private Hibernate() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(News.class);
                configuration.addAnnotatedClass(Tag.class);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                logger.error("Исключение: " + e.getMessage());
            }
        }
        return sessionFactory;
    }

    public static Session getSession() {
        if (session == null || !session.isOpen())
            session = getSessionFactory().openSession();

        var locker = new ReentrantLock();
        var condition = locker.newCondition();
        locker.lock();
        try {
            while (session.getTransaction().isActive())
                condition.await();
            logger.debug("Транзакция заверешна");
            condition.signalAll();
        } catch (InterruptedException e) {
            logger.error("Прервано ожидание сессии: ");
            logger.error(e.getMessage());
        } finally {
            locker.unlock();
        }
        return session;
    }
}
