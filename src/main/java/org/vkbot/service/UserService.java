package org.vkbot.service;

import org.vkbot.dao.UserDao;
import org.vkbot.models.User;

import java.util.List;

public class UserService {
    private final UserDao userDao = new UserDao();

    public UserService() {}

    public User get(int id) {
        return userDao.findById(id);
    }

    public void save(User user) {
        userDao.save(user);
    }

    public void saveIfNotExists(int id) {
        if (get(id) == null) {
            userDao.save(new User(id));
        }
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }
}
