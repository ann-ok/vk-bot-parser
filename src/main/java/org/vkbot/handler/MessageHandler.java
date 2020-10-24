package org.vkbot.handler;

import com.vk.api.sdk.objects.messages.Message;
import org.vkbot.App;
import org.vkbot.commands.CommandManager;

import java.util.List;

public class MessageHandler implements Runnable
{
    private final List<Message> messages;

    public MessageHandler(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public void run() {
        for (var message : messages) {
            App.userService.saveIfNotExists(message.getFromId());
            CommandManager.execute(message);
        }
    }
}
