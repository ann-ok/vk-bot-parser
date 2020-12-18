package org.vkbot.service;

import org.vkbot.dao.NewsDao;
import org.vkbot.models.News;

import java.util.List;

public class NewsService {
    private final NewsDao newsDao = new NewsDao();

    public NewsService() {}

    public News get(int id) {
        return newsDao.findById(id);
    }

    public News findByLink(String link) {
        return newsDao.findByLink(link);
    }

    public List<News> findDESCLimit(int n) {
        return  newsDao.findDESCLimit(n);
    }

    public void save(News news) {
        newsDao.save(news);
    }

    public void delete(News news) {
        newsDao.delete(news);
    }

    public void update(News news) {
        newsDao.update(news);
    }

    public List<News> findAll() {
        return newsDao.findAll();
    }

    public List<News> findAllASCDate() {
        return newsDao.findAllASCDate();
    }
}
