package org.vkbot.servers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vkbot.parsers.ParserNews;
import org.vkbot.utils.NewsObserver;

public class NewsServer implements Runnable
{
    private static final Logger logger = LogManager.getLogger();
    private static final int PAUSE_MINUTES = 10;

    @Override
    public void run() {
        var newsObserver = new NewsObserver();

        while (true) {
            try {
                ParserNews.initiate();
                newsObserver.observe();
                Thread.sleep(1000 * 60 * PAUSE_MINUTES);
            } catch (InterruptedException ex) {
                logger.error("Ошибка в сервере обработки новостей:");
                logger.error(ex.getMessage());
                return;
            }
        }
    }
}
