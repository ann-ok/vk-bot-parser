package org.vkbot.commands.list;

import com.vk.api.sdk.objects.messages.Message;
import org.vkbot.App;
import org.vkbot.utils.OperationResult;

public class UnsubscribeCommand extends Command {

    public UnsubscribeCommand(String name) {
        super(name);
    }

    @Override
    public boolean check(String message) {
        return message.trim()
                .split(" ")[0]
                .toLowerCase()
                .equals("отписаться");
    }

    @Override
    public String getAnswer(Message message) {
        return "Вы отписались от рассылки";
    }

    @Override
    public OperationResult exec(Message message) {
        var user = App.userService.get(message.getFromId());

        user.setSubscribed(false);
        App.userService.update(user);

        return new OperationResult();
    }
}