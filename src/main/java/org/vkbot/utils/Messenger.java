package org.vkbot.utils;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vkbot.handler.MessageHandler;
import org.vkbot.handler.PropertyHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

public class Messenger
{
    private static final Logger logger = LogManager.getLogger();
    private static final Random random = new Random();

    private static VkApiClient vkApiClient;
    private static GroupActor groupActor;
    private static int ts;
    private static int maxMsgId = -1;

    private static String DEFAULT_KEYBOARD;

    public Messenger() {
        keyboardInit();

        try {  // Получение конфигурации
            PropertyHandler propertyHandler = new PropertyHandler();
            var groupId =  Integer.parseInt(propertyHandler.getProperty("groupId"));
            var accessToken = propertyHandler.getProperty("accessToken");

            groupActor = new GroupActor(groupId, accessToken);
        } catch (IOException e) {
            logger.error("Ошибка при загрузке конфигурации: " + e.getMessage());
        }

        initializeClient();
    }

    private static void initializeClient() {
        TransportClient transportClient = new HttpTransportClient();
        vkApiClient = new VkApiClient(transportClient);

        try {
            ts = vkApiClient.messages().getLongPollServer(groupActor).execute().getTs();
        } catch (ApiException | ClientException e) {
            logger.error("Ошибка при обращении к ВК: " + e.getMessage());
        }
    }

    public static void update() throws InterruptedException {
        List<Message> messages = null;

        final int MAX_RECONNECTS = 5;
        final int RECONNECT_TIME = 5;

        for (int i = 0; i < MAX_RECONNECTS; i++) {
            try {
                messages = getMessages();
                break;
            } catch (ClientException | ApiException e) {
                logger.error("Ошибка при получении сообщений: " + e.getMessage());
                logger.error("Повторное подключение к ВК через " + RECONNECT_TIME + " секунд...");
                Thread.sleep(RECONNECT_TIME * 1000);
                initializeClient();
            }
        }

        if (messages != null) {
            Executors.newCachedThreadPool().execute(new MessageHandler(messages));
        }
    }

    private static List<Message> getMessages() throws ClientException, ApiException {
        var historyQuery = vkApiClient.messages().getLongPollHistory(groupActor).ts(ts);

        if (maxMsgId > 0) {
            historyQuery.maxMsgId(maxMsgId);
        }

        var messages = historyQuery.execute().getMessages().getItems();

        if (!messages.isEmpty()) {
            messages.removeIf(x -> x.getFromId() < 0);
            messages.forEach(x -> maxMsgId = Math.max(maxMsgId, x.getId()));

            ts = vkApiClient.messages().getLongPollServer(groupActor).execute().getTs();
        }

        return messages;
    }

    private static int getRandomId() {
        var millis = System.currentTimeMillis();
        var randomLong = random.nextLong();
        return (int) ((millis + randomLong) % Integer.MAX_VALUE);
    }

    public static void sendMessage(String msg, int peerId) {
        sendMessage(msg, peerId, null);
    }

    public static void sendMessage(String msg, int peerId, String inlineKeyboard) {

        if (msg == null) {
            logger.error("Попытка отправить пустое сообщение");
            return;
        }

        try {
            var keyboard = DEFAULT_KEYBOARD;

            while (msg.length() > 4096) {
                int i = 0;

                while (true) {
                    int j = msg.indexOf('\n', i + 1);
                    if (j > 4096 || j == -1) break;
                    i = j;
                }

                vkApiClient.messages().send(groupActor).peerId(peerId).randomId(getRandomId())
                        .message(msg.substring(0, i)).unsafeParam("keyboard", keyboard).execute();

                msg = msg.substring(i + 1);
            }

            if (inlineKeyboard != null) {
                keyboard = inlineKeyboard;
            }

            vkApiClient.messages().send(groupActor).peerId(peerId).randomId(getRandomId())
                    .message(msg).unsafeParam("keyboard", keyboard).execute();
        } catch (ApiException | ClientException e) {
            logger.error("Ошибка при отправке сообщения: " + e.getMessage());
        }
    }

    public static String getUserFirstName(Message message) {
        var clientId = String.valueOf(message.getFromId());

        try {
            var userFields = vkApiClient.users().get(groupActor)
                    .userIds(clientId).fields().execute().get(0);

            return userFields.getFirstName();
        } catch (ApiException | ClientException e) {
            logger.error("Ошибка при получении имени клиента: " + e.getMessage());
            logger.error("Вместо имени было передано ID: " + clientId);

            return clientId;
        }
    }

    private void keyboardInit() {
        String keyboard = null;
        try {
            var fs = new FileInputStream("src/main/resources/keyboard.json");
            keyboard = new String(fs.readAllBytes());
        } catch (IOException e) {
            logger.error("Не удалось прочитать конфигурацию клавиатуры: ");
            logger.error(e.getMessage());
        }
        if (keyboard == null) DEFAULT_KEYBOARD = "{\"buttons\":[], \"one_time\":true}";
        else DEFAULT_KEYBOARD = keyboard;
    }
}
