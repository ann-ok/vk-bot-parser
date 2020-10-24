package org.vkbot.servers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vkbot.utils.Messenger;

public class VKServer implements Runnable
{
    private static final Logger logger = LogManager.getLogger();

    public VKServer() {
        new Messenger();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(300);
                Messenger.update();
            } catch (InterruptedException e) {
                logger.error("Возникли проблемы: " + e.getMessage());
                final int RECONNECT_TIME = 10;
                logger.error("Повторное соединение через " + RECONNECT_TIME + " секунд...");
                try {
                    Thread.sleep(RECONNECT_TIME * 1000);
                } catch (InterruptedException ex) {
                    logger.error("Совсем всё сломалось :`(");
                    logger.error(ex.getMessage());
                    return;
                }
            }
        }
    }
}
