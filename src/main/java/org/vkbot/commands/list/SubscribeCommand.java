package org.vkbot.commands.list;

import com.vk.api.sdk.objects.messages.Message;
import org.vkbot.App;
import org.vkbot.utils.OperationResult;

public class SubscribeCommand extends Command {

    public SubscribeCommand(String name) {
        super(name);
    }

    @Override
    public boolean check(String message) {
        return message.trim()
                .split(" ")[0]
                .toLowerCase()
                .equals("подписаться");
    }

    @Override
    public String getAnswer(Message message) {
        return "Вы подписались на рассылку";
    }

    @Override
    public OperationResult exec(Message message) {
        var user = App.userService.get(message.getFromId());

        user.setSubscribed(true);
        App.userService.update(user);

        return new OperationResult();
    }
}