package org.vkbot.commands.list;

import com.vk.api.sdk.objects.messages.Message;

public class UnknownCommand extends Command {

    public UnknownCommand(String name) {
        super(name);
    }

    private boolean emptyBody(String body) {
        return body == null || body.isEmpty();
    }

    @Override
    public String getAnswer(Message message) {
        var msg = emptyBody(message.getText()) ? "Неизвестная команда" : message.getText();
        msg += "\n\n(Для справки напишите \"Помощь\")";
        return msg;
    }

    @Override
    public void exec(Message message) {}
}