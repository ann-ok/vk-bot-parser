package org.vkbot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vkbot.servers.NewsServer;
import org.vkbot.servers.VKServer;
import org.vkbot.service.NewsService;
import org.vkbot.service.UserService;

import java.util.concurrent.Executors;

public class App 
{
    private static final Logger logger = LogManager.getLogger();

    public static NewsService newsService;
    public static UserService userService;

    public static void main(String[] args) {
        logger.error("Запуск сервисов...");
        userService = new UserService();
        newsService = new NewsService();
        logger.error("Готово");

        logger.error("Запуск сервера обработки новостей...");
        Executors.newCachedThreadPool().execute(new NewsServer());
        logger.error("Готово");

        logger.error("Запуск сервера работы с ВКонтакте...");
        Executors.newCachedThreadPool().execute(new VKServer());
        logger.error("Готово");
    }
}
