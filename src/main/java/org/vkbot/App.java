package org.vkbot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vkbot.servers.VKServer;
import org.vkbot.service.NewsService;
import org.vkbot.service.TagService;
import org.vkbot.service.UserService;
import org.vkbot.utils.Hibernate;

import java.util.concurrent.Executors;

public class App 
{
    private static final Logger logger = LogManager.getLogger();
    public static NewsService newsService;
    public static UserService userService;
    public static TagService tagService;

    public static void main( String[] args ) {
        logger.error("Запуск сервисов...");
        userService = new UserService();
        newsService = new NewsService();
        tagService = new TagService();
        logger.error("Готово");

        logger.error("Запуск сервера работы с ВКонтакте...");
        Executors.newCachedThreadPool().execute(new VKServer());
        logger.error("Готово");
    }
}
