package org.vkbot.commands.list;

import com.vk.api.sdk.objects.messages.Message;
import org.vkbot.App;
import org.vkbot.utils.OperationResult;

public class StatusCommand extends Command
{
    public StatusCommand(String name) {
        super(name);
    }

    @Override
    public boolean check(String message) {
        return message.trim()
                .split(" ")[0]
                .toLowerCase()
                .equals("статус");
    }

    @Override
    public String getAnswer(Message message) {
        var user = App.userService.get(message.getFromId());
        var msg = user.isSubscribed() ? "Вы подписаны на рассылку" : "Вы не подписаны на рассылку";

        return msg;
    }

    @Override
    public OperationResult exec(Message message) {
        return new OperationResult();
    }
}
