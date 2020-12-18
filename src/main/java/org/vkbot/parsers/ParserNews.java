package org.vkbot.parsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.vkbot.App;
import org.vkbot.models.News;
import org.vkbot.utils.HtmlToPlainText;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class ParserNews
{
    private static final Logger logger = LogManager.getLogger();
    private static final int MAX_DAYS_TERM = 20;

    public static void initiate() {
        var minDate = Timestamp.valueOf(LocalDateTime.now().minusDays(MAX_DAYS_TERM));
        logger.error("--- Парсинг новостей ---");

        mainLoop:
        for (int i = 1; i < 10; i++) {
            try {
                logger.error("Парсинг страницы " + i + ":");

                Document page = Jsoup.connect("https://www.kpml.ru/pages/news?Pager_1=" + i)
                        .userAgent("Chrome/4.0.249.0 Safari/532.5")
                        .referrer("http://www.google.com")
                        .get();

                logger.error("(старт) Получение новостей");

                var counter = 0;
                Elements listNews = page.select(".content .columns .page-container .page-children-list .row");

                for (Element news : listNews.select(".column .page-children-item")) {
                    var formatter = new SimpleDateFormat("HH:mm:ss");

                    var date = news.select(".created-at").text();
                    var dateArray = date.split("\\.");
                    var dateFormatted = String.format("%s-%s-%s %s", dateArray[2], dateArray[1], dateArray[0], formatter.format(new Date()));

                    var timestamp = Timestamp.valueOf(dateFormatted);

                    if (timestamp.before(minDate)) {
                        logger.info("Все новости за последние " + MAX_DAYS_TERM + " дней добавлены");
                        logger.error("(финиш) Получение новостей. Получено новостей - " + counter);
                        break mainLoop;
                    }

                    var title = news.select("h2 a");
                    var newsLink = title.attr("abs:href");

                    if (App.newsService.findByLink(newsLink) != null) {
                        logger.error("(финиш) Получение новостей. Получено новостей - " + counter);
                        break mainLoop;
                    }

                    Document newsPage = Jsoup.connect(newsLink)
                            .userAgent("Chrome/4.0.249.0 Safari/532.5")
                            .referrer("http://www.google.com")
                            .get();

                    var newsPageTitle = newsPage.select(".content .columns .heading h1").text();
                    var newsPageContent = newsPage.selectFirst(".content .columns .page-container .page-content");

                    if (newsPageContent == null) {
                        continue;
                    }

                    HtmlToPlainText htmlFormatter = new HtmlToPlainText();
                    var newsPageContentFormatted = htmlFormatter.getPlainText(newsPageContent);

                    var newNews = new News(timestamp, newsPageTitle, newsLink, newsPageContentFormatted);
                    App.newsService.save(newNews);

                    counter++;
                }

                logger.error("(финиш) Получение новостей. Получено новостей - " + counter);

            } catch (Exception ex) {
                logger.error("Возникла ошибка при парсе новостей: " + ex.getMessage());
                break;
            }
        }
        logger.error("--- Парсинг новостей завершен ---");
    }
}
