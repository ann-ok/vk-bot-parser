package org.vkbot.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vkbot.App;
import org.vkbot.models.News;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class NewsObserver
{
    private static final Logger logger = LogManager.getLogger();
    private Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());

    public NewsObserver() {
        for (var user : App.userService.findAll()) {
            if (user.getLastUpdate().before(lastUpdate)) {
                lastUpdate = user.getLastUpdate();
            }
        }
    }

    public void observe() {
        logger.error("--- Обновление новостей ---");

        logger.error("Удаление старых новостей...");
        clearPastNews();
        logger.error("Готово");

        logger.error("Рассылка новых новостей...");
        sendUsersNewsletter();
        logger.error("Готово");

        setLastUpdate();

        logger.error("--- Обновление новостей завершено ---");
    }

    private void setLastUpdate() {
        lastUpdate = Timestamp.valueOf(LocalDateTime.now());

        for (var user : App.userService.findAll()) {
            user.setLastUpdate(lastUpdate);
            App.userService.update(user);
        }
    }

    private void sendUsersNewsletter() {
        for (var user : App.userService.findAll()) {
            logger.error("Рассылка новостей пользователю " + user.getId());

            var counter = 0;

            if (user.isSubscribed()) {
                for (var news : App.newsService.findAll()) {
                    if (user.getLastUpdate().before(news.getDate())) {
                        Messenger.sendMessage(getNewsString(news), user.getId());
                        counter++;
                    }
                }
            }

            logger.error("Отправлено новостей - " + counter);
        }
    }

    public static String getNewsString(News news) {
        return new SimpleDateFormat("'&#128197; 'dd.MM.yyyy'\n'")
                .format(news.getDate())
                + "&#10071;"
                + news.getHead().toUpperCase()
                + "\n"
                + news.getContent();
    }

    private void clearPastNews() {
        var counter = 0;

        var allNews = App.newsService.findAllASCDate();

        if (allNews.size() > 5) {
            for (var news : allNews) {
                if (news.getDate().before(lastUpdate)) {
                    App.newsService.delete(news);
                    counter++;
                    logger.info("Удалена новость: " + news.getLink());

                    if (allNews.size() - counter == 5) {
                        break;
                    }
                }
            }
        }

        logger.error("Удалено новостей - " + counter);
    }
}
